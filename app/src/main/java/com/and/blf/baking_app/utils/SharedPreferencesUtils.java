package com.and.blf.baking_app.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class SharedPreferencesUtils {
    private SharedPreferencesUtils() {}

    public static void writeFavoriteRecipeDetailsToSharedPreferences(Context context, String shPrefName,
                                                                     String prefIdName, long recipeId,
                                                                     String prefSetName, Set<String> ingredientsSet,
                                                                     String prefRecipeName, String recipeName){
        SharedPreferences sharedPref = context.getSharedPreferences(shPrefName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putLong(prefIdName, recipeId).apply();

        editor.putString(prefRecipeName, recipeName).apply();

        editor.putStringSet(prefSetName, ingredientsSet).commit();
    }

    public static List<String> readFavoriteRecipeDetailsFromSharedPreferences(Context context, String shPrefName, String prefName, Set<String> defaultValue) {
        SharedPreferences sharedPref = context.getSharedPreferences(shPrefName,Context.MODE_PRIVATE);
        return new ArrayList<>(sharedPref.getStringSet(prefName,defaultValue));
    }

    public static long readFavoriteRecipeDetailsFromSharedPreferences(Context context, String shPrefName, String prefName, long defaultValue) {
        SharedPreferences sharedPref = context.getSharedPreferences(shPrefName,Context.MODE_PRIVATE);
        return sharedPref.getLong(prefName,defaultValue);
    }

    public static String readFavoriteRecipeDetailsFromSharedPreferences(Context context, String shPrefName, String prefName, String defaultValue) {
        SharedPreferences sharedPref = context.getSharedPreferences(shPrefName,Context.MODE_PRIVATE);
        return sharedPref.getString(prefName,defaultValue);
    }

}
