package com.example.getyourgroceries;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.getyourgroceries.entity.StoredIngredient;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.robotium.solo.Solo;

import junit.framework.TestSuite;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Objects;

/**
 * All UI tests for the ingredients
 */
public class IngredientFragmentTest {
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

        solo.enterText((EditText) solo.getView(R.id.change_ingredient_description), "Test");
        assertTrue(solo.waitForText("Test", 1, 2000));

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

        ListView ingredientList = (ListView) solo.getView(R.id.ingredientListView);
        int size = ingredientList.getAdapter().getCount();
        for(int i = 0; i < size; i++) {
            StoredIngredient tv = (StoredIngredient) ingredientList.getAdapter().getItem(i);
            if(Objects.equals(tv.getDescription(), "Test")) {
                solo.clickInList(i, 0);

                solo.clearEditText((EditText) solo.getView(R.id.change_ingredient_description));
                solo.enterText((EditText) solo.getView(R.id.change_ingredient_description), "Test2");
                assertTrue(solo.waitForText("Test2", 1, 2000));

                solo.enterText((EditText) solo.getView(R.id.change_ingredient_quantity), "6");
                assertTrue(solo.waitForText("6", 1, 2000));

                TextView calendar = (TextView) solo.getView(R.id.change_ingredient_expiry);
                solo.clickOnView(calendar);
                solo.clickOnButton("OK");

                solo.pressSpinnerItem(0,2);
                assertTrue(solo.waitForText("Freezer", 1, 2000));

                solo.pressSpinnerItem(1,2);
                assertTrue(solo.waitForText("Category 3", 1, 2000));

                solo.clearEditText((EditText) solo.getView(R.id.change_ingredient_unit));
                solo.enterText((EditText) solo.getView(R.id.change_ingredient_unit), "1.99");
                assertTrue(solo.waitForText("1.99", 1, 2000));

                // click on confirm button
                solo.clickOnButton(0);
                assertTrue(solo.waitForText("Ingredients"));
            }
        }
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
            if(Objects.equals(tv.getDescription(), "Test") || Objects.equals(tv.getDescription(), "Test2")) {
                solo.clickLongInList(i, 0);
                solo.clickOnButton("Yes");
                break;
            }
        }
    }

    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
