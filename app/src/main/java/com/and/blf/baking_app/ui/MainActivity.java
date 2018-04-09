package com.and.blf.baking_app.ui;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.and.blf.baking_app.R;
import com.and.blf.baking_app.model.Recipe;
import com.and.blf.baking_app.utils.RecipeRetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecipeRetrofitService mRecipeRetrofitService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecipeRetrofitService = RecipeRetrofitService.utils.getRecipeRetrofitService();

        loadRecipes();
    }

    private void loadRecipes(){
        Call<Recipe[]> recipesArrayCall = mRecipeRetrofitService.getAllRecipes();

        recipesArrayCall.enqueue(new Callback<Recipe[]>() {
            @Override
            public void onResponse(@NonNull Call<Recipe[]> call, @NonNull Response<Recipe[]> response) {
                try {
                    Recipe[] recipesArray = response.body();

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
