/* RecipeDB class. */
package com.example.getyourgroceries.control;

// Import statements.
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.getyourgroceries.entity.Recipe;
import com.example.getyourgroceries.entity.RecipeStorage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * Create an object to modify data in Firebase related to recipes.
 */
public class RecipeDB {
    public static final String TAG = "RECIPEDB";
    CollectionReference recipeCollection;
    FirebaseFirestore db;

    /**
     * Initializes the firebase instance and recipe collection.
     */
    public RecipeDB() {
        db = FirebaseFirestore.getInstance();
        recipeCollection = db.collection("Recipes");
        recipeCollection
                .get()
                .addOnCompleteListener(new OnCompleteListener<>() {
                    /**
                     * Execute the code when getting the ingredient collection is completed (or fails).
                     *
                     * @param task The task being done.
                     */
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Successful.
                            RecipeStorage.getInstance().clearLocalStorage();
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Recipe recipe = doc.toObject(Recipe.class);
                                recipe.setId(doc.getId());
                                RecipeStorage.getInstance().addRecipe(recipe, false);
                            }
                        } else {
                            // Failed.
                            Log.d(TAG, "Error getting recipes: ", task.getException());
                        }
                    }
                });
    }

    /**
     * Adds a given recipe to the firebase database
     * @param recipe: Recipe to add.
     * @return Newly created document ID.
     * @NOTE Make sure to assign the recipe the given returned ID after calling function.
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
     * Deletes a given recipe from the firebase database
     * @param recipe: recipe to delete.
     */
    public void deleteRecipe(Recipe recipe) {
        recipeCollection.document(recipe.getId()).delete();
    }

    /**
     * Updates a given recipe in the database.
     * @param recipe The recipe to update.
     */
    public void updateRecipe(Recipe recipe) {
        recipeCollection.document(recipe.getId()).set(recipe);
    }
}
