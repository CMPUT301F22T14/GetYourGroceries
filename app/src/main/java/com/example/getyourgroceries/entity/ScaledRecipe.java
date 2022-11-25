package com.example.getyourgroceries.entity;

public class ScaledRecipe extends Recipe {

    private Recipe recipe;
    private int scale;

    public ScaledRecipe(Recipe recipe, int scale) {
        this.recipe = recipe;
        this.scale = scale;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public int getScale() {
        return scale;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }
}
