package com.example.getyourgroceries.entity;

import java.util.ArrayList;

/**
 * Create an object to represent a meal plan.
 */
public class MealPlan {
    private String id;
    private String mealPlanName;
    private ArrayList<MealPlanDay> mealPlanDays;

    // empty constructor for firebase storage
    public MealPlan() {}

    /**
     * Constructor for the MealPlan class.
     *
     * @param mealPlanName The name of the meal plan.
     */
    public MealPlan(String mealPlanName) {
        this.mealPlanName = mealPlanName;
        this.mealPlanDays = new ArrayList<>();
    }

    /**
     * Constructor for auto-setting the array list
     *
     * @param mealPlanName name of the meal plan
     * @param mealPlanDays list of days for the meal
     */
    public MealPlan(String mealPlanName, ArrayList<MealPlanDay> mealPlanDays) {
        this.mealPlanName = mealPlanName;
        this.mealPlanDays = mealPlanDays;
    }

    /**
     * Get the meal plan's ID.
     *
     * @return The meal plan's ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Set the meal plan's ID.
     *
     * @param id The meal plan's ID.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get the meal plan's name.
     *
     * @return The name of the meal plan.
     */
    public String getName() {
        return mealPlanName;
    }

    /**
     * Set the meal plan's name.
     *
     * @param name The name of the meal plan.
     */
    public void setName(String name) {
        this.mealPlanName = name;
    }

    /**
     * Add a day to the meal plan.
     *
     * @param day The day to add.
     */
    public void addDay(MealPlanDay day) {
        if (!mealPlanDays.contains(day)) {
            mealPlanDays.add(day);
        }
    }

    /**
     * Delete a day from the meal plan.
     *
     * @param day The day to delete.
     */
    public void deleteDay(MealPlanDay day) {
        mealPlanDays.remove(day);
    }

    /**
     * Get the days of the meal plan.
     *
     * @return The days of the meal plan.
     */
    public ArrayList<MealPlanDay> getMealPlanDays() {
        return mealPlanDays;
    }

    /**
     * Sets the meal plan days
     *
     * @param days list of days
     */
    public void setMealPlanDays(ArrayList<MealPlanDay> days) {
        this.mealPlanDays = days;
    }
}