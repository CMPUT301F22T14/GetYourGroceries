package com.example.getyourgroceries;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.getyourgroceries.entity.StoredIngredient;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.robotium.solo.Solo;

import androidx.fragment.app.Fragment;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The UI tests for the main activity
 */
public class MainActivityTest {
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
     * Tests the bottom navigation click to get to the ingredients fragment
     */
    @Test
    public void testGoToIngredients() {
        solo.assertCurrentActivity("Wrong Activity!", MainActivity.class);
        BottomNavigationItemView navItem = (BottomNavigationItemView) solo.getView(R.id.ingredients_icon);
        solo.clickOnView(navItem.getChildAt(1));

        assertTrue(solo.waitForText("Ingredients"));
    }

    /**
     * Tests adding an ingredient
     */
    @Test
    public void testAddIngredient() {
        solo.assertCurrentActivity("Wrong Activity!", MainActivity.class);
        BottomNavigationItemView navItem = (BottomNavigationItemView) solo.getView(R.id.ingredients_icon);
        solo.clickOnView(navItem.getChildAt(1));

        ListView ingredientList = (ListView) solo.getView(R.id.ingredientListView);
        int size = ingredientList.getAdapter().getCount();

        // click on add ingredient
        solo.clickOnButton(0);

        solo.enterText((EditText) solo.getView(R.id.change_ingredient_description), "Apples");
        assertTrue(solo.waitForText("Apples", 1, 2000));

        solo.enterText((EditText) solo.getView(R.id.change_ingredient_quantity), "5");
        assertTrue(solo.waitForText("5", 1, 2000));

        TextView calendar = (TextView) solo.getView(R.id.change_ingredient_expiry);
        solo.clickOnView(calendar);
        solo.clickOnButton("OK");

        solo.pressSpinnerItem(0,1);
        assertTrue(solo.waitForText("Pantry", 1, 2000));

        solo.pressSpinnerItem(1,1);
        assertTrue(solo.waitForText("Category 1", 1, 2000));

        solo.enterText((EditText) solo.getView(R.id.change_ingredient_unit), "0.99");
        assertTrue(solo.waitForText("0.99", 1, 2000));

        // click on confirm button
        solo.clickOnButton(0);
        assertTrue(solo.waitForText("Ingredients"));

        ListView ingredientListEnd = (ListView) solo.getView(R.id.ingredientListView);
        assertEquals(ingredientListEnd.getAdapter().getCount(), size+1);
    }

    /**
     * Tests editing an ingredient
     */
    @Test
    public void testEditIngredient() {
        solo.assertCurrentActivity("Wrong Activity!", MainActivity.class);
        BottomNavigationItemView navItem = (BottomNavigationItemView) solo.getView(R.id.ingredients_icon);
        solo.clickOnView(navItem.getChildAt(1));
    }

    /**
     * Tests deleting an ingredient
     */
    @Test
    public void testDeleteIngredient() {
        solo.assertCurrentActivity("Wrong Activity!", MainActivity.class);
        BottomNavigationItemView navItem = (BottomNavigationItemView) solo.getView(R.id.ingredients_icon);
        solo.clickOnView(navItem.getChildAt(1));

        ListView ingredientList = (ListView) solo.getView(R.id.ingredientListView);
        int size = ingredientList.getAdapter().getCount();
        for(int i = 0; i < size; i++) {
            StoredIngredient tv = (StoredIngredient) ingredientList.getAdapter().getItem(i);
            Log.d("DELETE TEST", "testDeleteIngredient: " + tv.getDescription());
            if(Objects.equals(tv.getDescription(), "Test")) {
                solo.clickLongInList(i, 0);
                solo.clickOnButton("Yes");
                break;
            }
        }
    }

    /**
     * Tests the bottom navigation click to get to the recipes fragment
     */
    @Test
    public void testGoToRecipes() {
        solo.assertCurrentActivity("Wrong Activity!", MainActivity.class);
        BottomNavigationItemView navItem = (BottomNavigationItemView) solo.getView(R.id.recipe_icon);
        solo.clickOnView(navItem.getChildAt(1));

        assertTrue(solo.waitForText("Recipe List"));
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
     * Tests the bottom navigation click to get to the shopping list fragment
     */
    @Test
    public void testGoToShoppingList() {
        solo.assertCurrentActivity("Wrong Activity!", MainActivity.class);
        BottomNavigationItemView navItem = (BottomNavigationItemView) solo.getView(R.id.shopping_icon);
        solo.clickOnView(navItem.getChildAt(1));

        assertTrue(solo.waitForText("Shopping List"));
    }

    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
