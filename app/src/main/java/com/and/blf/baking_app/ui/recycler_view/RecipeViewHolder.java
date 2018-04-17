package com.and.blf.baking_app.ui.recycler_view;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.and.blf.baking_app.R;
import com.and.blf.baking_app.model.Recipe;
import com.and.blf.baking_app.ui.MainRecipeListActivity;
import com.and.blf.baking_app.ui.RecipeHostActivity;

import java.util.ArrayList;

public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView mRecipeName;
    ImageView mRecipeImage;

    public RecipeViewHolder(View itemView) {
        super(itemView);
        mRecipeImage = itemView.findViewById(R.id.recipe_img);
        mRecipeName  = itemView.findViewById(R.id.recipe_txt);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //getAdapterPosition();
        Intent intent = new Intent(v.getContext(), RecipeHostActivity.class);
        intent.putExtra(MainRecipeListActivity.RECIPE_PARCELABLE_TAG, ((ArrayList<Recipe>)itemView.getTag()).get(getAdapterPosition()));
        v.getContext().startActivity(intent);
    }
}
