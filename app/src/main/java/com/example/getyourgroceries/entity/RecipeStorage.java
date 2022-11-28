package com.example.getyourgroceries.entity;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.getyourgroceries.adapters.RecipeAdapter;
import com.example.getyourgroceries.control.RecipeDB;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Create an object to represent a list of recipes.
 */
public class RecipeStorage {
    // Actual singleton instance
    private static final RecipeStorage instance = new RecipeStorage();

    // private constructor to enforce singleton
    private RecipeStorage() {
        super();
    }

    private ArrayList<Recipe> recipeStorage = new ArrayList<>();
    private ArrayAdapter<Recipe> recipeAdapter;
    private RecipeDB recipeDB;

    /**
     * retrieves the singleton instance
     *
     * @return the instance
     */
    public static RecipeStorage getInstance() {
        return instance;
    }

    /**
     * Sets up storage adapter using the context
     *
     * @param context to connect to
     * @return the connected array adapter
     */
    public void setupStorage(Context context) {
        recipeAdapter = new RecipeAdapter(context, recipeStorage);
        this.recipeDB = new RecipeDB();
    }

    /**
     * Gets the associated adapter
     *
     * @return the recipe adapter
     */
    public ArrayAdapter<Recipe> getRecipeAdapter() {
        return recipeAdapter;
    }

    /**
     * Add recipe to recipe storage
     *
     * @param recipe to add
     * @param toDB   determines to add or not to the database
     */
    public void addRecipe(Recipe recipe, boolean toDB) {
        if (toDB) {
            recipe.setId(recipeDB.addRecipe(recipe));
        }
        recipeAdapter.add(recipe);

        recipeAdapter.notifyDataSetChanged();
    }

    /**
     * Retrieves a recipe from the storage
     *
     * @param i index of recipe
     * @return the recipe at the index
     */
    public Recipe getRecipe(int i) {
        return recipeAdapter.getItem(i);
    }

    /**
     * Updates a recipe from the storage
     *
     * @param recipe to update
     */
    public void updateRecipe(Recipe recipe) {
        recipeDB.updateRecipe(recipe);
    }

    /**
     * Deletes a recipe from storage
     *
     * @param recipe to delete
     * @param toDB   determines to delete or not from the database
     */
    public void deleteRecipe(Recipe recipe, boolean toDB) {
        recipeAdapter.remove(recipe);

        if (toDB) {
            recipeDB.deleteRecipe(recipe);
        }

        recipeAdapter.notifyDataSetChanged();
    }

    /**
     * Retrieves the list of recipes
     *
     * @return list
     */
    public ArrayList<Recipe> getRecipeList() {
        return recipeStorage;
    }

    /**
     * Clear the local(non-database data) adapter information
     */
    public void clearLocalStorage() {
        recipeAdapter.clear();
        recipeAdapter.notifyDataSetChanged();
    }

    /**
     * Sorts the recipe list
     *
     * @param type which type to sort by
     * @param desc descending or ascending
     */
    public void sortCategory(int type, boolean desc) {
        switch (type) {
            case 0:
                if (desc) {
                    recipeAdapter.sort((o1, o2) -> o1.getName().compareTo(o2.getName()) * -1);
                } else {
                    recipeAdapter.sort(Comparator.comparing(Recipe::getName));
                }
                recipeAdapter.notifyDataSetChanged();
                break;

            case 1:
                if (desc) {
                    recipeAdapter.sort(Comparator.comparingInt(Recipe::getPrepTime).reversed());
                } else {
                    recipeAdapter.sort(Comparator.comparingInt(Recipe::getPrepTime));
                }
                recipeAdapter.notifyDataSetChanged();
                break;
            case 2:
                if (desc) {
                    recipeAdapter.sort(Comparator.comparingInt(Recipe::getNumOfServings).reversed());
                } else {
                    recipeAdapter.sort(Comparator.comparingInt(Recipe::getNumOfServings));
                }
                recipeAdapter.notifyDataSetChanged();
                break;
            case 3:
                if (desc) {
                    recipeAdapter.sort((o1, o2) -> o1.getRecipeCategory().compareTo(o2.getRecipeCategory()) * -1);
                } else {
                    recipeAdapter.sort(Comparator.comparing(Recipe::getRecipeCategory));
                }
                recipeAdapter.notifyDataSetChanged();
                break;
        }
    }

    /**
     * Update local storage based on remote database
     */
    public void updateStorage(){
        recipeDB.refreshStorage();
    }
}
