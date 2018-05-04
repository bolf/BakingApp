package com.and.blf.baking_app.utils;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import com.and.blf.baking_app.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class RecipeIngredientListItemFactory implements RemoteViewsFactory {
    List<String> mIngredientsList;
    Context mContext;
    int mWidgetID;

    public RecipeIngredientListItemFactory(Context context, Intent intent) {
        mContext = context;
        mWidgetID = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    void updateIngredientsList(){
        mIngredientsList.clear();
        mIngredientsList = SharedPreferencesUtils.readFavoriteRecipeDetailsFromSharedPreferences(
                mContext,
                mContext.getString(R.string.sharedPrefFileName),
                mContext.getString(R.string.favorite_recipe_ingredients_shared_pref_name),
                new HashSet<String>());
    }

    @Override
    public void onCreate() {
        mIngredientsList = new ArrayList<>();
    }

    @Override
    public void onDataSetChanged() {
        updateIngredientsList();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mIngredientsList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rView = new RemoteViews(mContext.getPackageName(),R.layout.widget_list_item);
        rView.setTextViewText(R.id.tvItemText, mIngredientsList.get(position));
        return rView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
