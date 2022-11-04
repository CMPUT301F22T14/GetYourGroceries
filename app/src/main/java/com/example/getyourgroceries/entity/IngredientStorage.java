/* IngredientStorage class. */
package com.example.getyourgroceries.entity;

// Import statements.
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Create an object to represent a list of ingredients.
 */
public class IngredientStorage {
    public static ArrayList<StoredIngredient> ingredientStorage = new ArrayList<>();
    public static ArrayAdapter<StoredIngredient> ingredientAdapter;

}
