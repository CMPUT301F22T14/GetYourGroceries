package com.example.getyourgroceries.entity;

import java.util.ArrayList;

public class MealPlanDay {
    private String title;
    private ArrayList<Recipe> recipeList;
    private ArrayList<Ingredient> ingredientList;

    /**
     * Default constructor for MealPlanDays
     */
    public MealPlanDay(String title) {
        this.title = title;
        this.recipeList = new ArrayList<>();
        this.ingredientList = new ArrayList<>();
    }

<<<<<<< Updated upstream
    public void addRecipe(Recipe recipe){
=======
    public MealPlanDay() {}

    /**
     * Adds a recipe to a meal plan day
     *
     * @param recipe object to add
     */
    public void addRecipe(Recipe recipe) {
>>>>>>> Stashed changes
        if (!recipeList.contains(recipe)) {
            recipeList.add(recipe);
        }
    }

    /**
     * Deletes a recipe from a meal plan day
     *
     * @param recipe object to remove
     */
    public void deleteRecipe(Recipe recipe) {
        recipeList.remove(recipe);
    }

    /**
     * Adds an ingredient to a meal plan day
     *
     * @param ingredient object to add
     */
    public void addIngredient(Ingredient ingredient) {
        if (!ingredientList.contains(ingredient)) {
            ingredientList.add(ingredient);
        }
    }

    /**
     * Deletes an ingredient from a meal plan day
     *
     * @param ingredient object to remove
     */
    public void deleteIngredient(Ingredient ingredient) {
        ingredientList.remove(ingredient);
    }

    /**
     * Fetches the list of recipes on the meal plan day
     *
     * @return list of Recipe objects
     */
    public ArrayList<Recipe> getRecipeList() {
        return recipeList;
    }

    /**
     * Fetches the list of ingredients on the meal plan day
     *
     * @return list of Ingredient objects
     */
    public ArrayList<Ingredient> getIngredientList() {
        return ingredientList;
    }

    /**
     * Fetches the title of the day
     *
     * @return title of day
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the day
     *
     * @param title string to set to
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
