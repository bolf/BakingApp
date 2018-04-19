package com.and.blf.baking_app.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.and.blf.baking_app.R;
import com.and.blf.baking_app.model.Recipe;
import com.and.blf.baking_app.ui.fragments.DetailStepFragment;

public class RecipeHostActivity extends AppCompatActivity implements StepClickListener{
    Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //make transaction for inflating proper fragments
        setContentView(R.layout.activity_recipe_host);
        mRecipe = getIntent().getExtras().getParcelable(MainRecipeListActivity.RECIPE_PARCELABLE_TAG);
    }

    @Override
    public void onStepClicked(int stepIndex) {
        Bundle bundle = new Bundle();
        bundle.putString("step_description",mRecipe.getSteps().get(stepIndex).getDescription());
        bundle.putString("video_ulr",mRecipe.getSteps().get(stepIndex).getVideoURL());

        Intent intent = new Intent(this, DetailStepFragment.class);
        intent.putExtras(bundle);
        startActivity(intent);
        Toast.makeText(this,String.valueOf(stepIndex),Toast.LENGTH_SHORT).show();
    }

    public Recipe getHostedRecipe() {
        return mRecipe;
    }
}