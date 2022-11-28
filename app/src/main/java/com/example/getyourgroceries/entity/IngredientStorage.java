/* IngredientStorage class. */
package com.example.getyourgroceries.entity;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.getyourgroceries.adapters.IngredientStorageAdapter;
import com.example.getyourgroceries.adapters.MealIngredientStorageAdapter;
import com.example.getyourgroceries.control.IngredientDB;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

/**
 * Create an object to represent a list of ingredients.
 * Using singleton pattern to ensure class has only one instance
 */
public class IngredientStorage {
    // Single instance of class
    private static final IngredientStorage instance = new IngredientStorage();

    // Constructor is private to prevent new storages being made
    private IngredientStorage() {
        super();
    }

    // ArrayList and db connection
    private final ArrayList<StoredIngredient> ingredientStorage = new ArrayList<>();
    private ArrayAdapter<StoredIngredient> ingredientAdapter;
    private ArrayAdapter<StoredIngredient> mealIngredientAdapter;
    private IngredientDB ingredientDB;

    /**
     * Static method to get singleton instance of Ingredient Storage
     *
     * @return the singleton instance
     */
    public static IngredientStorage getInstance() {
        return instance;
    }

    /**
     * Setup storage adapter using provided context
     *
     * @param context the context
     */
    public void setupStorage(Context context) {
        ingredientAdapter = new IngredientStorageAdapter(context, this.ingredientStorage);
        mealIngredientAdapter = new MealIngredientStorageAdapter(context, this.ingredientStorage);
        this.ingredientDB = new IngredientDB();
    }

    /**
     * Gets the associated adapter
     *
     * @return the ingredient adapter
     */
    public ArrayAdapter<StoredIngredient> getIngredientAdapter() {
        return ingredientAdapter;
    }

    public ArrayAdapter<StoredIngredient> getMealIngredientAdapter() {
        return mealIngredientAdapter;
    }

    /**
     * Add ingredient to storage
     *
     * @param storedIngredient new ingredient to add
     * @param toDB             boolean to push changes to DB or not
     */
    public void addIngredient(StoredIngredient storedIngredient, boolean toDB) {
        if (toDB) {
            storedIngredient.setId(ingredientDB.addIngredient(storedIngredient));
        }
        ingredientAdapter.add(storedIngredient);

        ingredientAdapter.notifyDataSetChanged();
    }

    /**
     * Finds a stored ingredient with a matching description
     *
     * @param description of the ingredient
     * @return the stored ingredient with the description
     */
    public StoredIngredient getIngredient(String description) {
        final StoredIngredient[] ret = new StoredIngredient[1];
        ingredientStorage.forEach(ingredient -> {
            if (Objects.equals(ingredient.getDescription(), description)) {
                ret[0] = ingredient;
            }
        });
        return ret[0];
    }

    /**
     * Retrieve a specific ingredient from the storage by index
     *
     * @param i index of ingredient in array
     * @return the requested ingredient
     */
    public StoredIngredient getIngredient(int i) {
        return ingredientAdapter.getItem(i);
    }

    /**
     * Update an ingredient in the database
     *
     * @param storedIngredient ingredient to update
     */
    public void updateIngredient(StoredIngredient storedIngredient) {
        ingredientDB.updateIngredient(storedIngredient);
    }

    /**
     * Delete an ingredient from local and database storage (if requested)
     *
     * @param storedIngredient ingredient to delete
     * @param toDB             boolean to determine if deletion is database-side
     */
    public void deleteIngredient(StoredIngredient storedIngredient, boolean toDB) {
        ingredientAdapter.remove(storedIngredient);
        if (toDB)
            ingredientDB.deleteIngredient(storedIngredient);
        ingredientAdapter.notifyDataSetChanged();
    }

    /**
     * Fetch arraylist containing ingredient objects
     *
     * @return ArrayList<StoredIngredient>
     */
    public ArrayList<StoredIngredient> getIngredientList() {
        return ingredientStorage;
    }

    /**
     * Clear storage of local ingredient storage
     */
    public void clearLocalStorage() {
        ingredientAdapter.clear();
        ingredientAdapter.notifyDataSetChanged();
    }

    /**
     * Sort Ingredient Storage by specific category
     *
     * @param type attribute to sort by
     * @param desc boolean to determine if descending
     */
    public void sortByCategory(int type, boolean desc) {
        switch (type) {
            case 0:
                if (desc) {
                    this.ingredientAdapter.sort((o1, o2) -> o1.getDescription().compareTo(o2.getDescription()) * -1);
                } else {
                    this.ingredientAdapter.sort(Comparator.comparing(Ingredient::getDescription));
                }
                this.ingredientAdapter.notifyDataSetChanged();
                break;

            case 1:
                if (desc) {
                    this.ingredientAdapter.sort((o1, o2) -> o1.getBestBefore().compareTo(o2.getBestBefore()) * -1);

                } else {
                    this.ingredientAdapter.sort(Comparator.comparing(StoredIngredient::getBestBefore));
                }
                this.ingredientAdapter.notifyDataSetChanged();
                break;
            case 2:
                if (desc) {
                    this.ingredientAdapter.sort((o1, o2) -> o1.getLocation().compareTo(o2.getLocation()) * -1);
                } else {
                    this.ingredientAdapter.sort(Comparator.comparing(StoredIngredient::getLocation));

                }
                this.ingredientAdapter.notifyDataSetChanged();
                break;
            case 3:
                if (desc) {
                    this.ingredientAdapter.sort((o1, o2) -> o1.getCategory().compareTo(o2.getCategory()) * -1);
                } else {
                    this.ingredientAdapter.sort(Comparator.comparing(Ingredient::getCategory));
                }
                this.ingredientAdapter.notifyDataSetChanged();
                break;
        }

    }

    /**
     * Check if an ingredient exists in the storage.
     *
     * @param ingredientName The ingredient to check for.
     * @return true/false depending on the existence of the ingredient in storage.
     */
    public boolean ingredientExists(String ingredientName) {
        for (int i = 0; i < ingredientStorage.size(); i++) {
            if (Objects.equals(ingredientStorage.get(i).getDescription(), ingredientName)) {
                return true;
            }
        }
        return false;
    }
}