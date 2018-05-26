package com.and.blf.baking_app.ui;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.and.blf.baking_app.R;
import com.and.blf.baking_app.model.Recipe;
import com.and.blf.baking_app.utils.IngredientsWidgetProvider;
import com.and.blf.baking_app.utils.SharedPreferencesUtils;

import java.util.Set;

public class IngredientsListActivity extends AppCompatActivity {
    private Recipe mRecipe;
    ListView mStepsListView;
    ArrayAdapter<String>mStepArrayAdapter;
    Menu mMenu;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_list);

        mRecipe = getIntent().getExtras().getParcelable(MainRecipeListActivity.RECIPE_PARCELABLE_TAG);
        mStepsListView = findViewById(R.id.ingredients_LV);

        populateStepList();

        Toolbar myToolbar = findViewById(R.id.activity_ingredients_list_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(mRecipe.getName().concat(" ingredients"));
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
        switch(item.getItemId()){
            case R.id.action_favorite:{
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
                Toast.makeText(this, mRecipe.getName().concat(getString(R.string.ingr_will_be_shown_in_widget)), Toast.LENGTH_LONG).show();

                updateRecipeInWidget();
                return true;
            }
            case android.R.id.home:
                onBackPressed();
                return true;
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

    private void populateStepList() {
        Set<String> ingredientsNamesSet = mRecipe.getIngredientsNamesSet();
        String[] namesArray = ingredientsNamesSet.toArray(new String[ingredientsNamesSet.size()]);
        mStepArrayAdapter = new ArrayAdapter<>(this,R.layout.step_view,namesArray);
        mStepsListView.setAdapter(mStepArrayAdapter);
    }
}
