/* IngredientStorage class. */
package com.example.getyourgroceries.entity;

// Import statements.
import android.content.Context;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import com.example.getyourgroceries.adapters.IngredientStorageAdapter;
import com.example.getyourgroceries.adapters.MealIngredientStorageAdapter;
import com.example.getyourgroceries.control.IngredientDB;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Create an object to represent a list of ingredients.
 * Using singleton pattern to ensure class has only one instance
 */
public class IngredientStorage {

    // Single instance of class
    private static final IngredientStorage instance = new IngredientStorage();

    // Constructor is private to prevent new storages being made
    private IngredientStorage(){
        super();
    }

    // ArrayList and db connection
    private ArrayList<StoredIngredient> ingredientStorage = new ArrayList<>();
    private ArrayAdapter<StoredIngredient> ingredientAdapter;
    private ArrayAdapter<StoredIngredient> mealIngredientAdapter;
    private IngredientDB ingredientDB;

    /**
     * Static method to get singleton instance of Ingredient Storage
     * @return
     */
    public static IngredientStorage getInstance() {
        return instance;
    }

    /**
     * Setup storage adapter using provided context
     * @param context
     * @return newly created ArrayAdapter object
     */
    public void setupStorage(Context context){
        ingredientAdapter = new IngredientStorageAdapter(context, this.ingredientStorage);
        mealIngredientAdapter = new MealIngredientStorageAdapter(context,this.ingredientStorage);
        this.ingredientDB = new IngredientDB();
    }


    /**
     * Gets the associated adapter
     * @return the ingredient adapter
     */
    public ArrayAdapter<StoredIngredient> getIngredientAdapter() {
        return ingredientAdapter;
    }
    public ArrayAdapter<StoredIngredient> getMealIngredientAdapter(){
        return mealIngredientAdapter;
    }

    /**
     * Add ingredient to storage
     * @param storedIngredient new ingredient to add
     * @param toDB boolean to push changes to DB or not
     */
    public void addIngredient(StoredIngredient storedIngredient, boolean toDB){
        ingredientAdapter.add(storedIngredient);
        if (toDB)
            ingredientDB.addIngredient(storedIngredient);
        ingredientAdapter.notifyDataSetChanged();
    }

    /**
     * Retrieve a specific ingredient from the storage by index
     * @param i index of ingredient in array
     * @return
     */
    public StoredIngredient getIngredient(int i){
        return ingredientAdapter.getItem(i);
    }

    /**
     * Update an ingredient in the database
     * @param storedIngredient ingredient to update
     */
    public void updateIngredient(StoredIngredient storedIngredient){
        ingredientDB.updateIngredient(storedIngredient);
    }

    /**
     * Delete an ingredient from local and database storage (if requested)
     * @param storedIngredient ingredient to delete
     * @param toDB boolean to determine if deletion is database-side
     */
    public void deleteIngredient(StoredIngredient storedIngredient, boolean toDB){
        ingredientAdapter.remove(storedIngredient);
        if (toDB)
            ingredientDB.deleteIngredient(storedIngredient);
        ingredientAdapter.notifyDataSetChanged();
    }

    /**
     * Fetch arraylist containing ingredient objects
     * @return ArrayList<StoredIngredient>
     */
    public ArrayList<StoredIngredient> getIngredientList() {
        return ingredientStorage;
    }

    /**
     * Clear storage of local ingredient storage
     */
    public void clearLocalStorage(){
        ingredientAdapter.clear();
        ingredientAdapter.notifyDataSetChanged();
    }

    /**
     * Sort Ingredient Storage by specific category
     * @param type attribute to sort by
     * @param desc boolean to determine if descending
     */
    public void sortByCategory(int type,boolean desc){
        switch(type){
            case 0:
                if (desc) {
                    this.ingredientAdapter.sort((o1, o2) -> o1.getDescription().compareTo(o2.getDescription())*-1);
                }
                else{
                    this.ingredientAdapter.sort(Comparator.comparing(Ingredient::getDescription));
                }
                this.ingredientAdapter.notifyDataSetChanged();
                break;

            case 1:
                if (desc){
                    this.ingredientAdapter.sort((o1, o2) -> o1.getBestBefore().compareTo(o2.getBestBefore())*-1);

                }
                else{
                    this.ingredientAdapter.sort(Comparator.comparing(StoredIngredient::getBestBefore));
                }
                this.ingredientAdapter.notifyDataSetChanged();
                break;
            case 2:
                if (desc){
                    this.ingredientAdapter.sort((o1, o2) -> o1.getLocation().compareTo(o2.getLocation())*-1);
                }
                else{
                    this.ingredientAdapter.sort(Comparator.comparing(StoredIngredient::getLocation));

                }
                this.ingredientAdapter.notifyDataSetChanged();
                break;
            case 3:
                if (desc){
                    this.ingredientAdapter.sort((o1, o2) -> o1.getCategory().compareTo(o2.getCategory())*-1);
                }
                else{
                    this.ingredientAdapter.sort(Comparator.comparing(Ingredient::getCategory));
                }
                this.ingredientAdapter.notifyDataSetChanged();
                break;
        }

    }
}
