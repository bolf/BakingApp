package com.and.blf.baking_app.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.and.blf.baking_app.IngredientsListActivity;
import com.and.blf.baking_app.R;
import com.and.blf.baking_app.model.Recipe;
import com.and.blf.baking_app.ui.MainRecipeListActivity;
import com.and.blf.baking_app.ui.RecipeHostActivity;
import com.and.blf.baking_app.ui.StepClickListener;

public class MasterListFragment extends Fragment {
    Recipe mRecipe;
    ListView mStepsListView;
    ArrayAdapter<String>mStepArrayAdapter;
    Button showIngredientsBtn;

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
        mStepsListView = getView().findViewById(R.id.steps_list);
        populateStepList();
        showIngredientsBtn = getActivity().findViewById(R.id.ingredients_button);
        showIngredientsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), IngredientsListActivity.class);
                intent.putExtra(MainRecipeListActivity.RECIPE_PARCELABLE_TAG,mRecipe);
                startActivity(intent);
            }
        });
    }

    private void populateStepList() {
        String[] stepsDescriptions = new String[mRecipe.getSteps().size()];
        for(int i = 0; i < mRecipe.getSteps().size(); i++){
            stepsDescriptions[i] = String.valueOf(i+1) + ". " + mRecipe.getSteps().get(i).getShortDescription();
        }
        mStepArrayAdapter = new ArrayAdapter<>(getContext(),R.layout.step_view,stepsDescriptions);
        mStepsListView.setAdapter(mStepArrayAdapter);

        mStepsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(mRecipe.getName());
    }
}