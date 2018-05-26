package com.and.blf.baking_app.ui.fragments;

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

import com.and.blf.baking_app.ui.IngredientsListActivity;
import com.and.blf.baking_app.R;
import com.and.blf.baking_app.model.Recipe;
import com.and.blf.baking_app.ui.MainRecipeListActivity;
import com.and.blf.baking_app.ui.RecipeHostActivity;
import com.and.blf.baking_app.ui.StepClickListener;

public class MasterListFragment extends Fragment {
    private Recipe mRecipe;
    private ListView mStepsListView;

    public MasterListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecipe = ((RecipeHostActivity)getActivity()).getHostedRecipe();
        mStepsListView = getView().findViewById(R.id.steps_list);
        populateStepList();
        Button showIngredientsBtn = getActivity().findViewById(R.id.ingredients_button);
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
            stepsDescriptions[i] = mRecipe.getSteps().get(i).getShortDescription();
        }
        ArrayAdapter<String> stepArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.step_view, stepsDescriptions);
        mStepsListView.setAdapter(stepArrayAdapter);

        mStepsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((StepClickListener)getActivity()).onStepClicked(position);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(mRecipe.getName());
    }
}