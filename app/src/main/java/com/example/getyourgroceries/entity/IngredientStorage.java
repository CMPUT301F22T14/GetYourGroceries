package com.example.getyourgroceries.entity;
// Import statements.
import android.content.Context;
import android.widget.ArrayAdapter;
import com.example.getyourgroceries.IngredientStorageAdapter;
import java.util.ArrayList;

public class IngredientStorage {

    // Attributes for ingredient storage
    // static because there will only be 1 storage
    private static ArrayList<StoredIngredient> ingredientStorage = new ArrayList<>();
    private static ArrayAdapter<StoredIngredient> ingredientAdapter;

    //setup initial adapter
    public static void setupAdapter(Context context){
        ingredientAdapter = new IngredientStorageAdapter(context, ingredientStorage);
    }

    /**
     * Add an ingredient to the ingredient storage
     * @param ingredient to store
     */
    public static void addIngredient(StoredIngredient ingredient){
        ingredientAdapter.add(ingredient);
        ingredientAdapter.notifyDataSetChanged();
    }

    /**
     * Retrieve stored ingredient for editing
     * @param i index of ingredient to get
     * @return Ingredient object
     */
    public static StoredIngredient getIngredient(int i){
        return ingredientAdapter.getItem(i);
    }

    /**
     * Clear all ingredients from the ingredient storage
     */
    public static void clearIngredients(){
        ingredientAdapter.clear();
        ingredientAdapter.notifyDataSetChanged();
    }

    /**
     * retrieve adapter (to interface with listviews)
     * @return ArrayAdapter representing ingredient storage
     */
    public static ArrayAdapter<StoredIngredient> getIngredientAdapter(){
        return ingredientAdapter;
    }

    public static void deleteIngredient(StoredIngredient ingredient) {
        if(!ingredientStorage.contains(ingredient)) {
            throw new IllegalArgumentException();
        }
        ingredientAdapter.remove(ingredient);
        ingredientAdapter.notifyDataSetChanged();
    }
}
