package com.and.blf.baking_app.ui;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.and.blf.baking_app.R;
import com.and.blf.baking_app.model.Recipe;
import com.and.blf.baking_app.ui.fragments.DetailStepFragment;
import com.and.blf.baking_app.ui.fragments.MasterListFragment;
import com.google.android.exoplayer2.text.TextOutput;

public class RecipeHostActivity extends AppCompatActivity implements StepClickListener{
    Recipe mRecipe;
    boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //make transaction for inflating proper fragments
        setContentView(R.layout.activity_recipe_host);

        mTwoPane = (findViewById(R.id.frame_divider) != null);

        Toolbar myToolbar = findViewById(R.id.activity_recipe_host_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (mTwoPane) {

        } else {


            MasterListFragment masterListFragment = new MasterListFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_host_frame_layout, masterListFragment)
                    .commit();
        }
        mRecipe = getIntent().getExtras().getParcelable(MainRecipeListActivity.RECIPE_PARCELABLE_TAG);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onStepClicked(int stepIndex) {
        Bundle bundle = new Bundle();
        bundle.putString("step_description",mRecipe.getSteps().get(stepIndex).getDescription());
        bundle.putString("video_ulr",mRecipe.getSteps().get(stepIndex).getVideoURL());

        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.recipe_host_frame_layout, new DetailStepFragment())
                .commit();

//        Intent intent = new Intent(this, DetailStepFragment.class);
//        intent.putExtras(bundle);
//        startActivity(intent);
//        Toast.makeText(this,String.valueOf(stepIndex),Toast.LENGTH_SHORT).show();
    }

    public Recipe getHostedRecipe() {
        return mRecipe;
    }
}