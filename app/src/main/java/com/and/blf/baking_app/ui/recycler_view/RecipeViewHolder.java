package com.and.blf.baking_app.ui.recycler_view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.and.blf.baking_app.R;

public class RecipeViewHolder extends RecyclerView.ViewHolder {

    TextView mRecipeName;
    ImageView mRecipeImage;

    public RecipeViewHolder(View itemView) {
        super(itemView);
        mRecipeImage = itemView.findViewById(R.id.recipe_img);
        mRecipeName  = itemView.findViewById(R.id.recipe_txt);
    }
}
