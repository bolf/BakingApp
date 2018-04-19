package com.and.blf.baking_app.ui.fragments;

import android.content.Context;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.and.blf.baking_app.R;
import com.and.blf.baking_app.model.Recipe;
import com.and.blf.baking_app.ui.RecipeHostActivity;
import com.and.blf.baking_app.ui.StepClickListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 */
public class MasterListFragment extends Fragment {

    Recipe mRecipe;

    ListView stepsListView;
    ArrayAdapter<String>mStepArrayAdapter;

    public MasterListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecipe = ((RecipeHostActivity)getActivity()).getHostedRecipe();
        stepsListView = getView().findViewById(R.id.steps_list);
        populateStepList();
    }

    private void populateStepList() {
        String[] stepsDescriptions = new String[mRecipe.getSteps().size()];
        for(int i = 0; i < mRecipe.getSteps().size(); i++){
            stepsDescriptions[i] = String.valueOf(i+1) + ". " + mRecipe.getSteps().get(i).getShortDescription();
        }
        mStepArrayAdapter = new ArrayAdapter<>(getContext(),R.layout.step_view,stepsDescriptions);
        stepsListView.setAdapter(mStepArrayAdapter);

        stepsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((StepClickListener)getActivity()).onStepClicked(position);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof RecipeClickListener) {
//            mRecipeClickListener = (RecipeClickListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement RecipeClickListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }


}