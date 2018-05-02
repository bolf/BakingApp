package com.and.blf.baking_app.ui;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.and.blf.baking_app.R;
import com.and.blf.baking_app.model.Recipe;
import com.and.blf.baking_app.ui.fragments.DetailStepFragment;
import com.and.blf.baking_app.ui.fragments.MasterListFragment;
import com.and.blf.baking_app.utils.SharedPreferencesUtils;
import com.google.android.exoplayer2.text.TextOutput;

public class RecipeHostActivity extends AppCompatActivity implements StepClickListener{
    Recipe mRecipe;
    boolean mTwoPane;
    Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //make transaction for inflating proper fragments
        setContentView(R.layout.activity_recipe_host);

        mRecipe = getIntent().getExtras().getParcelable(MainRecipeListActivity.RECIPE_PARCELABLE_TAG);

        mTwoPane = (findViewById(R.id.frame_divider) != null);

        Toolbar myToolbar = findViewById(R.id.activity_recipe_host_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(mRecipe.getName());

        if (mTwoPane) {

        } else {
            MasterListFragment masterListFragment = new MasterListFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_host_frame_layout, masterListFragment)
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.recipe_list_menu, menu);
        mMenu = menu;
        setMenuFavoriteIcon(mRecipe.getId());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_favorite) {
            SharedPreferencesUtils.writeToSharedPreferences(this, getString(R.string.favorite_racipe_shared_pref_name), getString(R.string.sharedPrefFileName), mRecipe.getId());
            setMenuFavoriteIcon(mRecipe.getId());
            Toast.makeText(this, mRecipe.getName().concat(" became favorite. It's ingredient list is shown in the widget."), Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setMenuFavoriteIcon(long curRecipeId){
        long favoriteRecipe = SharedPreferencesUtils.readFromSharedPreferences(this,getString(R.string.favorite_racipe_shared_pref_name),getString(R.string.sharedPrefFileName));
        if (favoriteRecipe == curRecipeId) {
            mMenu.getItem(0).setIcon(R.drawable.ic_star_gold_24dp);
        } else {
            mMenu.getItem(0).setIcon(R.drawable.ic_star_blue_24dp);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onStepClicked(int stepIndex) {
        if (mTwoPane) {

        } else {
            Bundle bundle = new Bundle();
            bundle.putString("step_description", mRecipe.getSteps().get(stepIndex).getDescription());
            bundle.putInt("stepIndex", stepIndex);
            bundle.putString("video_ulr", mRecipe.getSteps().get(stepIndex).getVideoURL());

            DetailStepFragment detailStepFragment = new DetailStepFragment();
            detailStepFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.recipe_host_frame_layout, detailStepFragment)
                    .commit();
        }
        getSupportActionBar().setTitle(mRecipe.getSteps().get(stepIndex).getShortDescription());
//        Intent intent = new Intent(this, DetailStepFragment.class);
//        intent.putExtras(bundle);
//        startActivity(intent);
//        Toast.makeText(this,String.valueOf(stepIndex),Toast.LENGTH_SHORT).show();
    }

    public Recipe getHostedRecipe() {
        return mRecipe;
    }
}