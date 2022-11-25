package com.example.getyourgroceries.entity;

import java.util.ArrayList;

public class MealPlan {
    private String id;
    private String mealPlanName;
    private ArrayList<MealPlanDay> mealPlanDays;

    public MealPlan() {}

    public MealPlan(String id, String mealPlanName){
        this.id = id;
        this.mealPlanName = mealPlanName;
        this.mealPlanDays = new ArrayList<>();
    }

    public MealPlan() {

    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return mealPlanName;
    }
    public void setName(String name) {
        this.mealPlanName = name;
    }
    public void addDay(MealPlanDay day){
        if (!mealPlanDays.contains(day)){
            mealPlanDays.add(day);
        }
    }
    public void deleteDay(MealPlanDay day){
        mealPlanDays.remove(day);
    }
    public ArrayList<MealPlanDay> getMealPlanDays() {
        return mealPlanDays;
    }
}
