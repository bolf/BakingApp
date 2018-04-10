package com.and.blf.baking_app.ui.recycler_view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.and.blf.baking_app.R;
import com.and.blf.baking_app.model.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder>{
    private List<Recipe> mRecipeList;

    public RecipeAdapter(List<Recipe> recipeList){
        mRecipeList = recipeList;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item, parent, false);
        return new RecipeViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }
}
