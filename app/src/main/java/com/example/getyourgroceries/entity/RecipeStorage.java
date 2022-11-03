package com.example.getyourgroceries.entity;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Create an object to represent a list of recipes.
 */
public class RecipeStorage {
    public static ArrayList<Recipe> recipeStorage = new ArrayList<>();
    public static ArrayAdapter<Recipe> recipeAdapter;
}
