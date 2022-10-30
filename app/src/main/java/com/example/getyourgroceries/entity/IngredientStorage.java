/* IngredientStorage class. */
package com.example.getyourgroceries.entity;

// Import statements.
import android.widget.ArrayAdapter;
import java.util.ArrayList;

/**
 * Create an object to represent a list of ingredients.
 * TODO: Add sorting.
 */
public class IngredientStorage {

    // Attributes.
    public static ArrayList<StoredIngredient> ingredientStorage = new ArrayList<>();
    public static ArrayAdapter<StoredIngredient> ingredientAdapter;
}
