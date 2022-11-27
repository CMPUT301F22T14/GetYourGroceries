package com.example.getyourgroceries;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.getyourgroceries.entity.IngredientStorage;
import com.example.getyourgroceries.entity.StoredIngredient;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.robotium.solo.Solo;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

/**
 * All UI tests for the ingredients
 */
public class IngredientFragmentTest {
    private Solo solo;
    private StoredIngredient editTestIngredient;
    private StoredIngredient deleteTestIngredient;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());

        IngredientStorage.getInstance().clearLocalStorage();

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        String expiry = "01/02/2022";
        editTestIngredient = new StoredIngredient("EditTest", 1, 0.99, "Fruit", formatter.parse(expiry), "Fridge");
        deleteTestIngredient = new StoredIngredient("DeleteTest", 1, 0.99, "Fruit", formatter.parse(expiry), "Fridge");
    }

    @Test
    public void start() {
        Activity activity = rule.getActivity();
    }

    /**
     * Tests the bottom navigation click to get to the ingredients fragment
     * User Stories:
     * - US 01.02.01
     * - US 01.05.01
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
     * User Stories:
     * - US 01.01.01
     */
    @Test
    public void testAddIngredient() throws InterruptedException {
        solo.assertCurrentActivity("Wrong Activity!", MainActivity.class);
        BottomNavigationItemView navItem = (BottomNavigationItemView) solo.getView(R.id.ingredients_icon);
        solo.clickOnView(navItem.getChildAt(1));

        ListView ingredients = (ListView) solo.getView(R.id.ingredientListView);
        int size = ingredients.getAdapter().getCount();

        // click on add ingredient
        solo.clickOnButton(0);

        solo.enterText((EditText) solo.getView(R.id.change_ingredient_description), "AddTest");
        assertTrue(solo.waitForText("AddTest", 1, 2000));

        solo.enterText((EditText) solo.getView(R.id.change_ingredient_quantity), "5");
        assertTrue(solo.waitForText("5", 1, 2000));

        TextView calendar = (TextView) solo.getView(R.id.change_ingredient_expiry);
        solo.clickOnView(calendar);
        solo.clickOnButton("OK");

        // Click location
        AutoCompleteTextView locationView = (AutoCompleteTextView) solo.getView(R.id.change_ingredient_location);
        solo.enterText(locationView, "Pantry");

        // Click Category
        AutoCompleteTextView categoryView = (AutoCompleteTextView) solo.getView(R.id.change_ingredient_category);
        solo.enterText(categoryView, "Vegetables");

        solo.enterText((EditText) solo.getView(R.id.change_ingredient_unit), "0.99");
        assertTrue(solo.waitForText("0.99", 1, 2000));

        // click on confirm button
        solo.clickOnButton(0);
        assertTrue(solo.waitForText("Ingredients"));

        Thread.sleep(200);
        assertEquals(ingredients.getAdapter().getCount(), size+1);

        for(int i = 0; i < size+1; i++) {
            StoredIngredient tv = (StoredIngredient) ingredients.getAdapter().getItem(i);
            if(Objects.equals(tv.getDescription(), "AddTest")) {
                solo.clickLongInList(i, 0);
                solo.clickOnButton("Yes");
                break;
            }
        }
    }

    /**
     * Tests editing an ingredient
     * User Stories:
     * - US 01.03.01
     */
    @Test
    public void testEditIngredient() throws InterruptedException {
        IngredientStorage.getInstance().addIngredient(editTestIngredient, true);
        solo.assertCurrentActivity("Wrong Activity!", MainActivity.class);
        BottomNavigationItemView navItem = (BottomNavigationItemView) solo.getView(R.id.ingredients_icon);
        solo.clickOnView(navItem.getChildAt(1));

        // setup test
        ListView ingredients = (ListView) solo.getView(R.id.ingredientListView);
        int size = ingredients.getAdapter().getCount();

        for(int i = 0; i < size; i++) {
            StoredIngredient tv = (StoredIngredient) ingredients.getAdapter().getItem(i);
            if(Objects.equals(tv.getDescription(), "EditTest")) {
                solo.clickInList(i, 0);

                EditText description = (EditText) solo.getView(R.id.change_ingredient_description);
                solo.clearEditText(description);
                solo.enterText(description, "EditTestUpdated");
                assertTrue(solo.waitForText("EditTestUpdated", 1, 2000));

                solo.enterText((EditText) solo.getView(R.id.change_ingredient_quantity), "6");
                assertTrue(solo.waitForText("6", 1, 2000));

                TextView calendar = (TextView) solo.getView(R.id.change_ingredient_expiry);
                solo.clickOnView(calendar);
                solo.clickOnButton("OK");

                EditText unitView = (EditText) solo.getView(R.id.change_ingredient_unit);
                solo.clearEditText(unitView);
                solo.enterText(unitView, "1.99");
                assertTrue(solo.waitForText("1.99", 1, 2000));

                // click on confirm button
                solo.clickOnView(solo.getView(R.id.change_ingredient_confirm));
                assertTrue(solo.waitForText("Ingredients"));
            }
        }

        Thread.sleep(200);

        for(int i = 0; i < size; i++) {
            StoredIngredient tv = (StoredIngredient) ingredients.getAdapter().getItem(i);
            if(Objects.equals(tv.getDescription(), "EditTestUpdated")) {
                solo.clickLongInList(i, 0);
                solo.clickOnButton("Yes");
                break;
            }
        }
    }

    /**
     * Tests deleting an ingredient
     * User Stories:
     * - US 01.04.01
     */
    @Test
    public void testDeleteIngredient() throws InterruptedException {
        IngredientStorage.getInstance().addIngredient(deleteTestIngredient, true);
        solo.assertCurrentActivity("Wrong Activity!", MainActivity.class);
        BottomNavigationItemView navItem = (BottomNavigationItemView) solo.getView(R.id.ingredients_icon);
        solo.clickOnView(navItem.getChildAt(1));

        ListView ingredients = (ListView) solo.getView(R.id.ingredientListView);
        int size = ingredients.getAdapter().getCount();

        for(int i = 0; i < size; i++) {
            StoredIngredient tv = (StoredIngredient) ingredients.getAdapter().getItem(i);
            if(Objects.equals(tv.getDescription(), "DeleteTest")) {
                solo.clickLongInList(i, 0);
                solo.clickOnButton("Yes");
                break;
            }
        }

        Thread.sleep(200); // need sleep for else the adapter doesn't properly update
        assertEquals(ingredients.getAdapter().getCount(), size-1);
    }

    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }
}
