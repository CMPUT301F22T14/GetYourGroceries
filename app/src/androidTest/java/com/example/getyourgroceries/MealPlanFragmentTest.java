package com.example.getyourgroceries;

import static androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.widget.ScrollView;

import androidx.core.widget.NestedScrollView;
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
        solo.clickOnView(((BottomNavigationItemView) solo.getView(R.id.meal_icon)).getChildAt(1));
        solo.clickOnText("Add Meal Plan");
        solo.enterText(0, "Bulking Szn");
        solo.clickOnText("Add Day");
        solo.clickOnText("Add Day");
        solo.clickOnText("Add Recipe");
        solo.sleep(2000);
        solo.clickOnView(solo.getView(R.id.addMealPlanRecipe));
        solo.enterText(0, "Fried Rice");
        solo.enterText(1, "25");
        solo.enterText(2, "3");
        solo.enterText(3, "Dinner");
        NestedScrollView scrollView = (NestedScrollView) solo.getView(R.id.change_recipe_layout);
        scrollView.post(() -> {
            for (int i = 0; i < 11; i++) {
                scrollView.arrowScroll(ScrollView.FOCUS_DOWN);
            }
        });
        solo.clickOnText("Confirm");
        solo.clickOnText("OK");
        solo.clickOnText("Add Ingredient", 2);
        solo.sleep(500);
        solo.clickOnView(solo.getView(R.id.addMealPlanIngredient));
        solo.enterText(1, "Banana");
        solo.enterText(2, "1");
        solo.enterText(3, "Fruit");
        solo.enterText(4, "0.75");
        solo.clickOnText("OK");
        solo.clickOnText("Confirm");

        /* Check for the existence of the meal plan days. */
        solo.waitForText("Bulking Szn", 1, 2000);
        solo.clickOnText("Bulking Szn");
        assertTrue(solo.waitForText("Day 1", 1, 2000));
        assertTrue( solo.waitForText("Day 2", 1, 2000));

        /* Check for the existence of the added recipes and ingredients. */
        assertTrue(solo.waitForText("Fried Rice", 1, 2000));
        assertTrue(solo.waitForText("Banana", 1, 2000));

        /* Delete the test meal plans. */
        solo.goBack();
        solo.clickLongOnText("Bulking Szn");
        solo.clickOnText("Yes");
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

        /* Change the scale of the recipe. */
        solo.clickOnText("Pasta");
        solo.enterText(0, "69");
        solo.clickOnText("OK");

        /* Check if the scale changed correctly. */
        assertTrue(solo.waitForText("69", 1, 2000));
        solo.clickOnText("Confirm");

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
