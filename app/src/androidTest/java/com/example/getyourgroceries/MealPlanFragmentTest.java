package com.example.getyourgroceries;

import static androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static org.junit.Assert.assertTrue;

import android.app.Activity;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.getyourgroceries.entity.Ingredient;
import com.example.getyourgroceries.entity.MealPlan;
import com.example.getyourgroceries.entity.MealPlanDay;
import com.example.getyourgroceries.entity.MealPlanStorage;
import com.example.getyourgroceries.entity.Recipe;
import com.example.getyourgroceries.entity.ScaledRecipe;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.robotium.solo.Solo;

import org.checkerframework.checker.units.qual.A;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

/**
 * All UI tests for the meal plan
 */
public class MealPlanFragmentTest {
    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }

    /**
     * Tests the bottom navigation click to get to the meal plan fragment
     */
    @Test
    public void testGoToMealPlan() {
        solo.assertCurrentActivity("Wrong Activity!", MainActivity.class);
        BottomNavigationItemView navItem = (BottomNavigationItemView) solo.getView(R.id.meal_icon);
        solo.clickOnView(navItem.getChildAt(1));

        assertTrue(solo.waitForText("Meal Plans"));
    }

    /**
     * Test adding meal plans with multiple days and including both recipes and ingredients (US 3.01.01).
     * @throws Throwable Exception for running delete on the UI thread.
     */
    @Test
    public void testAddMealPlan() throws Throwable {

        /* Add two meal plans. */
        ArrayList<MealPlanDay> mealPlanDays1 = new ArrayList<>();
        mealPlanDays1.add(new MealPlanDay("Day 1"));
        mealPlanDays1.add(new MealPlanDay("Day 2"));
        mealPlanDays1.add(new MealPlanDay("Day 3"));
        mealPlanDays1.get(0).addRecipe(new ScaledRecipe(new Recipe("Fried Rice", 25, 3, "Dinner", "", ""), 1));
        mealPlanDays1.get(1).addRecipe(new ScaledRecipe(new Recipe("Scrambled Eggs", 10, 1, "Breakfast", "", ""), 1));
        mealPlanDays1.get(1).addIngredient(new Ingredient("Banana", 1, 0.50, "Fruit"));
        mealPlanDays1.get(2).addIngredient(new Ingredient("Apple", 1, 0.75, "Fruit"));
        MealPlan mealPlan1 = new MealPlan("Bulking Szn", mealPlanDays1);
        MealPlanStorage.getInstance().addMealPlan(mealPlan1, true);
        ArrayList<MealPlanDay> mealPlanDays2 = new ArrayList<>();
        mealPlanDays2.add(new MealPlanDay("Sunday"));
        mealPlanDays2.add(new MealPlanDay("Wednesday"));
        mealPlanDays2.get(0).addIngredient(new Ingredient("Yogurt", 1, 1.50, "Dairy"));
        mealPlanDays2.get(1).addRecipe(new ScaledRecipe(new Recipe("Protein Shake", 5, 1, "Supplements", "", ""), 1));
        MealPlan mealPlan2 = new MealPlan("Cutting Szn", mealPlanDays2);
        MealPlanStorage.getInstance().addMealPlan(mealPlan2, true);

        /* Check for the existence of the created meal plans. */
        solo.clickOnView(((BottomNavigationItemView) solo.getView(R.id.meal_icon)).getChildAt(1));
        assertTrue(solo.waitForText("Bulking Szn", 1, 2000));
        assertTrue(solo.waitForText("Cutting Szn", 1, 2000));

        /* Check for the existence of the meal plan days. */
        solo.clickOnText("Bulking Szn");
        assertTrue(solo.waitForText("Day 1", 1, 2000));
        assertTrue( solo.waitForText("Day 2", 1, 2000));
        solo.scrollToBottom();
        assertTrue(solo.waitForText("Day 3", 1, 2000));
        solo.goBack();
        solo.clickOnText("Cutting Szn");
        assertTrue(solo.waitForText("Sunday", 1, 2000));
        assertTrue(solo.waitForText("Wednesday", 1, 2000));
        solo.goBack();

        /* Check for the existence of the added recipes and ingredients. */
        solo.clickOnText("Bulking Szn");
        assertTrue(solo.waitForText("Fried Rice", 1, 2000));
        assertTrue(solo.waitForText("Scrambled Eggs", 1, 2000));
        solo.scrollToBottom();
        assertTrue(solo.waitForText("Banana", 1, 2000));
        assertTrue(solo.waitForText("Apple", 1, 2000));
        solo.goBack();
        solo.clickOnText("Cutting Szn");
        assertTrue(solo.waitForText("Yogurt", 1, 2000));
        assertTrue(solo.waitForText("Protein Shake", 1, 2000));
        solo.goBack();

        /* Delete the test meal plans. */
        runOnUiThread(() -> {
            MealPlanStorage.getInstance().deleteMealPlan(mealPlan1, true);
            MealPlanStorage.getInstance().deleteMealPlan(mealPlan2, true);
        });
    }

    /**
     * Tests updating the scale of a recipe in a meal plan (US 3.02.01).
     * @throws Throwable Exception for running delete on the UI thread.
     */
    @Test
    public void testScaleMealPlan() throws Throwable {

        /* Add a meal plan with a recipe. */
        ArrayList<MealPlanDay> mealPlanDays = new ArrayList<>();
        mealPlanDays.add(new MealPlanDay("Day 1"));
        ScaledRecipe scaledRecipe = new ScaledRecipe(new Recipe("Pasta", 20, 2, "Dinner", "", ""), 1);
        mealPlanDays.get(0).addRecipe(scaledRecipe);
        MealPlan mealPlan = new MealPlan("Cheat Day", mealPlanDays);
        MealPlanStorage.getInstance().addMealPlan(mealPlan, true);

        /* Test for the existence of the added recipe. */
        solo.clickOnView(((BottomNavigationItemView) solo.getView(R.id.meal_icon)).getChildAt(1));
        assertTrue(solo.waitForText("Cheat Day", 1, 2000));
        solo.clickOnText("Cheat Day");
        assertTrue(solo.waitForText("Pasta", 1, 2000));
        assertTrue(solo.waitForText("1", 1, 2000));
        solo.goBack();

        /* Change the scale of the recipe. */
        scaledRecipe.setScale(69);
        mealPlan.getMealPlanDays().get(0).updateRecipe(scaledRecipe, 0);
        MealPlanStorage.getInstance().updateMealPlan(mealPlan);

        /* Check if the scale changed correctly. */
        solo.clickOnText("Cheat Day");
        assertTrue(solo.waitForText("69", 1, 2000));

        /* Delete the added meal plan. */
        runOnUiThread(() -> {
            MealPlanStorage.getInstance().deleteMealPlan(mealPlan, true);
        });
    }

    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
