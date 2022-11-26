package com.example.getyourgroceries.entity;

import java.util.ArrayList;

/**
 * Create an object to represent a day of a meal plan.
 */
public class MealPlanDay {
    private String title;
    private ArrayList<ScaledRecipe> recipeList;
    private ArrayList<Ingredient> ingredientList;

    /**
     * Default constructor for MealPlanDays
     */
    public MealPlanDay(String title){
        this.title = title;
        this.recipeList = new ArrayList<>();
        this.ingredientList = new ArrayList<>();
    }
    public MealPlanDay(){}

   
    public void addRecipe(ScaledRecipe recipe){
        if (!recipeList.contains(recipe)) {
            recipeList.add(recipe);
        }
    }

    /**
     * Delete a recipe from a meal plan day.
     * @param recipe The recipe to delete.
     */
    public void deleteRecipe(ScaledRecipe recipe){
        recipeList.remove(recipe);
    }

    public void updateRecipe(ScaledRecipe recipe, int position) {
        recipeList.set(position, recipe);
    }

    
    /**
     * Add an ingredient to a meal plan day.
     * @param ingredient The ingredient to add.
     */
    public void addIngredient(Ingredient ingredient){
        if (!ingredientList.contains(ingredient)){
            ingredientList.add(ingredient);
        }
    }

    /**
     * Delete an ingredient from a meal plan day.
     * @param ingredient The ingredient to delete.
     */
    public void deleteIngredient(Ingredient ingredient){
        ingredientList.remove(ingredient);
    }

    /**
     * Get the recipes in a meal plan day.
     * @return The list of recipes.
     */
    public ArrayList<ScaledRecipe> getRecipeList() {
        return recipeList;
    }

    /**
     * Get the ingredients in a meal plan day.
     * @return The list of ingredients.
     */
    public ArrayList<Ingredient> getIngredientList() {
        return ingredientList;
    }

    /**
     * Get the title of the meal plan day.
     * @return The title of the meal plan day.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the title of the meal plan day.
     * @param title The title of the meal plan day.
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
