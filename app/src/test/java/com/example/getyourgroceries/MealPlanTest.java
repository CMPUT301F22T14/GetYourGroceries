package com.example.getyourgroceries;

import static org.junit.Assert.assertEquals;

import com.example.getyourgroceries.entity.MealPlan;
import com.example.getyourgroceries.entity.MealPlanDay;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Test the MealPlan entity class.
 */
public class MealPlanTest {
    private MealPlan mealPlan;

    /**
     * Create a mock meal plan for testing.
     */
    public MealPlan mockMealPlan() {
        mealPlan = new MealPlan("Bulking Szn");
        mealPlan.setId("69420");
        return mealPlan;
    }

    /**
     * Test getting the meal plan ID.
     */
    @Test
    public void getIdTest() {
        mealPlan = mockMealPlan();
        assertEquals(mockMealPlan().getId(), "69420");
    }

    /**
     * Test setting the mean plan ID.
     */
    @Test
    public void setIdTest() {
        mealPlan = mockMealPlan();
        mealPlan.setId("42069");
        assertEquals("42069", mealPlan.getId());
    }

    /**
     * Test getting the name of the meal plan.
     */
    @Test
    public void getNameTest() {
        mealPlan = mockMealPlan();
        assertEquals("Bulking Szn", mealPlan.getName());
    }

    /**
     * Test setting the name of the meal plan.
     */
    @Test
    public void setNameTest() {
        mealPlan = mockMealPlan();
        mealPlan.setName("Cutting Szn");
        assertEquals("Cutting Szn", mealPlan.getName());
    }

    /**
     * Test adding days to the meal plan.
     */
    @Test
    public void addDayTest() {
        mealPlan = mockMealPlan();
        mealPlan.addDay(new MealPlanDay("Day 1"));
        assertEquals(1, mealPlan.getMealPlanDays().size());
    }

    /**
     * Test deleting days from the meal plan.
     */
    @Test
    public void deleteDayTest() {
        mealPlan = mockMealPlan();
        MealPlanDay mealPlanDay = new MealPlanDay("Day 1");
        mealPlan.addDay(mealPlanDay);
        mealPlan.deleteDay(mealPlanDay);
        assertEquals(0, mealPlan.getMealPlanDays().size());
    }

    /**
     * Test getting the list of meal plan days of a meal plan.
     */
    @Test
    public void getMealPlanDaysTest() {
        mealPlan = mockMealPlan();
        MealPlanDay mealPlanDay = new MealPlanDay("Day 1");
        mealPlan.addDay(mealPlanDay);
        ArrayList<MealPlanDay> mealPlanDaysList = new ArrayList<>();
        mealPlanDaysList.add(mealPlanDay);
        assertEquals(mealPlanDaysList, mealPlan.getMealPlanDays());
    }
}
