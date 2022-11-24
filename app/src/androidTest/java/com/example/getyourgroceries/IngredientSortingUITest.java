package com.example.getyourgroceries;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.getyourgroceries.entity.StoredIngredient;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * All UI tests for the ingredients
 */
public class IngredientSortingUITest {
    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class, true, true);

    /**
     * Setup up before each test by navigating to the right page
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());

        solo.assertCurrentActivity("Wrong Activity!", MainActivity.class);
        BottomNavigationItemView navItem = (BottomNavigationItemView) solo.getView(R.id.ingredients_icon);
        solo.clickOnView(navItem.getChildAt(1));
        //assertTrue(solo.waitForText("Ingredients"));
    }

    @Test
    public void start() throws Exception {
        Activity activity = rule.getActivity();
    }

    /**
     * Tests to see if descriptions ascend after sort
     */
    @Test
    public void testDescriptionSortAsc() {
        solo.clickOnView(solo.getView(R.id.sortIngredientSpinner));
        solo.clickOnView(solo.getView(R.id.sortingSwitchIngredient));
        solo.clickOnView(solo.getView(R.id.sortingSwitchIngredient)); //click on switch twice to sort it
        solo.scrollToTop();
        solo.clickOnText("Description");

        //loop through list view elements and make sure they are ascending
        ListView ingredients = (ListView) solo.getView(R.id.ingredientListView);
        for (int i = 0; i<ingredients.getCount()-1; i++){
            View v = ingredients.getChildAt(i);
            String s = ((TextView) v.findViewById(R.id.ingredient_name)).getText().toString();
            View v2 = ingredients.getChildAt(i+1);
            String s2 = ((TextView) v2.findViewById(R.id.ingredient_name)).getText().toString();
            assertTrue((s.compareTo(s2))<=0);
        }

    }

    /**
     * Tests to see if descriptions descend after sort
     */
    @Test
    public void testDescriptionSortDsc() {
        solo.clickOnView(solo.getView(R.id.sortingSwitchIngredient));
        solo.clickOnView(solo.getView(R.id.sortIngredientSpinner));

        solo.scrollToTop();
        solo.clickOnText("Description");

        // loop through listview to assert order
        ListView ingredients = (ListView) solo.getView(R.id.ingredientListView);
        for (int i = 0; i<ingredients.getCount()-1; i++){
            View v = ingredients.getChildAt(i);
            String s = ((TextView) v.findViewById(R.id.ingredient_name)).getText().toString();
            View v2 = ingredients.getChildAt(i+1);
            String s2 = ((TextView) v2.findViewById(R.id.ingredient_name)).getText().toString();
            assertTrue((s.compareTo(s2))>=0);
        }
    }

    @Test
    public void testDateSortDsc() {
        solo.clickOnView(solo.getView(R.id.sortingSwitchIngredient));
        Spinner spinner = (Spinner) solo.getView(R.id.sortIngredientSpinner);
        solo.clickOnView(spinner);
        solo.clickOnText("Date");
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);

        //loop through listview to assert date order
        ListView ingredients = (ListView) solo.getView(R.id.ingredientListView);
        for (int i = 0; i<ingredients.getCount()-1; i++){
            View v = ingredients.getChildAt(i);
            String s = ((TextView) v.findViewById(R.id.ingredient_bestbefore)).getText().toString();
            View v2 = ingredients.getChildAt(i+1);
            String s2 = ((TextView) v2.findViewById(R.id.ingredient_bestbefore)).getText().toString();
            try{
                Date d1 = formatter.parse(s);
                Date d2 = formatter.parse(s2);
                assertTrue(d1.after(d2));
            }
            catch (ParseException e){
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testDateSortAsc() {
        Spinner spinner = (Spinner) solo.getView(R.id.sortIngredientSpinner);
        solo.clickOnView(spinner);
        solo.clickOnText("Date");
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);

        // loop through listview to assert date order
        ListView ingredients = (ListView) solo.getView(R.id.ingredientListView);
        for (int i = 0; i<ingredients.getCount()-1; i++){
            View v = ingredients.getChildAt(i);
            String s = ((TextView) v.findViewById(R.id.ingredient_bestbefore)).getText().toString();
            View v2 = ingredients.getChildAt(i+1);
            String s2 = ((TextView) v2.findViewById(R.id.ingredient_bestbefore)).getText().toString();
            try{
                Date d1 = formatter.parse(s);
                Date d2 = formatter.parse(s2);
                assertTrue(d1.before(d2));
            }
            catch (ParseException e){
                e.printStackTrace();
            }
        }
    }


    @Test
    public void testLocationSortAsc() {
        solo.clickOnView(solo.getView(R.id.sortIngredientSpinner));
        solo.scrollToTop();
        solo.clickOnText("Location");

        //loop through listview to assert location order
        ListView ingredients = (ListView) solo.getView(R.id.ingredientListView);
        for (int i = 0; i<ingredients.getCount()-1; i++){
            View v = ingredients.getChildAt(i);
            String s = ((TextView) v.findViewById(R.id.ingredient_location)).getText().toString();
            View v2 = ingredients.getChildAt(i+1);
            String s2 = ((TextView) v2.findViewById(R.id.ingredient_location)).getText().toString();
            assertTrue((s.compareTo(s2))<=0);
        }
    }

    @Test
    public void testLocationSortDsc() {
        solo.clickOnView(solo.getView(R.id.sortingSwitchIngredient));
        solo.clickOnView(solo.getView(R.id.sortIngredientSpinner));

        solo.scrollToTop();
        solo.clickOnText("Location");

        // loop through listview to assert location order
        ListView ingredients = (ListView) solo.getView(R.id.ingredientListView);
        for (int i = 0; i<ingredients.getCount()-1; i++){
            View v = ingredients.getChildAt(i);
            String s = ((TextView) v.findViewById(R.id.ingredient_location)).getText().toString();
            View v2 = ingredients.getChildAt(i+1);
            String s2 = ((TextView) v2.findViewById(R.id.ingredient_location)).getText().toString();
            assertTrue((s.compareTo(s2))>=0);
        }
    }


    @Test
    public void testCategorySortAsc() {
        solo.clickOnView(solo.getView(R.id.sortIngredientSpinner));
        solo.scrollToTop();
        solo.clickOnText("Category");

        ListView ingredients = (ListView) solo.getView(R.id.ingredientListView);
        for (int i = 0; i<ingredients.getCount()-1; i++){
            View v = ingredients.getChildAt(i);
            String s = ((TextView) v.findViewById(R.id.ingredient_category)).getText().toString();
            View v2 = ingredients.getChildAt(i+1);
            String s2 = ((TextView) v2.findViewById(R.id.ingredient_category)).getText().toString();
            assertTrue((s.compareTo(s2))<=0);
        }
    }

    @Test
    public void testCategorySortDsc() {
        solo.clickOnView(solo.getView(R.id.sortingSwitchIngredient));
        solo.clickOnView(solo.getView(R.id.sortIngredientSpinner));

        solo.scrollToTop();
        solo.clickOnText("Category");

        ListView ingredients = (ListView) solo.getView(R.id.ingredientListView);
        for (int i = 0; i<ingredients.getCount()-1; i++){
            View v = ingredients.getChildAt(i);
            String s = ((TextView) v.findViewById(R.id.ingredient_category)).getText().toString();
            View v2 = ingredients.getChildAt(i+1);
            String s2 = ((TextView) v2.findViewById(R.id.ingredient_category)).getText().toString();
            assertTrue((s.compareTo(s2))>=0);
        }
    }

    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
