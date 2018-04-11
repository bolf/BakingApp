package com.and.blf.baking_app.ui.recycler_view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.and.blf.baking_app.R;
import com.and.blf.baking_app.ui.RecipeItemClickListener;

import java.lang.ref.WeakReference;

public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView mRecipeName;
    ImageView mRecipeImage;
    private WeakReference<RecipeItemClickListener> mRecipeItemClickListener;

    public RecipeViewHolder(View itemView, WeakReference<RecipeItemClickListener> recipeItemClickListener) {
        super(itemView);
        mRecipeImage = itemView.findViewById(R.id.recipe_img);
        mRecipeName  = itemView.findViewById(R.id.recipe_txt);
        itemView.setOnClickListener(this);
        mRecipeItemClickListener = recipeItemClickListener;
    }

    @Override
    public void onClick(View v) {
        mRecipeItemClickListener.get().onRecipeItemClicked(getAdapterPosition());
    }
}
