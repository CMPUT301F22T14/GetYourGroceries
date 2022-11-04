package com.example.getyourgroceries;

import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.getyourgroceries.entity.IngredientStorage;
import com.example.getyourgroceries.entity.RecipeStorage;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * All UI tests for recipes
 */
public class RecipeFragmentTest {
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
     * Tests the bottom navigation click to get to the recipes fragment
     */
    @Test
    public void testGoToRecipes() {
        solo.assertCurrentActivity("Wrong Activity!", MainActivity.class);
        BottomNavigationItemView navItem = (BottomNavigationItemView) solo.getView(R.id.recipe_icon);
        solo.clickOnView(navItem.getChildAt(1));

        assertTrue(solo.waitForText("Recipe List"));
    }

    @Test
    public void testSortingNameAsc(){
        solo.assertCurrentActivity("Wrong Activity!", MainActivity.class);
        BottomNavigationItemView navItem = (BottomNavigationItemView) solo.getView(R.id.recipe_icon);
        solo.clickOnView(navItem.getChildAt(1));
        Spinner dropDown = (Spinner)solo.getView(R.id.sort_recipe_spinner);
        solo.clickOnView(dropDown);
        solo.clickOnText("Name");
        SwitchCompat switch_sorting = (SwitchCompat)solo.getView(R.id.sortingSwitchRecipe);
        solo.clickOnView(switch_sorting);
        solo.clickOnView(switch_sorting);
        solo.sleep(100);
        //Need to assert that the first item is the correct sorted item
//        Assert.assertEquals("Apple",RecipeStorage.recipeAdapter.getItem(0).getName());
    }
    @Test
    public void testSortingNameDsc(){
        solo.assertCurrentActivity("Wrong Activity!", MainActivity.class);
        BottomNavigationItemView navItem = (BottomNavigationItemView) solo.getView(R.id.recipe_icon);
        solo.clickOnView(navItem.getChildAt(1));
        Spinner dropDown = (Spinner)solo.getView(R.id.sort_recipe_spinner);
        solo.clickOnView(dropDown);
        solo.clickOnText("Name");
        SwitchCompat switch_sorting = (SwitchCompat)solo.getView(R.id.sortingSwitchRecipe);
        solo.clickOnView(switch_sorting);
        solo.sleep(100);
        //Need to assert that the first item is the correct sorted item
//        Assert.assertEquals("Test",RecipeStorage.recipeAdapter.getItem(0).getName());
    }


    @Test
    public void testSortingPrepAsc(){
        solo.assertCurrentActivity("Wrong Activity!", MainActivity.class);
        BottomNavigationItemView navItem = (BottomNavigationItemView) solo.getView(R.id.recipe_icon);
        solo.clickOnView(navItem.getChildAt(1));
        Spinner dropDown = (Spinner)solo.getView(R.id.sort_recipe_spinner);
        solo.clickOnView(dropDown);
        solo.pressMenuItem(3);
        SwitchCompat switch_sorting = (SwitchCompat)solo.getView(R.id.sortingSwitchRecipe);
        solo.clickOnView(switch_sorting);
        solo.clickOnView(switch_sorting);
        solo.sleep(100);
        //Need to assert that the first item is the correct sorted item
//        Assert.assertEquals("Apple",RecipeStorage.recipeAdapter.getItem(0).getName());
    }
    @Test
    public void testSortingPrepDsc(){
        solo.assertCurrentActivity("Wrong Activity!", MainActivity.class);
        BottomNavigationItemView navItem = (BottomNavigationItemView) solo.getView(R.id.recipe_icon);
        solo.clickOnView(navItem.getChildAt(1));
        Spinner dropDown = (Spinner)solo.getView(R.id.sort_recipe_spinner);
        solo.clickOnView(dropDown);
        solo.pressMenuItem(3);
        SwitchCompat switch_sorting = (SwitchCompat)solo.getView(R.id.sortingSwitchRecipe);
        solo.clickOnView(switch_sorting);
        solo.sleep(100);
        //Need to assert that the first item is the correct sorted item
//        Assert.assertEquals("Some Recipe",RecipeStorage.recipeAdapter.getItem(0).getName());
    }

    @Test
    public void testSortingServingAsc(){
        solo.assertCurrentActivity("Wrong Activity!", MainActivity.class);
        BottomNavigationItemView navItem = (BottomNavigationItemView) solo.getView(R.id.recipe_icon);
        solo.clickOnView(navItem.getChildAt(1));
        Spinner dropDown = (Spinner)solo.getView(R.id.sort_recipe_spinner);
        solo.clickOnView(dropDown);
        solo.pressMenuItem(6);
        SwitchCompat switch_sorting = (SwitchCompat)solo.getView(R.id.sortingSwitchRecipe);
        solo.clickOnView(switch_sorting);
        solo.clickOnView(switch_sorting);
        solo.sleep(100);
        //Need to assert that the first item is the correct sorted item
//        Assert.assertEquals("Some Recipe",RecipeStorage.recipeAdapter.getItem(0).getName());
    }
    @Test
    public void testSortingServingDsc(){
        solo.assertCurrentActivity("Wrong Activity!", MainActivity.class);
        BottomNavigationItemView navItem = (BottomNavigationItemView) solo.getView(R.id.recipe_icon);
        solo.clickOnView(navItem.getChildAt(1));
        Spinner dropDown = (Spinner)solo.getView(R.id.sort_recipe_spinner);
        solo.clickOnView(dropDown);
        solo.pressMenuItem(6);
        SwitchCompat switch_sorting = (SwitchCompat)solo.getView(R.id.sortingSwitchRecipe);
        solo.clickOnView(switch_sorting);
        solo.sleep(100);
        //Need to assert that the first item is the correct sorted item
//        Assert.assertEquals("Apple",RecipeStorage.recipeAdapter.getItem(0).getName());
    }


    @Test
    public void testSortingCategoryAsc(){
        solo.assertCurrentActivity("Wrong Activity!", MainActivity.class);
        BottomNavigationItemView navItem = (BottomNavigationItemView) solo.getView(R.id.recipe_icon);
        solo.clickOnView(navItem.getChildAt(1));
        Spinner dropDown = (Spinner)solo.getView(R.id.sort_recipe_spinner);
        solo.clickOnView(dropDown);
        solo.pressMenuItem(8);
        solo.pressSpinnerItem(0,4);
        assertTrue(solo.waitForText("Category", 1, 2000));
        solo.sleep(5000);
        SwitchCompat switch_sorting = (SwitchCompat)solo.getView(R.id.sortingSwitchRecipe);
        solo.clickOnView(switch_sorting);
        solo.clickOnView(switch_sorting);
        //Need to assert that the first item is the correct sorted item
    }
    @Test
    public void testSortingCategoryDsc(){
        solo.assertCurrentActivity("Wrong Activity!", MainActivity.class);
        BottomNavigationItemView navItem = (BottomNavigationItemView) solo.getView(R.id.recipe_icon);
        solo.clickOnView(navItem.getChildAt(1));
        Spinner dropDown = (Spinner)solo.getView(R.id.sort_recipe_spinner);
        solo.clickOnView(dropDown);
        solo.pressMenuItem(8);
        solo.pressSpinnerItem(0,4);
        assertTrue(solo.waitForText("Category", 1, 2000));
        solo.sleep(5000);
        SwitchCompat switch_sorting = (SwitchCompat)solo.getView(R.id.sortingSwitchRecipe);
        solo.clickOnView(switch_sorting);
        //Need to assert that the first item is the correct sorted item
    }
    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
