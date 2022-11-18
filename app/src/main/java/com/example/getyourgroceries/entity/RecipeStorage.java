package com.example.getyourgroceries.entity;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.getyourgroceries.R;
import com.example.getyourgroceries.adapters.RecipeAdapter;
import com.example.getyourgroceries.control.RecipeDB;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Create an object to represent a list of recipes.
 */
public class RecipeStorage {
    private static final RecipeStorage instance = new RecipeStorage();

    private RecipeStorage() {
        super();
    }

    private ArrayList<Recipe> recipeStorage = new ArrayList<>();
    private ArrayAdapter<Recipe> recipeAdapter;
    private RecipeDB recipeDB;

    public static RecipeStorage getInstance() {
        return instance;
    }

    public ArrayAdapter<Recipe> setupStorage(Context context) {
        recipeAdapter = new RecipeAdapter(context, recipeStorage);
        this.recipeDB = new RecipeDB();
        return recipeAdapter;
    }

    public void addRecipe(Recipe recipe, boolean toDB) {
        recipeAdapter.add(recipe);

        if(toDB) {
            recipeDB.addRecipe(recipe);
        }

        recipeAdapter.notifyDataSetChanged();
    }

    public Recipe getRecipe(int i) {
        return recipeAdapter.getItem(i);
    }

    public void updateRecipe(Recipe recipe) {
        recipeDB.updateRecipe(recipe);
    }

    public void deleteRecipe(Recipe recipe, boolean toDB) {
        recipeAdapter.remove(recipe);

        if(toDB) {
            recipeDB.deleteRecipe(recipe);
        }

        recipeAdapter.notifyDataSetChanged();
    }

    public ArrayList<Recipe> getRecipeList() {
        return recipeStorage;
    }

    public void clearLocalStorage() {
        recipeAdapter.clear();
        recipeAdapter.notifyDataSetChanged();
    }

    public void sortCategory(int type,boolean desc) {
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
}
