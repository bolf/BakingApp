package com.and.blf.baking_app.ui.recycler_view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.and.blf.baking_app.R;
import com.and.blf.baking_app.model.Recipe;
import com.and.blf.baking_app.ui.RecipeItemClickListener;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeViewHolder>{
    private List<Recipe> mRecipeList;
    private WeakReference<RecipeItemClickListener> mRecipeItemClickListener;

    public RecipeAdapter(List<Recipe> recipeList, WeakReference<RecipeItemClickListener> recipeItemClickListener){
        mRecipeList = recipeList;
        mRecipeItemClickListener = recipeItemClickListener;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item, parent, false);
        return new RecipeViewHolder(layoutView, mRecipeItemClickListener);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.mRecipeName.setText(mRecipeList.get(position).getName());
//        Picasso.with(holder.m_moviePosterThumbnail.getContext())
//                .load(MovieNetworkUtils.buildImageRequestUrl(holder.m_moviePosterThumbnail.getContext().getString(R.string.detailed_poster_size), m_movieList.get(position).getPosterPath()))
//                .into(holder.m_moviePosterThumbnail);
    }

    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }

    public void setRecipeList(Recipe[] recipeArray){
        mRecipeList = Arrays.asList(recipeArray);
        notifyDataSetChanged();
    }

}
