package com.and.blf.baking_app.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.and.blf.baking_app.model.Step;
import com.and.blf.baking_app.ui.IngredientsListActivity;
import com.and.blf.baking_app.R;
import com.and.blf.baking_app.model.Recipe;
import com.and.blf.baking_app.ui.MainRecipeListActivity;
import com.and.blf.baking_app.ui.RecipeHostActivity;
import com.and.blf.baking_app.ui.StepClickListener;
import com.and.blf.baking_app.ui.recycler_view.StepAdapter;

import java.util.ArrayList;

public class MasterListFragment extends Fragment {
    private Recipe mRecipe;

    protected RecyclerView mRecyclerView;
    protected LinearLayoutManager mLinearLayoutManager;

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
        Button showIngredientsBtn = getActivity().findViewById(R.id.ingredients_button);
        showIngredientsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), IngredientsListActivity.class);
                intent.putExtra(MainRecipeListActivity.RECIPE_PARCELABLE_TAG,mRecipe);
                startActivity(intent);
            }
        });

        mRecyclerView = getActivity().findViewById(R.id.rv_steps_list);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(new StepAdapter(new ArrayList(mRecipe.getSteps())));
    }


    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(mRecipe.getName());
    }
}