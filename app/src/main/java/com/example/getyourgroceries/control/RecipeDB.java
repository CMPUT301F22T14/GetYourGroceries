package com.example.getyourgroceries.control;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.getyourgroceries.entity.Recipe;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RecipeDB {
    public static final String TAG = "RECIPEDB";
    CollectionReference recipeCollection;
    FirebaseFirestore db;

    /**
     * Initializes firebase instance and recipe collection
     */
    public RecipeDB() {
        db = FirebaseFirestore.getInstance();
        recipeCollection = db.collection("Recipes");
    }

    /**
     *  Adds a given recipe to the firebase database
     * @param recipe: recipe object to be added to the database
     * @return newly created document id
     * @NOTE make sure to assign the recipe the given returned ID after calling function
     */
    public String addRecipe(Recipe recipe) {
        // TODO: only add recipe for logged in user
        final String[] id = new String[1];
        recipeCollection
                .add(recipe)
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
                        Log.w(TAG, "Error adding recipe", e);
                    }
                });
        return id[0];
    }

    /**
     * Updates a given recipe in the database
     * @param recipe: updated recipe object
     */
    public void updateRecipe(Recipe recipe) {
        // TODO: only update recipe for logged in user
        recipeCollection.document(recipe.getId())
                .set(recipe);
    }

    /**
     * Fetches all recipes from recipe collection
     * @return all recipes in an array list
     */
    public ArrayList<Recipe> getRecipes(){
        // TODO: only fetch recipes for logged in user
        ArrayList<Recipe> recipes = new ArrayList<>();

        recipeCollection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
                for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                    Recipe recipe = doc.toObject(Recipe.class);
                    recipe.setId(doc.getId());
                    recipes.add(recipe);
                }
            }
        });
        return recipes;
    }
}
