package com.and.blf.baking_app.ui;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.and.blf.baking_app.R;
import com.and.blf.baking_app.model.Recipe;
import com.and.blf.baking_app.ui.recycler_view.RecipeAdapter;
import com.and.blf.baking_app.utils.RecipeRetrofitService;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRecipeListActivity extends AppCompatActivity {
    public final static String RECIPE_PARCELABLE_TAG = "RECIPE_PARCELABLE_TAG";

    private RecipeRetrofitService mRecipeRetrofitService;

    protected RecyclerView mRecyclerView;
    protected GridLayoutManager mGridLayoutManager;

    protected RecipeAdapter mRecipeAdapter = new RecipeAdapter(new ArrayList<Recipe>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recipe_list);

        Toolbar myToolbar = findViewById(R.id.main_recipe_list_tool_bar);
        setSupportActionBar(myToolbar);

        mRecipeRetrofitService = RecipeRetrofitService.utils.getRecipeRetrofitService();

        mRecyclerView = findViewById(R.id.rv_main_list);
        mGridLayoutManager = new GridLayoutManager(this,getGridLayoutColumnCount());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(mRecipeAdapter);

        loadRecipes();
    }

    private int getGridLayoutColumnCount() {
        //TODO implement counting amount of columns
        return 1;
    }

    private void loadRecipes() {
        if(! RecipeRetrofitService.utils.networkIsAvailable(this)){
            //set visibility of the indicator
            //show toast "net is not available"
            return;
        }

        Call<Recipe[]> recipesArrayCall = mRecipeRetrofitService.getAllRecipes();

        recipesArrayCall.enqueue(new Callback<Recipe[]>() {
            @Override
            public void onResponse(@NonNull Call<Recipe[]> call, @NonNull Response<Recipe[]> response) {
                try {
                    mRecipeAdapter.setRecipeList(response.body());
                }catch (NullPointerException e){
                    Log.d(getString(R.string.getingAllRecipesExceptionTag), e.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull  Call<Recipe[]> call, @NonNull Throwable t) {
                Log.d(getString(R.string.getingAllRecipesExceptionTag), t.getMessage());
            }
        });
    }
}
