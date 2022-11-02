/* RecipeDB class. */
package com.example.getyourgroceries.control;

// Import statements.
import android.util.Log;
import com.example.getyourgroceries.entity.Recipe;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;

/**
 * Create an object to modify data in Firebase related to recipes.
 */
public class RecipeDB {

    // Attributes.
    public static final String TAG = "RECIPEDB";
    CollectionReference recipeCollection;
    FirebaseFirestore db;

    /**
     * Initializes the firebase instance and recipe collection.
     */
    public RecipeDB() {

        // Initialize.
        db = FirebaseFirestore.getInstance();
        recipeCollection = db.collection("Recipes");
    }

    /**
     * Adds a given recipe to the firebase database
     * @param recipe: Recipe to add.
     * @return Newly created document ID.
     * @NOTE Make sure to assign the recipe the given returned ID after calling function.
     * TODO: Add user account verification.
     */
    public String addRecipe(Recipe recipe) {

        // Add the recipe.
        final String[] id = new String[1];
        recipeCollection
                .add(recipe)
                .addOnSuccessListener(documentReference -> {
                    id[0] = documentReference.getId();
                    Log.d(TAG, "Added Recipe to db");
                })
                .addOnFailureListener(e -> Log.w(TAG, "Error adding recipe", e));

        // Return the ID.
        return id[0];
    }

    /**
     * Updates a given recipe in the database.
     * @param recipe The recipe to update.
     * TODO: User account verification.
     */
    public void updateRecipe(Recipe recipe) {

        // Update the recipe.
        recipeCollection.document(recipe.getId())
                .set(recipe);
    }

    /**
     * Fetches all recipes from recipe collection.
     * @return All recipes in an array list.
     * TODO: User account verification.
     */
    public ArrayList<Recipe> getRecipes(){

        // Get the recipes.
        ArrayList<Recipe> recipes = new ArrayList<>();
        recipeCollection.addSnapshotListener((queryDocumentSnapshots, error) -> {
            assert queryDocumentSnapshots != null;
            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                Recipe recipe = doc.toObject(Recipe.class);
                recipes.add(recipe);
            }
        });
        return recipes;
    }
}
