package com.and.blf.baking_app.utils;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.and.blf.baking_app.R;

public class IngredientsWidgetProvider extends AppWidgetProvider {

    void setList(RemoteViews rv, Context context, int appWidgetId) {
        Intent adapter = new Intent(context, RecipeIngredientsListRemoteViewService.class);
        adapter.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        rv.setRemoteAdapter(R.id.widgetLV, adapter);
    }

    void setTV(RemoteViews rv, Context context) {
        String recipeName = SharedPreferencesUtils.readFavoriteRecipeDetailsFromSharedPreferences(
                context,
                context.getString(R.string.sharedPrefFileName),
                context.getString(R.string.favorite_recipe_name_shared_pref_name),
                "");
        if(! recipeName.isEmpty()){
            recipeName = recipeName.concat(" ingredients:");
        }else{
            recipeName = "no favorite recipe found";
        }
        rv.setTextViewText(R.id.widgetNameTV,recipeName);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        RemoteViews rv = new RemoteViews(context.getPackageName(),
                R.layout.ingredients_widget);
        for (int appWidgetId : appWidgetIds) {
            setList(rv, context, appWidgetId);
            setTV(rv, context);
            appWidgetManager.updateAppWidget(appWidgetId, rv);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.widgetLV);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

