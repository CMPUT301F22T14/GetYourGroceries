/* IngredientDB Class. */
package com.example.getyourgroceries.control;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.getyourgroceries.entity.Ingredient;
import com.example.getyourgroceries.entity.IngredientStorage;
import com.example.getyourgroceries.entity.StoredIngredient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Create an object to modify the data in Firebase related to ingredients.
 */
public class IngredientDB {
    public static final String TAG = "INGREDIENTDB";
    CollectionReference ingredientCollection;
    FirebaseFirestore db;

    /**
     * Initializes firebase instance and ingredient collection.
     */
    public IngredientDB() {
        db = FirebaseFirestore.getInstance();
        ingredientCollection = db.collection("Ingredients");

        // Get the initial ingredient collection from firebase and populate the ingredient storage.
        refreshStorage();
    }

    /**
     * Adds a given ingredient to the firebase database.
     *
     * @param ingredient Ingredient object to be added to the database.
     * @return Newly created document ID.
     * @NOTE Make sure to assign the ingredient the given returned ID after calling function.
     */
    public String addIngredient(Ingredient ingredient) {
        final String[] id = new String[1];

        //add ingredient to remote collection
        ingredientCollection
                .add(ingredient)
                .addOnSuccessListener(documentReference -> {
                    id[0] = documentReference.getId();
                    ingredient.setId(id[0]);
                    ingredientCollection.document(id[0]).update("id", id[0]);
                    Log.d(TAG, "Added Recipe to db");
                })
                .addOnFailureListener(e -> Log.w(TAG, "Error adding ingredient", e));

        // Return the ID.
        return id[0];
    }

    /**
     * Update a given ingredient in the database
     *
     * @param ingredient Ingredient to update.
     */
    public void updateIngredient(Ingredient ingredient) {
        if (ingredient.getId() != null) {
            ingredientCollection.document(ingredient.getId()).set(ingredient);
        }
    }

    /**
     * Delete an ingredient from the database.
     *
     * @param ingredient The ingredient to delete.
     */
    public void deleteIngredient(Ingredient ingredient) {
        ingredientCollection.document(ingredient.getId()).delete();
    }

    /**
     * Query the database for current values
     */
    public void refreshStorage(){
        ingredientCollection
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
                            IngredientStorage.getInstance().clearLocalStorage();
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                StoredIngredient s = doc.toObject(StoredIngredient.class);
                                IngredientStorage.getInstance().addIngredient(s, false);
                            }
                        } else {
                            // Failed.
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}