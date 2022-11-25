package com.example.getyourgroceries;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.core.widget.NestedScrollView;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.getyourgroceries.entity.Ingredient;
import com.example.getyourgroceries.entity.IngredientStorage;
import com.example.getyourgroceries.entity.Recipe;
import com.example.getyourgroceries.entity.RecipeStorage;
import com.example.getyourgroceries.entity.StoredIngredient;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Objects;

/**
 * All UI tests for recipes
 */
public class RecipeFragmentTest {
    private Solo solo;
    private Recipe viewRecipeTest;
    private Recipe deleteRecipeTest;
    private Recipe editRecipeTest;
    private Recipe sortRecipeTest1;
    private Recipe sortRecipeTest2;
    private Recipe sortRecipeTest3;
    private Recipe sortRecipeTest4;

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class, true, true);

    @Before
    public void setUp() {
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
        // setup for view
        viewRecipeTest = new Recipe("View Test", 2, 1, "Baking", "No comments", "recipes/apple.jpg");

        // setup for delete
        deleteRecipeTest = new Recipe("Delete Recipe Test", 2, 1, "Baking", "No comments", "recipes/apple.jpg");

        // setup for edit
        ArrayList<Ingredient> editIngredients = new ArrayList<>();
        editIngredients.add(new Ingredient("Ingredient To Delete", 1, 1.9, "Baking"));
        editRecipeTest = new Recipe("Edit Recipe Test", 2, 1, "Baking", "No comments", "recipes/apple.jpg", editIngredients);

        // setup for sorting
        sortRecipeTest1 = new Recipe("Apple Pie", 60, 2, "Baking", "Let cool for 30min", "recipes/apple.jpg");
        sortRecipeTest2 = new Recipe("Dumplings", 15, 7, "Frying", "Don't overcook", "recipes/apple.jpg");
        sortRecipeTest3 = new Recipe("Popcorn", 2, 10, "Microwaving", "Can burn", "recipes/apple.jpg");
        sortRecipeTest4 = new Recipe("Steak + Lobster", 120, 1, "Cooking", "Yum Yum", "recipes/apple.jpg");
    }

    @Test
    public void start() {
        Activity activity = rule.getActivity();
    }

    /**
     * Tests the bottom navigation click to get to the recipes fragment
     * User Stories:
     * - US 02.07.01
     */
    @Test
    public void testGoToRecipes() {
        solo.assertCurrentActivity("Wrong Activity!", MainActivity.class);
        BottomNavigationItemView navItem = (BottomNavigationItemView) solo.getView(R.id.recipe_icon);
        solo.clickOnView(navItem.getChildAt(1));

        assertTrue(solo.waitForText("Recipe List"));
    }

    /**
     * Tests soring by Names Ascending
     * User Stories:
     * - US 02.08.01
     */
    @Test
    public void testSortingNameAsc() {
        RecipeStorage.getInstance().addRecipe(sortRecipeTest1, false);
        RecipeStorage.getInstance().addRecipe(sortRecipeTest2, false);
        RecipeStorage.getInstance().addRecipe(sortRecipeTest3, false);
        RecipeStorage.getInstance().addRecipe(sortRecipeTest4, false);
        solo.assertCurrentActivity("Wrong Activity!", MainActivity.class);
        BottomNavigationItemView navItem = (BottomNavigationItemView) solo.getView(R.id.recipe_icon);
        solo.clickOnView(navItem.getChildAt(1));

        solo.clickOnView(solo.getView(R.id.sort_recipe_spinner));
        solo.clickOnText("Name");
        solo.scrollToTop();
        solo.sleep(200);

        ListView recipes = (ListView) solo.getView(R.id.recipe_list);
        int size = recipes.getAdapter().getCount();

        // loop through list view elements and make sure they are ascending
        for (int i = 0; i < size - 1; i++) {
            Recipe rec1 = (Recipe) recipes.getAdapter().getItem(i);
            Recipe rec2 = (Recipe) recipes.getAdapter().getItem(i + 1);
            String s = rec1.getName();
            String s2 = rec2.getName();
            assertTrue((s.compareTo(s2)) <= 0);
        }
    }

    /**
     * Tests soring by Names Descending
     * User Stories:
     * - US 02.08.01
     */
    @Test
    public void testSortingNameDsc() {
        RecipeStorage.getInstance().addRecipe(sortRecipeTest1, false);
        RecipeStorage.getInstance().addRecipe(sortRecipeTest2, false);
        RecipeStorage.getInstance().addRecipe(sortRecipeTest3, false);
        RecipeStorage.getInstance().addRecipe(sortRecipeTest4, false);
        solo.assertCurrentActivity("Wrong Activity!", MainActivity.class);
        BottomNavigationItemView navItem = (BottomNavigationItemView) solo.getView(R.id.recipe_icon);
        solo.clickOnView(navItem.getChildAt(1));

        solo.clickOnView(solo.getView(R.id.sort_recipe_spinner));
        solo.clickOnText("Name");
        solo.scrollToTop();
        solo.clickOnView(solo.getView(R.id.sorting_switch_recipe));
        solo.sleep(200);


        ListView recipes = (ListView) solo.getView(R.id.recipe_list);
        int size = recipes.getAdapter().getCount();

        // loop through list view elements and make sure they are descending
        for (int i = 0; i < size - 1; i++) {
            Recipe rec1 = (Recipe) recipes.getAdapter().getItem(i);
            Recipe rec2 = (Recipe) recipes.getAdapter().getItem(i + 1);
            String s = rec1.getName();
            String s2 = rec2.getName();
            assertTrue((s.compareTo(s2)) >= 0);
        }
    }

    /**
     * Tests soring by Prep Time Ascending
     * User Stories:
     * - US 02.08.01
     */
    @Test
    public void testSortingPrepAsc() {
        RecipeStorage.getInstance().addRecipe(sortRecipeTest1, false);
        RecipeStorage.getInstance().addRecipe(sortRecipeTest2, false);
        RecipeStorage.getInstance().addRecipe(sortRecipeTest3, false);
        RecipeStorage.getInstance().addRecipe(sortRecipeTest4, false);
        solo.assertCurrentActivity("Wrong Activity!", MainActivity.class);
        BottomNavigationItemView navItem = (BottomNavigationItemView) solo.getView(R.id.recipe_icon);
        solo.clickOnView(navItem.getChildAt(1));

        solo.clickOnView(solo.getView(R.id.sort_recipe_spinner));
        solo.clickOnText("Prep Time");
        solo.scrollToTop();
        solo.sleep(200);

        ListView recipes = (ListView) solo.getView(R.id.recipe_list);
        int size = recipes.getAdapter().getCount();

        // loop through list view elements and make sure they are ascending
        for (int i = 0; i < size - 1; i++) {
            Recipe rec1 = (Recipe) recipes.getAdapter().getItem(i);
            Recipe rec2 = (Recipe) recipes.getAdapter().getItem(i + 1);
            int s = rec1.getPrepTime();
            int s2 = rec2.getPrepTime();
            assertTrue(s <= s2);
        }
    }

    /**
     * Tests soring by Prep Time Descending
     * User Stories:
     * - US 02.08.01
     */
    @Test
    public void testSortingPrepDsc() {
        RecipeStorage.getInstance().addRecipe(sortRecipeTest1, false);
        RecipeStorage.getInstance().addRecipe(sortRecipeTest2, false);
        RecipeStorage.getInstance().addRecipe(sortRecipeTest3, false);
        RecipeStorage.getInstance().addRecipe(sortRecipeTest4, false);
        solo.assertCurrentActivity("Wrong Activity!", MainActivity.class);
        BottomNavigationItemView navItem = (BottomNavigationItemView) solo.getView(R.id.recipe_icon);
        solo.clickOnView(navItem.getChildAt(1));


        solo.clickOnView(solo.getView(R.id.sort_recipe_spinner));
        solo.clickOnText("Prep Time");
        solo.scrollToTop();
        solo.clickOnView(solo.getView(R.id.sorting_switch_recipe));
        solo.sleep(200);

        ListView recipes = (ListView) solo.getView(R.id.recipe_list);
        int size = recipes.getAdapter().getCount();

        // loop through list view elements and make sure they are descending
        for (int i = 0; i < size - 1; i++) {
            Recipe rec1 = (Recipe) recipes.getAdapter().getItem(i);
            Recipe rec2 = (Recipe) recipes.getAdapter().getItem(i + 1);
            int s = rec1.getPrepTime();
            int s2 = rec2.getPrepTime();
            assertTrue(s >= s2);
        }
    }

    /**
     * Tests soring by Serving Size Ascending
     * User Stories:
     * - US 02.08.01
     */
    @Test
    public void testSortingServingAsc() {
        RecipeStorage.getInstance().addRecipe(sortRecipeTest1, false);
        RecipeStorage.getInstance().addRecipe(sortRecipeTest2, false);
        RecipeStorage.getInstance().addRecipe(sortRecipeTest3, false);
        RecipeStorage.getInstance().addRecipe(sortRecipeTest4, false);
        solo.assertCurrentActivity("Wrong Activity!", MainActivity.class);
        BottomNavigationItemView navItem = (BottomNavigationItemView) solo.getView(R.id.recipe_icon);
        solo.clickOnView(navItem.getChildAt(1));

        solo.clickOnView(solo.getView(R.id.sort_recipe_spinner));
        solo.clickOnText("Serving Count");
        solo.scrollToTop();
        solo.sleep(200);

        ListView recipes = (ListView) solo.getView(R.id.recipe_list);
        int size = recipes.getAdapter().getCount();

        // loop through list view elements and make sure they are ascending
        for (int i = 0; i < size - 1; i++) {
            Recipe rec1 = (Recipe) recipes.getAdapter().getItem(i);
            Recipe rec2 = (Recipe) recipes.getAdapter().getItem(i + 1);
            int s = rec1.getNumOfServings();
            int s2 = rec2.getNumOfServings();
            assertTrue(s <= s2);
        }
    }

    /**
     * Tests soring by Serving Size Descending
     * User Stories:
     * - US 02.08.01
     */
    @Test
    public void testSortingServingDsc() {
        RecipeStorage.getInstance().addRecipe(sortRecipeTest1, false);
        RecipeStorage.getInstance().addRecipe(sortRecipeTest2, false);
        RecipeStorage.getInstance().addRecipe(sortRecipeTest3, false);
        RecipeStorage.getInstance().addRecipe(sortRecipeTest4, false);
        solo.assertCurrentActivity("Wrong Activity!", MainActivity.class);
        BottomNavigationItemView navItem = (BottomNavigationItemView) solo.getView(R.id.recipe_icon);
        solo.clickOnView(navItem.getChildAt(1));

        solo.clickOnView(solo.getView(R.id.sort_recipe_spinner));
        solo.clickOnText("Serving Count");
        solo.scrollToTop();
        solo.clickOnView(solo.getView(R.id.sorting_switch_recipe));
        solo.sleep(200);

        ListView recipes = (ListView) solo.getView(R.id.recipe_list);
        int size = recipes.getAdapter().getCount();

        // loop through list view elements and make sure they are descending
        for (int i = 0; i < size - 1; i++) {
            Recipe rec1 = (Recipe) recipes.getAdapter().getItem(i);
            Recipe rec2 = (Recipe) recipes.getAdapter().getItem(i + 1);
            int s = rec1.getNumOfServings();
            int s2 = rec2.getNumOfServings();
            assertTrue(s >= s2);
        }
    }

    /**
     * Tests soring by Category Ascending
     * User Stories:
     * - US 02.08.01
     */
    @Test
    public void testSortingCategoryAsc() {
        RecipeStorage.getInstance().addRecipe(sortRecipeTest1, false);
        RecipeStorage.getInstance().addRecipe(sortRecipeTest2, false);
        RecipeStorage.getInstance().addRecipe(sortRecipeTest3, false);
        RecipeStorage.getInstance().addRecipe(sortRecipeTest4, false);
        solo.assertCurrentActivity("Wrong Activity!", MainActivity.class);
        BottomNavigationItemView navItem = (BottomNavigationItemView) solo.getView(R.id.recipe_icon);
        solo.clickOnView(navItem.getChildAt(1));

        solo.clickOnView(solo.getView(R.id.sort_recipe_spinner));
        solo.clickOnText("Category Type");
        solo.scrollToTop();
        solo.sleep(200);

        ListView recipes = (ListView) solo.getView(R.id.recipe_list);
        int size = recipes.getAdapter().getCount();

        // loop through list view elements and make sure they are ascending
        for (int i = 0; i < size - 1; i++) {
            Recipe rec1 = (Recipe) recipes.getAdapter().getItem(i);
            Recipe rec2 = (Recipe) recipes.getAdapter().getItem(i + 1);
            String s = rec1.getRecipeCategory();
            String s2 = rec2.getRecipeCategory();
            assertTrue((s.compareTo(s2)) <= 0);
        }
    }

    /**
     * Tests soring by Category Descending
     * User Stories:
     * - US 02.08.01
     */
    @Test
    public void testSortingCategoryDsc() {
        RecipeStorage.getInstance().addRecipe(sortRecipeTest1, false);
        RecipeStorage.getInstance().addRecipe(sortRecipeTest2, false);
        RecipeStorage.getInstance().addRecipe(sortRecipeTest3, false);
        RecipeStorage.getInstance().addRecipe(sortRecipeTest4, false);
        solo.assertCurrentActivity("Wrong Activity!", MainActivity.class);
        BottomNavigationItemView navItem = (BottomNavigationItemView) solo.getView(R.id.recipe_icon);
        solo.clickOnView(navItem.getChildAt(1));

        solo.clickOnView(solo.getView(R.id.sort_recipe_spinner));
        solo.clickOnText("Category Type");
        solo.clickOnView(solo.getView(R.id.sorting_switch_recipe));
        solo.scrollToTop();
        solo.sleep(200);

        ListView recipes = (ListView) solo.getView(R.id.recipe_list);
        int size = recipes.getAdapter().getCount();

        // loop through list view elements and make sure they are ascending
        for (int i = 0; i < size - 1; i++) {
            Recipe rec1 = (Recipe) recipes.getAdapter().getItem(i);
            Recipe rec2 = (Recipe) recipes.getAdapter().getItem(i + 1);
            String s = rec1.getRecipeCategory();
            String s2 = rec2.getRecipeCategory();
            assertTrue((s.compareTo(s2)) >= 0);
        }
    }

    /**
     * Tests the Recipe View page
     * User Stories:
     * - US 02.04.01
     */
    @Test
    public void testViewRecipe() {
        RecipeStorage.getInstance().addRecipe(viewRecipeTest, false);
        solo.assertCurrentActivity("Wrong Activity!", MainActivity.class);
        BottomNavigationItemView navItem = (BottomNavigationItemView) solo.getView(R.id.recipe_icon);
        solo.clickOnView(navItem.getChildAt(1));

        ListView recipes = (ListView) solo.getView(R.id.recipe_list);
        int size = recipes.getAdapter().getCount();

        // Click on recipe in list
        for (int i = 0; i < size; i++) {
            Recipe recipe = (Recipe) recipes.getAdapter().getItem(i);
            if (Objects.equals(recipe.getName(), "View Test")) {
                solo.clickInList(i);
                break;
            }
        }

        // Confirm we are on the correct page
        TextView viewTitle = (TextView) solo.getView(R.id.titleTextField);
        assertEquals(viewTitle.getText().toString(), "View Test");
    }

    /**
     * Add recipe, add ingredient to recipe
     * User Stories:
     * - US 02.01.01
     * - US 02.02.01
     */
    @Test
    public void testAddRecipe() {
        solo.assertCurrentActivity("Wrong Activity!", MainActivity.class);
        BottomNavigationItemView navItem = (BottomNavigationItemView) solo.getView(R.id.recipe_icon);
        solo.clickOnView(navItem.getChildAt(1));

        ListView recipes = (ListView) solo.getView(R.id.recipe_list);
        int size = recipes.getAdapter().getCount();

        solo.clickOnButton("Add Recipe");

        solo.enterText((EditText) solo.getView(R.id.change_recipe_description), "Add Recipe Test");
        assertTrue(solo.waitForText("Test", 1, 2000));

        solo.enterText((EditText) solo.getView(R.id.change_recipe_prep_time), "20");
        assertTrue(solo.waitForText("20", 1, 2000));

        solo.enterText((EditText) solo.getView(R.id.change_recipe_servings), "3");
        assertTrue(solo.waitForText("3", 1, 2000));

        // Click category
        AutoCompleteTextView categoryView = (AutoCompleteTextView) solo.getView(R.id.change_recipe_category);
        solo.clearEditText(categoryView);
        solo.enterText(categoryView, "Bak");
        solo.clickOnView(categoryView);
        solo.waitForText("Baking");
        solo.clickOnText("Baking");

        solo.enterText((EditText) solo.getView(R.id.change_recipe_comments), "This is a test.");
        assertTrue(solo.waitForText("This is a test.", 1, 2000));

        // TODO: implement adding a photo
        // solo.clickOnView(solo.getView(R.id.change_recipe_add_photo));

        // Add ingredient to recipe
        solo.clickOnView(solo.getView(R.id.change_recipe_add_ingredient));
        solo.enterText((EditText) solo.getView(R.id.change_ingredient_description), "Add Ingredient Recipe Test");
        solo.enterText((EditText) solo.getView(R.id.change_ingredient_quantity), "2");
        AutoCompleteTextView ingredientCategoryView = (AutoCompleteTextView) solo.getView(R.id.change_ingredient_category);
        solo.clearEditText(ingredientCategoryView);
        solo.enterText(ingredientCategoryView, "Fry");
        solo.clickOnView(ingredientCategoryView);
        solo.waitForText("Frying");
        solo.clickOnText("Frying");
        solo.enterText((EditText) solo.getView(R.id.change_ingredient_unit), "1.99");
        solo.clickOnButton("OK");

        // Scroll to bottom
        NestedScrollView scrollView = (NestedScrollView) solo.getView(R.id.change_recipe_layout);
        scrollView.post(() -> {
            scrollView.arrowScroll(ScrollView.FOCUS_DOWN);
            scrollView.arrowScroll(ScrollView.FOCUS_DOWN);
            scrollView.arrowScroll(ScrollView.FOCUS_DOWN);
        });
        solo.sleep(1000);
        solo.clickOnView(solo.getView(R.id.change_recipe_confirm));

        solo.sleep(200);
        assertEquals(size + 1, recipes.getAdapter().getCount());

        // Delete recipe
        for (int i = 0; i < size + 1; i++) {
            Recipe tv = RecipeStorage.getInstance().getRecipeAdapter().getItem(i);
            if (Objects.equals(tv.getName(), "Add Recipe Test")) {
                solo.clickLongInList(i, 0);
                solo.clickOnButton("Yes");
                break;
            }
        }
    }

    /**
     * Tests editing a recipe and deleting an ingredient from a recipe
     * User Stories:
     * - US 02.03.01
     * - US 02.05.01
     */
    @Test
    public void testEditRecipe() {
        RecipeStorage.getInstance().addRecipe(editRecipeTest, true);

        solo.assertCurrentActivity("Wrong Activity!", MainActivity.class);
        BottomNavigationItemView navItem = (BottomNavigationItemView) solo.getView(R.id.recipe_icon);
        solo.clickOnView(navItem.getChildAt(1));
        ListView recipes = (ListView) solo.getView(R.id.recipe_list);
        int size = recipes.getAdapter().getCount();

        for (int i = 0; i < size; i++) {
            Recipe recipe = (Recipe) recipes.getAdapter().getItem(i);
            if (Objects.equals(recipe.getName(), "Edit Recipe Test")) {
                // Go to view page
                solo.clickInList(i);

                // Go to edit page
                solo.clickOnImageButton(0);

                EditText description = (EditText) solo.getView(R.id.change_recipe_description);
                solo.clearEditText(description);
                solo.enterText(description, "Edit Recipe Test Updated");
                assertTrue(solo.waitForText("Edit Recipe Test Updated", 1, 2000));

                EditText prepTime = (EditText) solo.getView(R.id.change_recipe_prep_time);
                solo.clearEditText(prepTime);
                solo.enterText(prepTime, "20");
                assertTrue(solo.waitForText("20", 1, 2000));

                EditText servings = (EditText) solo.getView(R.id.change_recipe_servings);
                solo.clearEditText(servings);
                solo.enterText(servings, "3");
                assertTrue(solo.waitForText("3", 1, 2000));

                // Click category
                AutoCompleteTextView categoryView = (AutoCompleteTextView) solo.getView(R.id.change_recipe_category);
                solo.clearEditText(categoryView);
                solo.enterText(categoryView, "Coo");
                solo.clickOnView(categoryView);
                solo.waitForText("Cooking");
                solo.clickOnText("Cooking");

                EditText comments = (EditText) solo.getView(R.id.change_recipe_comments);
                solo.clearEditText(comments);
                solo.enterText(comments, "This is a test.");
                assertTrue(solo.waitForText("This is a test.", 1, 2000));

                // Scroll to bottom
                NestedScrollView scrollView = (NestedScrollView) solo.getView(R.id.change_recipe_layout);
                scrollView.post(() -> {
                    scrollView.arrowScroll(ScrollView.FOCUS_DOWN);
                    scrollView.arrowScroll(ScrollView.FOCUS_DOWN);
                    scrollView.arrowScroll(ScrollView.FOCUS_DOWN);
                });
                solo.sleep(800);

                // Delete ingredient from recipe
                ListView ingredients = (ListView) solo.getView(R.id.add_ingredients_recipe);
                for (int j = 0; j < size; j++) {
                    Ingredient ing = (Ingredient) ingredients.getAdapter().getItem(j);
                    if (Objects.equals(ing.getDescription(), "Ingredient To Delete")) {
                        solo.clickLongInList(j, 0);
                        solo.clickOnButton("Yes");
                        break;
                    }
                }

                solo.clickOnView(solo.getView(R.id.change_recipe_confirm));
                solo.sleep(200);
                break;
            }
        }

        // Delete recipe
        solo.sleep(1000);
        ListView recipesList = (ListView) solo.getView(R.id.recipe_list);
        for (int i = 0; i < size; i++) {
            Recipe tv = (Recipe) recipesList.getAdapter().getItem(i);
            if (Objects.equals(tv.getName(), "Edit Recipe Test Updated")) {
                solo.clickLongInList(i, 0);
                solo.clickOnButton("Yes");
                break;
            }
        }
    }

    /**
     * Tests deleting a recipe
     * User Stories:
     * - US 02.06.01
     */
    @Test
    public void testDeleteRecipe() {
        RecipeStorage.getInstance().addRecipe(deleteRecipeTest, true);
        solo.assertCurrentActivity("Wrong Activity!", MainActivity.class);
        BottomNavigationItemView navItem = (BottomNavigationItemView) solo.getView(R.id.recipe_icon);
        solo.clickOnView(navItem.getChildAt(1));

        ListView recipes = (ListView) solo.getView(R.id.recipe_list);
        int size = recipes.getAdapter().getCount();

        for (int i = 0; i < size; i++) {
            Recipe tv = RecipeStorage.getInstance().getRecipeAdapter().getItem(i);
            if (Objects.equals(tv.getName(), "Delete Recipe Test")) {
                solo.clickLongInList(i, 0);
                solo.clickOnButton("Yes");
                break;
            }
        }

        solo.sleep(200);
        assertEquals(recipes.getAdapter().getCount(), size - 1);
    }

    @After
    public void tearDown() {
        solo.finishOpenedActivities();
    }
}
