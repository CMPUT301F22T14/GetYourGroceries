/* IngredientDB Class. */
package com.example.getyourgroceries.control;

// Import statements.
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.getyourgroceries.entity.Ingredient;
import com.example.getyourgroceries.entity.IngredientStorage;
import com.example.getyourgroceries.entity.StoredIngredient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Create an object to modify the data in Firebase related to ingredients.
 */
public class IngredientDB {

    // Attributes.
    public static final String TAG = "INGREDIENTDB";
    CollectionReference ingredientCollection;
    FirebaseFirestore db;

    /**
     * Initializes firebase instance and ingredient collection.
     */
    public IngredientDB() {

        // Initialize.
        db = FirebaseFirestore.getInstance();
        ingredientCollection = db.collection("Ingredients");
        new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);

        // Get the initial ingredient collection from firebase and populate the ingredient storage.
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
                            IngredientStorage.clearIngredients();
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                StoredIngredient s = doc.toObject(StoredIngredient.class);
                                IngredientStorage.addIngredient(s);
                            }
                        } else {

                            // Failed.
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        // Create a listener for future changes.
        ingredientCollection.addSnapshotListener(new EventListener<>() {

            /**
             * Listen for changes to the data set.
             * @param queryDocumentSnapshots Snapshot of the data.
             * @param error                  Possible errors.
             */
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {

                // Add updated ingredients to the storage.
                IngredientStorage.clearIngredients();
                assert queryDocumentSnapshots != null;
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    StoredIngredient s = doc.toObject(StoredIngredient.class);
                    IngredientStorage.addIngredient(s);
                }
            }
        });
    }

    /**
     *  Adds a given ingredient to the firebase database.
     * @param ingredient Ingredient object to be added to the database.
     * @return Newly created document ID.
     * @NOTE Make sure to assign the ingredient the given returned ID after calling function.
     * TODO: Add user account verification.
     */
    public String addIngredient(Ingredient ingredient) {

        // Add the ingredient.
        final String[] id = new String[1];
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
     * @param ingredient Ingredient to update.
     * TODO: Add user account verification.
     */
    public void updateIngredient(Ingredient ingredient) {

        // Update the information.
        ingredientCollection.document(ingredient.getId())
                .set(ingredient);
    }

    /**
     * Delete an ingredient from the database.
     * @param ingredient The ingredient to delete.
     */
    public void deleteIngredient(Ingredient ingredient) {
        ingredientCollection.document(ingredient.getId()).delete();
    }
}