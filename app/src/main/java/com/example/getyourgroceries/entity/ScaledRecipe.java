package com.example.getyourgroceries.entity;

/**
 * Recipe that gets scaled on meal plans
 */
public class ScaledRecipe extends Recipe {
    private Recipe recipe;
    private int scale;

    /**
     * Empty constructor
     */
    public ScaledRecipe() {}

    /**
     * Constructor
     * @param recipe sets internal recipe object
     * @param scale number scale of the recipe
     */
    public ScaledRecipe(Recipe recipe, int scale) {
        this.recipe = recipe;
        this.scale = scale;
    }

    /**
     * Fetches recipe
     * @return recipe object
     */
    public Recipe getRecipe() {
        return recipe;
    }

    /**
     * Fetches scale of recipe
     * @return number scale
     */
    public int getScale() {
        return scale;
    }

    /**
     * Sets the internal recipe
     * @param recipe object to set to
     */
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    /**
     * Sets the scale of the recipe
     * @param scale number to set to
     */
    public void setScale(int scale) {
        this.scale = scale;
    }
}
