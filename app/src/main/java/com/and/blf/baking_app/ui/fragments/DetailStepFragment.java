package com.and.blf.baking_app.ui.fragments;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.and.blf.baking_app.R;
import com.and.blf.baking_app.model.Step;
import com.and.blf.baking_app.ui.RecipeHostActivity;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class DetailStepFragment extends Fragment implements View.OnClickListener {
    private int mCurrentStepIndx;
    private SimpleExoPlayerView mPlayerView;
    private SimpleExoPlayer mExoPlayer;
    private int mCurrentWindow = 0 ;
    private long mPlaybackPosition = 0;
    private boolean mPlayWhenReady = true;
    private Uri mVideoUri;
    private final String BACK_BUTTON_TAG = "BACK_BUTTON_TAG";
    private final String NEXT_BUTTON_TAG = "NEXT_BUTTON_TAG";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_step,container,false);

        mCurrentStepIndx = getArguments().getInt("stepIndex");

        Button b = rootView.findViewById(R.id.previous_step_button);
        b.setOnClickListener(this);
        b.setTag(BACK_BUTTON_TAG);
        b = rootView.findViewById(R.id.next_step_button);
        b.setOnClickListener(this);
        b.setTag(NEXT_BUTTON_TAG);

        //((TextView)rootView.findViewById(R.id.description_tv)).setText(getIntent().getExtras().getString("step_description"));
        //mVideoUri = Uri.parse(getIntent().getExtras().getString("video_ulr"));
        mPlayerView = rootView.findViewById(R.id.playerView);
        initializePlayer(mVideoUri);
        mPlayerView.setPlayer(mExoPlayer);

        return  rootView;
    }

    @Override
    public void onClick(View v) {
        String tag = (String)v.getTag();
        if(tag.equals(NEXT_BUTTON_TAG)){
            mCurrentStepIndx++;
            switchStep();
        }else if(tag.equals(BACK_BUTTON_TAG)){
            mCurrentStepIndx--;
            switchStep();
        }
    }

    private void switchStep(){
        RecipeHostActivity hostActivity = (RecipeHostActivity)getActivity();
        Step curStep = hostActivity.getHostedRecipe().getSteps().get(mCurrentStepIndx);
        hostActivity.getSupportActionBar().setTitle(curStep.getShortDescription());
        //TODO: set other attributes of cur.step
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {

            mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getActivity()),
                    new DefaultTrackSelector(), new DefaultLoadControl());

            MediaSource mediaSource = buildMediaSource(mediaUri);
            mExoPlayer.prepare(mediaSource, true, false);

            mExoPlayer.setPlayWhenReady(mPlayWhenReady);
            mExoPlayer.seekTo(mCurrentWindow,mPlaybackPosition);
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory(Util.getUserAgent(getActivity(), getString(R.string.app_name)))).
                createMediaSource(uri);
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                //| View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                //| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                //| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer(mVideoUri);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT <= 23 || mExoPlayer == null)) {
            initializePlayer(mVideoUri);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mPlaybackPosition = mExoPlayer.getCurrentPosition();
            mCurrentWindow = mExoPlayer.getCurrentWindowIndex();
            mPlayWhenReady = mExoPlayer.getPlayWhenReady();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

}

