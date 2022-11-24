package com.example.getyourgroceries;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.getyourgroceries.entity.IngredientStorage;
import com.example.getyourgroceries.entity.Recipe;
import com.example.getyourgroceries.entity.RecipeStorage;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Objects;

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
        SwitchCompat switch_sorting = (SwitchCompat)solo.getView(R.id.sorting_switch_recipe);
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
        SwitchCompat switch_sorting = (SwitchCompat)solo.getView(R.id.sorting_switch_recipe);
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
        SwitchCompat switch_sorting = (SwitchCompat)solo.getView(R.id.sorting_switch_recipe);
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
        SwitchCompat switch_sorting = (SwitchCompat)solo.getView(R.id.sorting_switch_recipe);
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
        SwitchCompat switch_sorting = (SwitchCompat)solo.getView(R.id.sorting_switch_recipe);
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
        SwitchCompat switch_sorting = (SwitchCompat)solo.getView(R.id.sorting_switch_recipe);
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
        SwitchCompat switch_sorting = (SwitchCompat)solo.getView(R.id.sorting_switch_recipe);
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
        SwitchCompat switch_sorting = (SwitchCompat)solo.getView(R.id.sorting_switch_recipe);
        solo.clickOnView(switch_sorting);
        //Need to assert that the first item is the correct sorted item
    }
    @Test
    public void testViewRecipe() {
        testAddRecipe();
        solo.assertCurrentActivity("Wrong Activity!", MainActivity.class);
        BottomNavigationItemView navItem = (BottomNavigationItemView) solo.getView(R.id.recipe_icon);
        solo.clickOnView(navItem.getChildAt(1));
        ListView recipes = (ListView) solo.getView(R.id.recipe_list);
        int size = recipes.getAdapter().getCount();
        for (int i = 0; i < size; i++) {
            Recipe recipe = (Recipe) recipes.getAdapter().getItem(i);
            if (Objects.equals(recipe.getName(), "Test")) {
                solo.clickInList(i);
                break;
            }
        }
    }
    @Test
    public void testAddRecipe() {
        solo.assertCurrentActivity("Wrong Activity!", MainActivity.class);
        BottomNavigationItemView navItem = (BottomNavigationItemView) solo.getView(R.id.recipe_icon);
        solo.clickOnView(navItem.getChildAt(1));
        ListView recipes = (ListView) solo.getView(R.id.recipe_list);
        int size = recipes.getAdapter().getCount();
        solo.clickOnButton("Add Recipe");
        solo.enterText((EditText) solo.getView(R.id.change_recipe_description), "Test");
        assertTrue(solo.waitForText("Test", 1, 2000));
        solo.enterText((EditText) solo.getView(R.id.change_recipe_prep_time), "20");
        assertTrue(solo.waitForText("20", 1, 2000));
        solo.enterText((EditText) solo.getView(R.id.change_recipe_servings), "3");
        assertTrue(solo.waitForText("3", 1, 2000));
        solo.pressSpinnerItem(0, 1);
        assertTrue(solo.waitForText("Baking", 1, 2000));
        solo.enterText((EditText) solo.getView(R.id.change_recipe_comments), "This is a test.");
        assertTrue(solo.waitForText("This is a test.", 1, 2000));
        solo.clickOnButton("Confirm");
        ListView recipesFinal = (ListView) solo.getView(R.id.recipe_list);
        assertEquals(recipesFinal.getAdapter().getCount(), size + 1);
    }
    @Test
    public void testEditRecipe() {
        testAddRecipe();
        solo.assertCurrentActivity("Wrong Activity!", MainActivity.class);
        BottomNavigationItemView navItem = (BottomNavigationItemView) solo.getView(R.id.recipe_icon);
        solo.clickOnView(navItem.getChildAt(1));
        ListView recipes = (ListView) solo.getView(R.id.recipe_list);
        int size = recipes.getAdapter().getCount();
        for (int i = 0; i < size; i++) {
            Recipe recipe = (Recipe) recipes.getAdapter().getItem(i);
            if (Objects.equals(recipe.getName(), "Test")) {
                solo.clickInList(i);
                solo.clickOnImageButton(0);
                solo.enterText((EditText) solo.getView(R.id.change_recipe_description), "Test");
                assertTrue(solo.waitForText("Test", 1, 2000));
                solo.enterText((EditText) solo.getView(R.id.change_recipe_prep_time), "20");
                assertTrue(solo.waitForText("20", 1, 2000));
                solo.enterText((EditText) solo.getView(R.id.change_recipe_servings), "3");
                assertTrue(solo.waitForText("3", 1, 2000));
                solo.pressSpinnerItem(0, 2);
                assertTrue(solo.waitForText("Microwaving", 1, 2000));
                solo.enterText((EditText) solo.getView(R.id.change_recipe_comments), "This is a test.");
                assertTrue(solo.waitForText("This is a test.", 1, 2000));
                solo.clickOnButton("Confirm");
                break;
            }
        }
    }
    @Test
    public void testDeleteRecipe() {
        testAddRecipe();
        solo.assertCurrentActivity("Wrong Activity!", MainActivity.class);
        BottomNavigationItemView navItem = (BottomNavigationItemView) solo.getView(R.id.recipe_icon);
        solo.clickOnView(navItem.getChildAt(1));
        ListView recipes = (ListView) solo.getView(R.id.recipe_list);
        int size = recipes.getAdapter().getCount();
        for (int i = 0; i < size; i++) {
            Recipe recipe = (Recipe) recipes.getAdapter().getItem(i);
            if (Objects.equals(recipe.getName(), "Test")) {
                solo.clickLongInList(i);
                solo.clickOnButton("Yes");
                solo.sleep(2000);
                break;
            }
        }
        ListView recipesFinal = (ListView) solo.getView(R.id.recipe_list);
        assertEquals(recipesFinal.getAdapter().getCount(), size - 1);
    }
    @After
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }
}
