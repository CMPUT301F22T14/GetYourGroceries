package com.example.getyourgroceries;

import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.getyourgroceries.entity.Ingredient;
import com.example.getyourgroceries.entity.IngredientStorage;
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

/**
 * All UI tests for the ingredients
 */
public class IngredientSortingUITest {
    private Solo solo;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class, true, true);

    /**
     * Setup up before each test by navigating to the right page
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());

        solo.assertCurrentActivity("Wrong Activity!", MainActivity.class);
        BottomNavigationItemView navItem = (BottomNavigationItemView) solo.getView(R.id.ingredients_icon);
        solo.clickOnView(navItem.getChildAt(1));

        // Setup items to be sorted
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        StoredIngredient testIngredient1 = new StoredIngredient("Apple", 4, 0.99, "Fruit", formatter.parse("01/01/2022"), "Fridge");
        StoredIngredient testIngredient2 = new StoredIngredient("Banana", 3, 1.99, "Fruit", formatter.parse("01/02/2022"), "Pantry");
        StoredIngredient testIngredient3 = new StoredIngredient("Broccoli", 2, 2.99, "Vegetable", formatter.parse("01/03/2022"), "Fridge");
        StoredIngredient testIngredient4 = new StoredIngredient("Steak", 1, 15.99, "Meat", formatter.parse("01/04/2022"), "Freezer");
        IngredientStorage.getInstance().addIngredient(testIngredient1, false);
        IngredientStorage.getInstance().addIngredient(testIngredient2, false);
        IngredientStorage.getInstance().addIngredient(testIngredient3, false);
        IngredientStorage.getInstance().addIngredient(testIngredient4, false);
    }

    @Test
    public void start() {
        Activity activity = rule.getActivity();
    }

    /**
     * Tests to see if descriptions ascend after sort
     */
    @Test
    public void testDescriptionSortAsc() {
        solo.clickOnView(solo.getView(R.id.sortIngredientSpinner));
        solo.clickOnText("Description");
        solo.scrollToTop();

        //loop through list view elements and make sure they are ascending
        for (int i = 0; i < IngredientStorage.getInstance().getIngredientAdapter().getCount() - 1; i++) {
            StoredIngredient ing1 = IngredientStorage.getInstance().getIngredientAdapter().getItem(i);
            StoredIngredient ing2 = IngredientStorage.getInstance().getIngredientAdapter().getItem(i + 1);
            String s = ing1.getDescription();
            String s2 = ing2.getDescription();
            assertTrue((s.compareTo(s2)) <= 0);
        }
    }

    /**
     * Tests to see if descriptions descend after sort
     */
    @Test
    public void testDescriptionSortDsc() {
        solo.clickOnView(solo.getView(R.id.sortingSwitchIngredient));
        solo.clickOnText("Description");
        solo.clickOnView(solo.getView(R.id.sortIngredientSpinner));


        // loop through listview to assert order
        for (int i = 0; i < IngredientStorage.getInstance().getIngredientAdapter().getCount() - 1; i++) {
            StoredIngredient ing1 = IngredientStorage.getInstance().getIngredientAdapter().getItem(i);
            StoredIngredient ing2 = IngredientStorage.getInstance().getIngredientAdapter().getItem(i + 1);
            String s = ing1.getDescription();
            String s2 = ing2.getDescription();
            assertTrue((s.compareTo(s2)) >= 0);
        }
    }

    /**
     * Test to see if dates descend after sort
     */
    @Test
    public void testDateSortDsc() {
        solo.clickOnView(solo.getView(R.id.sortIngredientSpinner));
        solo.clickOnText("Date");
        solo.clickOnView(solo.getView(R.id.sortingSwitchIngredient));
        solo.scrollToTop();

        //loop through listview to assert date order
        for (int i = 0; i < IngredientStorage.getInstance().getIngredientAdapter().getCount() - 1; i++) {
            StoredIngredient ing1 = IngredientStorage.getInstance().getIngredientAdapter().getItem(i);
            StoredIngredient ing2 = IngredientStorage.getInstance().getIngredientAdapter().getItem(i + 1);
            Date d1 = ing1.getBestBefore();
            Date d2 = ing2.getBestBefore();
            assertTrue(d1.after(d2));
        }
    }

    /**
     * Test to see if dates ascend after sort
     */
    @Test
    public void testDateSortAsc() {
        solo.clickOnView(solo.getView(R.id.sortIngredientSpinner));
        solo.clickOnText("Date");

        // loop through listview to assert date order
        for (int i = 0; i < IngredientStorage.getInstance().getIngredientAdapter().getCount() - 1; i++) {
            StoredIngredient ing1 = IngredientStorage.getInstance().getIngredientAdapter().getItem(i);
            StoredIngredient ing2 = IngredientStorage.getInstance().getIngredientAdapter().getItem(i + 1);
            Date d1 = ing1.getBestBefore();
            Date d2 = ing2.getBestBefore();
            assertTrue(d1.before(d2));
        }
    }

    /**
     * Test to see if location ascend after sort
     */
    @Test
    public void testLocationSortAsc() {
        solo.clickOnView(solo.getView(R.id.sortIngredientSpinner));
        solo.clickOnText("Location");
        solo.scrollToTop();

        //loop through listview to assert location order
        for (int i = 0; i < IngredientStorage.getInstance().getIngredientAdapter().getCount() - 1; i++) {
            StoredIngredient ing1 = IngredientStorage.getInstance().getIngredientAdapter().getItem(i);
            StoredIngredient ing2 = IngredientStorage.getInstance().getIngredientAdapter().getItem(i + 1);
            String l1 = ing1.getLocation();
            String l2 = ing2.getLocation();
            assertTrue((l1.compareTo(l2)) <= 0);
        }
    }

    /**
     * Test to see if dates location after sort
     */
    @Test
    public void testLocationSortDsc() {
        solo.clickOnView(solo.getView(R.id.sortIngredientSpinner));
        solo.clickOnText("Location");
        solo.clickOnView(solo.getView(R.id.sortingSwitchIngredient));
        solo.scrollToTop();

        // loop through listview to assert location order
        for (int i = 0; i < IngredientStorage.getInstance().getIngredientAdapter().getCount() - 1; i++) {
            StoredIngredient ing1 = IngredientStorage.getInstance().getIngredientAdapter().getItem(i);
            StoredIngredient ing2 = IngredientStorage.getInstance().getIngredientAdapter().getItem(i + 1);
            String l1 = ing1.getLocation();
            String l2 = ing2.getLocation();

            assertTrue((l1.compareTo(l2)) >= 0);
        }
    }

    /**
     * Test to see if category ascend after sort
     */
    @Test
    public void testCategorySortAsc() {
        solo.clickOnView(solo.getView(R.id.sortIngredientSpinner));
        solo.clickOnText("Category");
        solo.scrollToTop();

        for (int i = 0; i < IngredientStorage.getInstance().getIngredientAdapter().getCount() - 1; i++) {
            StoredIngredient ing1 = IngredientStorage.getInstance().getIngredientAdapter().getItem(i);
            StoredIngredient ing2 = IngredientStorage.getInstance().getIngredientAdapter().getItem(i + 1);
            String c1 = ing1.getCategory();
            String c2 = ing2.getCategory();
            assertTrue((c1.compareTo(c2)) <= 0);
        }
    }

    /**
     * Test to see if category descend after sort
     */
    @Test
    public void testCategorySortDsc() {
        solo.clickOnView(solo.getView(R.id.sortIngredientSpinner));
        solo.clickOnText("Category");
        solo.clickOnView(solo.getView(R.id.sortingSwitchIngredient));
        solo.scrollToTop();

        for (int i = 0; i < IngredientStorage.getInstance().getIngredientAdapter().getCount() - 1; i++) {
            StoredIngredient ing1 = IngredientStorage.getInstance().getIngredientAdapter().getItem(i);
            StoredIngredient ing2 = IngredientStorage.getInstance().getIngredientAdapter().getItem(i + 1);
            String c1 = ing1.getCategory();
            String c2 = ing2.getCategory();
            assertTrue((c1.compareTo(c2)) >= 0);
        }
    }

    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
