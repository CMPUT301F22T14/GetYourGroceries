package com.example.getyourgroceries;

import static androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.widget.ListView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.getyourgroceries.entity.Ingredient;
import com.example.getyourgroceries.entity.IngredientStorage;
import com.example.getyourgroceries.entity.MealPlan;
import com.example.getyourgroceries.entity.MealPlanDay;
import com.example.getyourgroceries.entity.MealPlanStorage;
import com.example.getyourgroceries.entity.Recipe;
import com.example.getyourgroceries.entity.ScaledRecipe;
import com.example.getyourgroceries.entity.StoredIngredient;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * All UI tests for the shopping list
 */
public class ShoppingListFragmentTest {
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
        BottomNavigationItemView navItem = (BottomNavigationItemView) solo.getView(R.id.shopping_icon);
        solo.clickOnView(navItem.getChildAt(1));

        assertTrue(solo.waitForText("Shopping List"));
    }

    /**
     * Test viewing the list of ingredients that are required and not currently stored (US. 4.01.01).
     * @throws Throwable Exception for deleting from the database.
     */
    @Test
    public void testViewShoppingList() throws Throwable {

        /* Add an ingredient to ingredient storage. */
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, 12, 25);
        Date date = calendar.getTime();
        StoredIngredient storedIngredient = new StoredIngredient("Apple", 3, 0.75, "Fruit", date, "Fruit Basket");
        IngredientStorage.getInstance().addIngredient(storedIngredient, true);

        /* Add recipes to meal plan. */
        ArrayList<MealPlanDay> mealPlanDayList1 = new ArrayList<>();
        mealPlanDayList1.add(new MealPlanDay("Day 1"));
        Recipe recipe = new Recipe("Apple Pie", 60, 8, "Dessert", "", "");
        recipe.addIngredient(new Ingredient("Apple", 10, 0.75, "Fruit"));
        mealPlanDayList1.get(0).addRecipe(new ScaledRecipe(recipe, 2));
        MealPlan mealPlan1 = new MealPlan("Cheat Life", mealPlanDayList1);
        MealPlanStorage.getInstance().addMealPlan(mealPlan1, true);
        ArrayList<MealPlanDay> mealPlanDayList2 = new ArrayList<>();
        mealPlanDayList2.add(new MealPlanDay("Sunday"));
        mealPlanDayList2.get(0).addIngredient(new Ingredient("Watermelon", 1, 3.00, "Fruit"));
        MealPlan mealPlan2 = new MealPlan("Bulking Szn", mealPlanDayList2);
        MealPlanStorage.getInstance().addMealPlan(mealPlan2, true);

        /* Check the shopping list for the required ingredients. */
        solo.clickOnView(((BottomNavigationItemView) solo.getView(R.id.shopping_icon)).getChildAt(1));
        assertTrue(solo.waitForText("Apple", 1, 2000));
        assertTrue(solo.waitForText("17", 1, 2000));
        assertTrue(solo.waitForText("Watermelon", 1, 2000));

        /* Delete the added meal plans. */
        runOnUiThread(() -> {
            IngredientStorage.getInstance().deleteIngredient(storedIngredient, true);
            MealPlanStorage.getInstance().deleteMealPlan(mealPlan1, true);
            MealPlanStorage.getInstance().deleteMealPlan(mealPlan2, true);
        });
    }

    /**
     * Test viewing the details of an ingredient in the shopping list (US 4.02.01).
     * @throws Throwable Exception for deleting from the database.
     */
    @Test
    public void testShowIngredientDetails() throws Throwable {

        /* Add an ingredient to ingredient storage. */
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, 12, 25);
        Date date = calendar.getTime();
        StoredIngredient storedIngredient = new StoredIngredient("Apple", 3, 0.75, "Fruit", date, "Fruit Basket");
        IngredientStorage.getInstance().addIngredient(storedIngredient, true);

        /* Add recipes to meal plan. */
        ArrayList<MealPlanDay> mealPlanDayList1 = new ArrayList<>();
        mealPlanDayList1.add(new MealPlanDay("Day 1"));
        Recipe recipe = new Recipe("Apple Pie", 60, 8, "Dessert", "", "");
        recipe.addIngredient(new Ingredient("Apple", 10, 0.75, "Fruit"));
        mealPlanDayList1.get(0).addRecipe(new ScaledRecipe(recipe, 2));
        MealPlan mealPlan1 = new MealPlan("Cheat Life", mealPlanDayList1);
        MealPlanStorage.getInstance().addMealPlan(mealPlan1, true);

        /* Check the shopping list for the details of the ingredient. */
        solo.clickOnView(((BottomNavigationItemView) solo.getView(R.id.shopping_icon)).getChildAt(1));
        assertTrue(solo.waitForText("Apple", 1, 2000));
        assertTrue(solo.waitForText("17", 1, 2000));
        assertTrue(solo.waitForText("Fruit", 1, 2000));
        assertTrue(solo.waitForText("0.75", 1, 2000));

        /* Delete the added ingredient. */
        runOnUiThread(() -> {
            IngredientStorage.getInstance().deleteIngredient(storedIngredient, true);
            MealPlanStorage.getInstance().deleteMealPlan(mealPlan1, true);
        });
    }

    /**
     * Test sorting the shopping list by description or category, ascending or descending (US 4.03.01).
     * @throws Throwable Exception for deleting from the database.
     */
    @Test
    public void testSortShoppingList() throws Throwable {

        /* Add recipes to meal plan. */
        ArrayList<MealPlanDay> mealPlanDayList1 = new ArrayList<>();
        mealPlanDayList1.add(new MealPlanDay("Day 1"));
        Recipe recipe = new Recipe("Apple Pie", 60, 8, "Dessert", "", "");
        recipe.addIngredient(new Ingredient("Apple", 10, 0.75, "Fruit"));
        mealPlanDayList1.get(0).addRecipe(new ScaledRecipe(recipe, 2));
        MealPlan mealPlan1 = new MealPlan("Cheat Life", mealPlanDayList1);
        MealPlanStorage.getInstance().addMealPlan(mealPlan1, true);
        ArrayList<MealPlanDay> mealPlanDayList2 = new ArrayList<>();
        mealPlanDayList2.add(new MealPlanDay("Sunday"));
        mealPlanDayList2.get(0).addIngredient(new Ingredient("Watermelon", 1, 3.00, "Fruit"));
        MealPlan mealPlan2 = new MealPlan("Bulking Szn", mealPlanDayList2);
        MealPlanStorage.getInstance().addMealPlan(mealPlan2, true);

        /* Sort by description ascending. */
        solo.clickOnView(((BottomNavigationItemView) solo.getView(R.id.shopping_icon)).getChildAt(1));
        solo.clickOnView(solo.getView(R.id.sort_shoppinglist_spinner));
        solo.clickOnText("Description");
        solo.sleep(2000);

        /* Make sure the ingredients sorted correctly. */
        ListView ingredients = (ListView) solo.getView(R.id.shoppingListView);
        for (int i = 0; i < ingredients.getAdapter().getCount() - 1; i++) {
            Ingredient ingredient1 = (Ingredient) ingredients.getAdapter().getItem(i);
            Ingredient ingredient2 = (Ingredient) ingredients.getAdapter().getItem(i + 1);
            assertTrue(ingredient1.getDescription().compareTo(ingredient2.getDescription()) <= 0);
        }

        /* Sort by description descending. */
        solo.clickOnView(solo.getView(R.id.sort_shoppinglist_spinner));
        solo.clickOnText("Description");
        solo.clickOnView(solo.getView(R.id.sorting_switch_shoppinglist));
        solo.sleep(2000);

        /* Make sure the ingredients sorted correctly. */
        for (int i = 0; i < ingredients.getAdapter().getCount() - 1; i++) {
            Ingredient ingredient1 = (Ingredient) ingredients.getAdapter().getItem(i);
            Ingredient ingredient2 = (Ingredient) ingredients.getAdapter().getItem(i + 1);
            assertTrue(ingredient1.getDescription().compareTo(ingredient2.getDescription()) >= 0);
        }

        /* Sort by category ascending. */
        solo.clickOnView(solo.getView(R.id.sort_shoppinglist_spinner));
        solo.clickOnText("Category");
        solo.clickOnView(solo.getView(R.id.sorting_switch_shoppinglist));
        solo.sleep(2000);

        /* Make sure the ingredients sorted correctly. */
        for (int i = 0; i < ingredients.getAdapter().getCount() - 1; i++) {
            Ingredient ingredient1 = (Ingredient) ingredients.getAdapter().getItem(i);
            Ingredient ingredient2 = (Ingredient) ingredients.getAdapter().getItem(i + 1);
            assertTrue(ingredient1.getCategory().compareTo(ingredient2.getCategory()) <= 0);
        }

        /* Sort by category descending. */
        solo.clickOnView(solo.getView(R.id.sort_shoppinglist_spinner));
        solo.clickOnText("Category");
        solo.clickOnView(solo.getView(R.id.sorting_switch_shoppinglist));
        solo.sleep(2000);

        /* Make sure the ingredients sorted correctly. */
        for (int i = 0; i < ingredients.getAdapter().getCount() - 1; i++) {
            Ingredient ingredient1 = (Ingredient) ingredients.getAdapter().getItem(i);
            Ingredient ingredient2 = (Ingredient) ingredients.getAdapter().getItem(i + 1);
            assertTrue(ingredient1.getCategory().compareTo(ingredient2.getCategory()) >= 0);
        }

        /* Delete the added meal plans. */
        runOnUiThread(() -> {
            MealPlanStorage.getInstance().deleteMealPlan(mealPlan1, true);
            MealPlanStorage.getInstance().deleteMealPlan(mealPlan2, true);
        });
    }

    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
