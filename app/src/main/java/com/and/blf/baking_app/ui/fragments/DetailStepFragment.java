package com.and.blf.baking_app.ui.fragments;

import android.app.Dialog;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.and.blf.baking_app.R;
import com.and.blf.baking_app.model.Step;
import com.and.blf.baking_app.ui.RecipeHostActivity;
import com.and.blf.baking_app.ui.StepClickListener;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class DetailStepFragment extends Fragment implements View.OnClickListener, StepClickListener {
    private int mCurrentStepIndx;

    private final String STATE_RESUME_WINDOW = "resumeWindow";
    private final String STATE_RESUME_POSITION = "resumePosition";
    private final String STATE_PLAYER_FULLSCREEN = "playerFullscreen";
    private SimpleExoPlayerView mExoPlayerView;
    private MediaSource mVideoSource;
    private boolean mExoPlayerFullscreen = false;
    private ImageView mFullScreenIcon;
    private Dialog mFullScreenDialog;

    private int mResumeWindow;
    private long mResumePosition;

    private String mStringUri;

    private final String BACK_BUTTON_TAG = "BACK_BUTTON_TAG";
    private final String NEXT_BUTTON_TAG = "NEXT_BUTTON_TAG";

    private TextView descriptionTV;
    private int maxStepNum;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_step,container,false);

        descriptionTV = rootView.findViewById(R.id.description_tv);
        Button b = rootView.findViewById(R.id.previous_step_button);
        b.setOnClickListener(this);
        b.setTag(BACK_BUTTON_TAG);
        b = rootView.findViewById(R.id.next_step_button);
        b.setOnClickListener(this);
        b.setTag(NEXT_BUTTON_TAG);

        return  rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            mResumeWindow = savedInstanceState.getInt(STATE_RESUME_WINDOW);
            mResumePosition = savedInstanceState.getLong(STATE_RESUME_POSITION);
            mExoPlayerFullscreen = savedInstanceState.getBoolean(STATE_PLAYER_FULLSCREEN);
        }

        boolean twoPane = (getActivity().findViewById(R.id.frame_divider) != null);
        if (twoPane) {
            Step curStep = ((RecipeHostActivity)getActivity()).mCurrStep;
            if(curStep == null){
                curStep = ((RecipeHostActivity)getActivity()).mRecipe.getSteps().get(0);
            }
            mStringUri = curStep.getVideoURL();
            mCurrentStepIndx = ((RecipeHostActivity)getActivity()).mRecipe.getSteps().indexOf(curStep);
            maxStepNum = ((RecipeHostActivity)getActivity()).mRecipe.getSteps().size();
            descriptionTV.setText(curStep.getDescription());

        } else {
            if (savedInstanceState != null) {
                mStringUri = savedInstanceState.getString(getString(R.string.mStringUri_tag));
                mCurrentStepIndx = savedInstanceState.getInt(getString(R.string.stepIndex_tag));
                maxStepNum = savedInstanceState.getInt(getString(R.string.maxStepNum_tag));
                descriptionTV.setText(savedInstanceState.getString(getString(R.string.descriptionTV_tag)));
            } else {
                maxStepNum = getArguments().getInt(getString(R.string.maxStepNum_tag));
                mStringUri = (String) getArguments().get(getString(R.string.video_ulr_tag));
                mCurrentStepIndx = getArguments().getInt(getString(R.string.stepIndex_tag));
                descriptionTV.setText((String) getArguments().get(getString(R.string.step_description_tag)));
            }
        }

        if (mStringUri == null || mStringUri.isEmpty()) {
            getActivity().findViewById(R.id.exoplayer).setVisibility(View.GONE);
            getActivity().findViewById(R.id.splash_screen).setVisibility(View.VISIBLE);
            mStringUri = "";
        }

        ((RecipeHostActivity)getActivity()).set_mDetailfargment(this);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_RESUME_WINDOW, mResumeWindow);
        outState.putLong(STATE_RESUME_POSITION, mResumePosition);
        outState.putBoolean(STATE_PLAYER_FULLSCREEN, mExoPlayerFullscreen);

        outState.putInt(getString(R.string.stepIndex_tag),mCurrentStepIndx);
        outState.putInt(getString(R.string.maxStepNum_tag),maxStepNum);
        outState.putString(getString(R.string.mStringUri_tag),mStringUri);
        outState.putString(getString(R.string.descriptionTV_tag),descriptionTV.getText().toString());

        super.onSaveInstanceState(outState);
    }

    private void initFullscreenDialog() {
        mFullScreenDialog = new Dialog(getActivity(), android.R.style.Theme_Black_NoTitleBar_Fullscreen) {
            public void onBackPressed() {
                if (mExoPlayerFullscreen)
                    closeFullscreenDialog();
                super.onBackPressed();
            }
        };
    }

    private void openFullscreenDialog() {
        ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
        mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_fullscreen_skrink));
        mExoPlayerFullscreen = true;
        mFullScreenDialog.show();
    }

    private void closeFullscreenDialog() {
        ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
        ((FrameLayout) getActivity().findViewById(R.id.main_media_frame)).addView(mExoPlayerView);
        mExoPlayerFullscreen = false;
        mFullScreenDialog.dismiss();
        mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_fullscreen_expand));
    }

    private void initFullscreenButton() {
        PlaybackControlView controlView = mExoPlayerView.findViewById(R.id.exo_controller);
        mFullScreenIcon = controlView.findViewById(R.id.exo_fullscreen_icon);
        FrameLayout fullScreenButton = controlView.findViewById(R.id.exo_fullscreen_button);
        fullScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mExoPlayerFullscreen)
                    openFullscreenDialog();
                else
                    closeFullscreenDialog();
            }
        });
    }

    private void initExoPlayer() {
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        LoadControl loadControl = new DefaultLoadControl();
        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getActivity()), trackSelector, loadControl);
        mExoPlayerView.setPlayer(player);

        boolean haveResumePosition = mResumeWindow != C.INDEX_UNSET;

        if (haveResumePosition) {
            mExoPlayerView.getPlayer().seekTo(mResumeWindow, mResumePosition);
        }

        mExoPlayerView.setControllerShowTimeoutMs(0);
        mExoPlayerView.setControllerHideOnTouch(false);
        mExoPlayerView.getPlayer().prepare(mVideoSource);
        mExoPlayerView.getPlayer().setPlayWhenReady(true);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mExoPlayerView == null) {
            mExoPlayerView = getActivity().findViewById(R.id.exoplayer);
            initFullscreenDialog();
            initFullscreenButton();
            mVideoSource = buildMediaSource();
            initExoPlayer();
        }

        int newConfig = getResources().getConfiguration().orientation;
        if (newConfig == Configuration.ORIENTATION_LANDSCAPE) {
            //Toast.makeText(getActivity(), "landscape", Toast.LENGTH_SHORT).show();
            mExoPlayerFullscreen = true;

        } else if (newConfig == Configuration.ORIENTATION_PORTRAIT){
            //Toast.makeText(getActivity(), "portrait", Toast.LENGTH_SHORT).show();
            mExoPlayerFullscreen = false;
        }

        if (mExoPlayerFullscreen) {
            ((ViewGroup) mExoPlayerView.getParent()).removeView(mExoPlayerView);
            mFullScreenDialog.addContentView(mExoPlayerView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            mFullScreenIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_fullscreen_skrink));
            mFullScreenDialog.show();
        }
    }

    private MediaSource buildMediaSource() {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory(Util.getUserAgent(getActivity(), getString(R.string.app_name)))).
                createMediaSource(Uri.parse(mStringUri));
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mExoPlayerView != null && mExoPlayerView.getPlayer() != null) {
            mResumeWindow = mExoPlayerView.getPlayer().getCurrentWindowIndex();
            mResumePosition = Math.max(0, mExoPlayerView.getPlayer().getContentPosition());

            mExoPlayerView.getPlayer().release();
        }

        if (mFullScreenDialog != null)
            mFullScreenDialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(NEXT_BUTTON_TAG)) {
            if (mCurrentStepIndx < maxStepNum-1) {
                mCurrentStepIndx++;
                switchStep();
            }else Toast.makeText(getActivity(), R.string.max_step_is_reached_info,Toast.LENGTH_SHORT).show();
        } else if (tag.equals(BACK_BUTTON_TAG)) {
            if (mCurrentStepIndx > 0) {
                mCurrentStepIndx--;
                switchStep();
            }else Toast.makeText(getActivity(), R.string.mix_step_is_reached_info,Toast.LENGTH_SHORT).show();
        }
    }

    private void switchStep(){
        RecipeHostActivity hostActivity = (RecipeHostActivity)getActivity();
        Step curStep = hostActivity.getHostedRecipe().getSteps().get(mCurrentStepIndx);
        hostActivity.mCurrStep = curStep;
        hostActivity.getSupportActionBar().setTitle(curStep.getShortDescription());

        descriptionTV.setText(curStep.getDescription());
        mStringUri = curStep.getVideoURL();

        if(!mStringUri.isEmpty()) {
            getActivity().findViewById(R.id.exoplayer).setVisibility(View.VISIBLE);
            getActivity().findViewById(R.id.splash_screen).setVisibility(View.GONE);
            mExoPlayerView.getPlayer().prepare(buildMediaSource());
        } else {
            getActivity().findViewById(R.id.exoplayer).setVisibility(View.GONE);
            getActivity().findViewById(R.id.splash_screen).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onStepClicked(int stepIndex) {

        mCurrentStepIndx = stepIndex;
        switchStep();
    }
}