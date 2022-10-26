package com.example.getyourgroceries.control;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.getyourgroceries.entity.Ingredient;
import com.example.getyourgroceries.entity.IngredientStorage;
import com.example.getyourgroceries.entity.StoredIngredient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);

        //get initial ingredient collection from firebase and populate ingredient storage
        ingredientCollection
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            IngredientStorage.ingredientStorage.clear();
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                StoredIngredient s = doc.toObject(StoredIngredient.class);
                                IngredientStorage.ingredientStorage.add(s);
                            }
                            IngredientStorage.ingredientAdapter.notifyDataSetChanged();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        //create listener for future changes
        ingredientCollection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                IngredientStorage.ingredientStorage.clear();
                assert queryDocumentSnapshots != null;
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    StoredIngredient s = doc.toObject(StoredIngredient.class);

                    IngredientStorage.ingredientStorage.add(s);
                }
                IngredientStorage.ingredientAdapter.notifyDataSetChanged();
            }
        });
        //ingredientCollection.sn
    }

    /**
     *  Adds a given ingredient to the firebase database
     * @param ingredient: ingredient object to be added to the database
     * @return newly created document id
     * @NOTE make sure to assign the ingredient the given returned ID after calling function
     */
    public String addRecipe(Ingredient ingredient) {
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
    public void updateRecipe(Ingredient ingredient) {
        // TODO: only update ingredient for logged in user
        ingredientCollection.document(ingredient.getId())
                .set(ingredient);
    }

}