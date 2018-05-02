package com.and.blf.baking_app.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtils {
    public static void writeToSharedPreferences(Context context, String shPrefName, String prefName, Long prefVal){
        SharedPreferences sharedPref = context.getSharedPreferences(shPrefName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(prefName, prefVal);
        editor.apply();
    }

    public static Long readFromSharedPreferences(Context context, String shPrefName, String prefName) {
        SharedPreferences sharedPref = context.getSharedPreferences(shPrefName,Context.MODE_PRIVATE);
        return sharedPref.getLong(prefName,0l);
    }
}
