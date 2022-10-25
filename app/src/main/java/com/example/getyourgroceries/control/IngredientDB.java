package com.example.getyourgroceries.control;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.getyourgroceries.entity.Ingredient;
import com.example.getyourgroceries.entity.Recipe;
import com.example.getyourgroceries.entity.StoredIngredient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class IngredientDB {
    public static final String TAG = "INGREDIENTDB";
    CollectionReference ingredientCollection;
    FirebaseFirestore db;

    /**
     * Initializes firebase instance and ingredient collection
     */
    public IngredientDB() {
        db = FirebaseFirestore.getInstance();
        ingredientCollection = db.collection("Ingredients");
    }

    /**
     *  Adds a given ingredient to the firebase database
     * @param ingredient: ingredient object to be added to the database
     * @return newly created document id
     * @NOTE make sure to assign the ingredient the given returned ID after calling function
     */
    public String addIngredient(Ingredient ingredient) {
        // TODO: only add ingredient for logged in user
        final String[] id = new String[1];
        ingredientCollection
                .add(ingredient)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        id[0] = documentReference.getId();
                        Log.d(TAG, "Added Recipe to db");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding ingredient", e);
                    }
                });
        return id[0];
    }

    /**
     * Updates a given ingredient in the database
     * @param ingredient: updated ingredient object
     */
    public void updateIngredient(Ingredient ingredient) {
        // TODO: only update ingredient for logged in user
        ingredientCollection.document(ingredient.getId())
                .set(ingredient);
    }


    /**
     * Fetches all ingredients from ingredients collection
     * @return all ingredients in an array list
     */
    public void getIngredients(IngredientDBCallback myCallback) {
        ArrayList<StoredIngredient> ingredients = new ArrayList<>();
        ingredientCollection.addSnapshotListener(
                new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                        assert queryDocumentSnapshots != null;
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            StoredIngredient ingredient = doc.toObject(StoredIngredient.class);
                            ingredient.setId(doc.getId());
                            ingredients.add(ingredient);
                        }
                        myCallback.onCallback(ingredients);
                    }
                });
    }
}