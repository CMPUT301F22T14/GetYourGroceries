package com.example.getyourgroceries.entity;

import java.util.ArrayList;

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

    public MealPlanDay(){

    }

    public void addRecipe(ScaledRecipe recipe){
        if (!recipeList.contains(recipe)) {
            recipeList.add(recipe);
        }
    }

    public void deleteRecipe(ScaledRecipe recipe){
        recipeList.remove(recipe);
    }

    public void addIngredient(Ingredient ingredient){
        if (!ingredientList.contains(ingredient)){
            ingredientList.add(ingredient);
        }
    }
    public void deleteIngredient(Ingredient ingredient){
        ingredientList.remove(ingredient);
    }

    public ArrayList<ScaledRecipe> getRecipeList() {
        return recipeList;
    }

    public ArrayList<Ingredient> getIngredientList() {
        return ingredientList;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
