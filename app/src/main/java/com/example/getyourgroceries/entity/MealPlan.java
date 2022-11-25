package com.example.getyourgroceries.entity;

import java.util.ArrayList;

public class MealPlan {
    private String id;
    private String mealPlanName;
    private ArrayList<MealPlanDay> mealPlanDays;

    public MealPlan() {}

    // Constructor for initialization
    public MealPlan(String id, String mealPlanName){
        this.id = id;
        this.mealPlanName = mealPlanName;
        this.mealPlanDays = new ArrayList<>();
    }
<<<<<<< Updated upstream
=======

    /**
     * The firebase id of the object
     * @return string representation of firebase id
     */
>>>>>>> Stashed changes
    public String getId() {
        return id;
    }

    /**
     * Sets the id of the object
     * @param id string representation of a firebase id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Fetches the name of the meal plan
     * @return the string name
     */
    public String getName() {
        return mealPlanName;
    }

    /**
     * Sets the name of the meal plan
     * @param name new name to set
     */
    public void setName(String name) {
        this.mealPlanName = name;
    }

    /**
     * Adds a day to the meal plan
     * @param day object ot add
     */
    public void addDay(MealPlanDay day){
        if (!mealPlanDays.contains(day)){
            mealPlanDays.add(day);
        }
    }

    /**
     * Removes a day from the meal plan
     * @param day object to remove
     */
    public void deleteDay(MealPlanDay day){
        mealPlanDays.remove(day);
    }

    /**
     * Fetches the list of days in the meal plan
     * @return list of day objects
     */
    public ArrayList<MealPlanDay> getMealPlanDays() {
        return mealPlanDays;
    }
}
