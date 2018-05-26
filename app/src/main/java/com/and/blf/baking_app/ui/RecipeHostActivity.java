package com.and.blf.baking_app.ui;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.and.blf.baking_app.R;
import com.and.blf.baking_app.model.Recipe;
import com.and.blf.baking_app.model.Step;
import com.and.blf.baking_app.ui.fragments.DetailStepFragment;
import com.and.blf.baking_app.ui.fragments.MasterListFragment;
import com.and.blf.baking_app.utils.IngredientsWidgetProvider;
import com.and.blf.baking_app.utils.SharedPreferencesUtils;

public class RecipeHostActivity extends AppCompatActivity implements StepClickListener{
    public Recipe mRecipe;
    boolean mTwoPane;
    Menu mMenu;
    public Step mCurrStep;
    private StepClickListener mDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_host);

        if (savedInstanceState != null) {
            mCurrStep = savedInstanceState.getParcelable("mCurrStep");
        }

        mRecipe = getIntent().getExtras().getParcelable(MainRecipeListActivity.RECIPE_PARCELABLE_TAG);

        mTwoPane = (findViewById(R.id.frame_divider) != null);

        Toolbar myToolbar = findViewById(R.id.activity_recipe_host_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(mRecipe.getName());

        if (mTwoPane) {

        } else {
            if (mCurrStep == null) {
                MasterListFragment masterListFragment = new MasterListFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.recipe_host_frame_layout, masterListFragment)
                        .commit();}
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("mCurrStep",mCurrStep);
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
        switch (item.getItemId()) {
            case R.id.action_favorite: {
                SharedPreferencesUtils.writeFavoriteRecipeDetailsToSharedPreferences(
                        this,
                        getString(R.string.sharedPrefFileName),
                        getString(R.string.favorite_recipe_id_shared_pref_name),
                        mRecipe.getId(),
                        getString(R.string.favorite_recipe_ingredients_shared_pref_name),
                        mRecipe.getIngredientsNamesSet(),
                        getString(R.string.favorite_recipe_name_shared_pref_name),
                        mRecipe.getName());

                setMenuFavoriteIcon(mRecipe.getId());
                Toast.makeText(this, mRecipe.getName().concat(" became favorite. It's ingredient list will be shown in the widget."), Toast.LENGTH_LONG).show();

                updateRecipeInWidget();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateRecipeInWidget() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int appWidgetIds[] = appWidgetManager.getAppWidgetIds(
                new ComponentName(this, IngredientsWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widgetLV);

        Intent intent = new Intent(this, IngredientsWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ids = AppWidgetManager.getInstance(getApplication()).
                getAppWidgetIds(new ComponentName(getApplication(), IngredientsWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(intent);
    }

    private void setMenuFavoriteIcon(long curRecipeId){
        long favoriteRecipe = SharedPreferencesUtils.readFavoriteRecipeDetailsFromSharedPreferences(
                this,
                getString(R.string.sharedPrefFileName),
                getString(R.string.favorite_recipe_id_shared_pref_name),
                0);

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
        mCurrStep = mRecipe.getSteps().get(stepIndex);
        if (mTwoPane) {
            mDetailFragment.onStepClicked(stepIndex);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("step_description", mCurrStep.getDescription());
            bundle.putInt("stepIndex", stepIndex);
            bundle.putString("video_ulr", mCurrStep.getVideoURL());
            bundle.putInt("maxStepNum", mRecipe.getSteps().size());

            DetailStepFragment detailStepFragment = new DetailStepFragment();
            detailStepFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.recipe_host_frame_layout, detailStepFragment)
                    .commit();
        }
        getSupportActionBar().setTitle(mRecipe.getSteps().get(stepIndex).getShortDescription());
    }

    public Recipe getHostedRecipe() {
        return mRecipe;
    }

    public void set_mDetailfargment(StepClickListener mDetailFragment){
        this.mDetailFragment = mDetailFragment;
    }
}