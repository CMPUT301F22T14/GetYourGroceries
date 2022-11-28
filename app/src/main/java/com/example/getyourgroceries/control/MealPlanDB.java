package com.example.getyourgroceries.control;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.getyourgroceries.entity.MealPlan;
import com.example.getyourgroceries.entity.MealPlanStorage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * Create an object to modify the Firebase database for recipes.
 */
public class MealPlanDB {
    public static final String TAG = "MEALPLANDB";
    CollectionReference mealPlanCollection;
    FirebaseFirestore db;

    /**
     * Create an object to modify data in Firebase related to meal plans.
     */
    public MealPlanDB() {
        db = FirebaseFirestore.getInstance();
        mealPlanCollection = db.collection("Meal Plans");
        refreshStorage();
    }

    /**
     * Adds a given Meal Plan to the firebase database
     *
     * @param plan: Meal Plan to add.
     * @return Newly created document ID.
     * @NOTE Make sure to assign the meal plan the given returned ID after calling function.
     */
    public String addMealPlan(MealPlan plan) {
        final String[] id = new String[1];
        mealPlanCollection
                .add(plan)
                .addOnSuccessListener(documentReference -> {
                    id[0] = documentReference.getId();
                    plan.setId(id[0]);
                    mealPlanCollection.document(id[0]).update("id", id[0]);
                    Log.d(TAG, "Added Meal Plan to db");
                })
                .addOnFailureListener(e -> Log.w(TAG, "Error adding meal plan", e));

        // Return the ID.
        return id[0];
    }

    /**
     * Deletes a given meal plan from the firebase database
     *
     * @param plan: meal plan to delete.
     */
    public void deleteMealPlan(MealPlan plan) {
        mealPlanCollection.document(plan.getId()).delete();
    }

    /**
     * Updates a given meal plan in the database.
     *
     * @param plan meal plan to update.
     */
    public void updateMealPlan(MealPlan plan) {
        mealPlanCollection.document(plan.getId()).set(plan);
    }

    /**
     * Query the database for current values
     */
    public void refreshStorage(){
        mealPlanCollection
                .get()
                .addOnCompleteListener(new OnCompleteListener<>() {
                    /**
                     * Execute the code when getting the meal plan collection is completed (or fails).
                     *
                     * @param task The task being done.
                     */
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Successful.
                            MealPlanStorage.getInstance().clearLocalStorage();
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                MealPlan plan = doc.toObject(MealPlan.class);
                                plan.setId(doc.getId());
                                MealPlanStorage.getInstance().addMealPlan(plan, false);
                            }
                        } else {
                            // Failed.
                            Log.d(TAG, "Error getting meal plans: ", task.getException());
                        }
                    }
                });
    }
}
