package com.and.blf.baking_app.model;

import java.util.List;

public class Recipe {
    private long mId;
    private String mName;
    private String mImageUrl;
    private int mServings;
    private List<Ingredient> mIngredients;
    private List<Step> mSteps;

    public Recipe(long id, String name, String imageUrl, int servings, List<Ingredient> ingredients, List<Step> steps) {
        mId = id;
        mName = name;
        mImageUrl = imageUrl;
        mServings = servings;
        mIngredients = ingredients;
        mSteps = steps;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public int getServings() {
        return mServings;
    }

    public void setServings(int servings) {
        mServings = servings;
    }

    public List<Ingredient> getIngredients() {
        return mIngredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        mIngredients = ingredients;
    }

    public List<Step> getSteps() {
        return mSteps;
    }

    public void setSteps(List<Step> steps) {
        mSteps = steps;
    }
}
