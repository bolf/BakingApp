package com.and.blf.baking_app.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.and.blf.baking_app.model.Recipe;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.GET;

public interface RecipeRetrofitService {
    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<Recipe[]> getAllRecipes();

    //static class for obtaining RetrofitService instance
    public static class utils{
        static final String RECIPE_URL = "https://d17h27t6h515a5.cloudfront.net/";

        public static RecipeRetrofitService getRecipeRetrofitService(){
            return new Retrofit.Builder()
                    .baseUrl(RECIPE_URL)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .client(getHttpClient())
                    .build()
                    .create(RecipeRetrofitService.class);
        }

        static OkHttpClient getHttpClient() {
            return new OkHttpClient.Builder()
                    .connectTimeout(3, TimeUnit.SECONDS)
                    .writeTimeout(3, TimeUnit.SECONDS)
                    .readTimeout(3, TimeUnit.SECONDS)
                    .build();
        }

        public static boolean networkIsAvailable(Context context) {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }

    }

}
