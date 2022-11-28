package com.example.getyourgroceries.entity;

import java.util.ArrayList;

public class ShoppingList {
    private String name;
    private ArrayList<Ingredient> ingredientList;

    /**
     * Constructor for shopping list to initialize an empty ingredient
     *
     * @param name shopping list name
     */
    public ShoppingList(String name, ArrayList<Ingredient> ingredients) {
        this.name = name;
        ingredientList = ingredients;
    }

    /**
     * Name getter
     *
     * @return shopping list name
     */
    public String getName() {
        return name;
    }

    /**
     * Updates shopping list name
     *
     * @param name shopping list name
     */
    public void editName(String name) {
        this.name = name;
    }

    /**
     * Gets the total shopping list
     *
     * @return list of ingredients
     */
    public ArrayList<Ingredient> getIngredientList() {
        return ingredientList;
    }

    /**
     * Adds ingredient to shopping list
     *
     * @param ingredient new ingredient to add
     */
    public void addIngredient(Ingredient ingredient) {
        ingredientList.add(ingredient);
    }

    /**
     * Deletes ingredient from shopping list
     *
     * @param ingredient to add
     */
    public void deleteIngredient(Ingredient ingredient) {
        if (!ingredientList.contains(ingredient)) {
            throw new IllegalArgumentException();
        }
        ingredientList.remove(ingredient);
    }
}
