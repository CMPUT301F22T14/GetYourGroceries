package com.example.getyourgroceries.entity;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.getyourgroceries.adapters.MealPlanAdapter;
import com.example.getyourgroceries.control.MealPlanDB;

import java.util.ArrayList;

/**
 * Create an object to store meal plans.
 */
public class MealPlanStorage {
    // Actual singleton instance
    private static final MealPlanStorage instance = new MealPlanStorage();

    /**
     * Private constructor to force singleton
     */
    private MealPlanStorage() {}

    private ArrayList<MealPlan> mealPlanStorage = new ArrayList<>();
    private ArrayAdapter<MealPlan> mealPlanAdapter;
    private MealPlanDB mealPlanDB;

    /**
     * Get the instance of the MealPlanStorage class.
     *
     * @return A reference to the instance.
     */
    public static MealPlanStorage getInstance() {
        return instance;
    }

    /**
     * Setup storage adapter using provided context
     *
     * @param context to bind to
     */
    public void setupStorage(Context context) {
        mealPlanAdapter = new MealPlanAdapter(context, mealPlanStorage);
        mealPlanDB = new MealPlanDB();
    }

    /**
     * Gets the associated adapter
     *
     * @return meal plan adapter
     */
    public ArrayAdapter<MealPlan> getMealPlanAdapter() {
        return mealPlanAdapter;
    }

    /**
     * Adds a meal plan to list and potentially database
     *
     * @param plan meal plan to add
     * @param toDB determines whether or not to add to database
     */
    public void addMealPlan(MealPlan plan, boolean toDB) {
        if (toDB) {
            plan.setId(mealPlanDB.addMealPlan(plan));
        }
        mealPlanAdapter.add(plan);

        mealPlanAdapter.notifyDataSetChanged();
    }

    /**
     * Fetches specific meal plan in list
     *
     * @param i index of meal plan
     * @return meal plan at index
     */
    public MealPlan getMealPlan(int i) {
        return mealPlanAdapter.getItem(i);
    }

    /**
     * Updates a meal plan in list and database
     *
     * @param plan meal plan to update(updated version)
     */
    public void updateMealPlan(MealPlan plan) {
        mealPlanDB.updateMealPlan(plan);
    }

    /**
     * Deletes meal plan from list and potentially database
     *
     * @param plan meal plan to delete
     * @param toDB determines whether or not to remove from database
     */
    public void deleteMealPlan(MealPlan plan, boolean toDB) {
        mealPlanAdapter.remove(plan);

        if (toDB) {
            mealPlanDB.deleteMealPlan(plan);
        }
        mealPlanAdapter.notifyDataSetChanged();
    }

    /**
     * Fetch list of meal plans
     *
     * @return meal plans
     */
    public ArrayList<MealPlan> getMealPlanList() {
        return mealPlanStorage;
    }

    /**
     * Clear storage of local meal plan storage
     */
    public void clearLocalStorage() {
        mealPlanAdapter.clear();
        mealPlanAdapter.notifyDataSetChanged();
    }
}
