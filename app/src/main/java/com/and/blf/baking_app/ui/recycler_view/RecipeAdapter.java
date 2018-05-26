package com.and.blf.baking_app.ui.recycler_view;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.and.blf.baking_app.R;
import com.and.blf.baking_app.model.Recipe;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder>{
    private List<Recipe> mRecipeList;

    public RecipeAdapter(List<Recipe> recipeList){
        mRecipeList = recipeList;
        //mRecipeItemClickListener = recipeItemClickListener;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item, parent, false);
        layoutView.setTag(mRecipeList);
        return new RecipeViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.mRecipeName.setText(mRecipeList.get(position).getName());
        String imageURL = mRecipeList.get(position).getImageUrl();
        if (!(imageURL == null || imageURL.isEmpty())) {
            Picasso.with(holder.mRecipeImage.getContext())
                    .load(Uri.parse(imageURL))
                    .placeholder(R.drawable.cake)
                    .error(R.drawable.cake)
                    .into(holder.mRecipeImage);
        }
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    public void setRecipeList(Recipe[] recipeArray){
        mRecipeList = new ArrayList<>(Arrays.asList(recipeArray));
        notifyDataSetChanged();
    }

    public Recipe getRecipeByIndex(int index){
        return mRecipeList.get(index);
    }

}
