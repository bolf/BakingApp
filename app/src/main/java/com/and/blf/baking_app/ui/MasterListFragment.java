package com.and.blf.baking_app.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.and.blf.baking_app.R;
import com.and.blf.baking_app.model.Recipe;
import com.and.blf.baking_app.ui.recycler_view.RecipeAdapter;
import com.and.blf.baking_app.utils.RecipeRetrofitService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MasterListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class MasterListFragment extends Fragment {

    //private OnFragmentInteractionListener mListener;
    private static final String TAG = "RecyclerViewFragment";

    private RecipeRetrofitService mRecipeRetrofitService;

    protected RecyclerView mRecyclerView;
    protected GridLayoutManager mGridLayoutManager;

    protected RecipeAdapter mRecipeAdapter = new RecipeAdapter(new ArrayList<Recipe>());
    protected Recipe[] mRecipeSet;


    public MasterListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRecipeRetrofitService = RecipeRetrofitService.utils.getRecipeRetrofitService();

        initDataSet();
    }

    private void initDataSet() {
        if(! RecipeRetrofitService.utils.networkIsAvailable(getContext())){
            //set visibility of the indicator
            //show toast "net is not available"
            return;
        }

        Call<Recipe[]> recipesArrayCall = mRecipeRetrofitService.getAllRecipes();

        recipesArrayCall.enqueue(new Callback<Recipe[]>() {
            @Override
            public void onResponse(@NonNull Call<Recipe[]> call, @NonNull Response<Recipe[]> response) {
                try {
                    Recipe[] mRecipeSet = response.body();

                }catch (NullPointerException e){
                    Log.d(getString(R.string.getingAllRecipesExceptionTag), e.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull  Call<Recipe[]> call, @NonNull Throwable t) {
                Log.d(getString(R.string.getingAllRecipesExceptionTag), t.getMessage());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);
        rootView.setTag(TAG);

        mRecyclerView = rootView.findViewById(R.id.rv_main_list);
        mGridLayoutManager = new GridLayoutManager(getActivity(),2);
        mRecyclerView.setAdapter(mRecipeAdapter);

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
