package com.and.blf.baking_app.utils;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class RecipeIngredientsListRemoteViewService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeIngredientListItemFactory(getApplicationContext(), intent);
    }
}
