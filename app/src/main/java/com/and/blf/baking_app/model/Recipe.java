package com.and.blf.baking_app.model;

import java.util.List;

public class Recipe {
    private long id;
    private String name;
    private String imageUrl;
    private int servings;
    private List<Ingredient> ingredients;
    private List<Step> steps;

    public Recipe(long id, String name, String imageUrl, int servings, List<Ingredient> ingredients, List<Step> steps) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.servings = servings;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }
}