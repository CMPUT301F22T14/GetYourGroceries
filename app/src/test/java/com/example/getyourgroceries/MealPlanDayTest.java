package com.example.getyourgroceries;

import static org.junit.Assert.assertEquals;

import com.example.getyourgroceries.entity.Ingredient;
import com.example.getyourgroceries.entity.MealPlanDay;
import com.example.getyourgroceries.entity.Recipe;
import com.example.getyourgroceries.entity.ScaledRecipe;

import org.junit.Test;

import java.util.ArrayList;

public class MealPlanDayTest {
    private MealPlanDay mealPlanDay;

    /**
     * Create a mock meal plan day.
     * @return The created day.
     */
    public MealPlanDay mockMealPlanDay() {
        mealPlanDay = new MealPlanDay("Sunday");
        return mealPlanDay;
    }

    /**
     * Test adding recipes to meal plan days.
     */
    @Test
    public void addRecipeTest() {
        mealPlanDay = mockMealPlanDay();
        Recipe recipe = new Recipe("Pasta", 25, 2, "Cooking", "I love pasta!", "");
        mealPlanDay.addRecipe(new ScaledRecipe(recipe, 5));
        assertEquals("Pasta", mealPlanDay.getRecipeList().get(0).getRecipe().getName());
    }

    /**
     * Test deleting recipes from meal plan days.
     */
    @Test
    public void deleteRecipeTest() {
        mealPlanDay = mockMealPlanDay();
        Recipe recipe = new Recipe("Pasta", 20, 2, "Cooking", "I love pasta!", "");
        ScaledRecipe scaledRecipe = new ScaledRecipe(recipe, 2);
        mealPlanDay.addRecipe(scaledRecipe);
        mealPlanDay.deleteRecipe(scaledRecipe);
        assertEquals(mealPlanDay.getRecipeList().size(), 0);
    }

    /**
     * Test adding ingredients to meal plan days.
     */
    @Test
    public void addIngredientTest() {
        mealPlanDay = mockMealPlanDay();
        mealPlanDay.addIngredient(new Ingredient("Apple", 4, 0.75, "Fruit"));
        assertEquals("Apple", mealPlanDay.getIngredientList().get(0).getDescription());
    }

    /**
     * Test deleting ingredients from meal plan days.
     */
    @Test
    public void deleteIngredientTest() {
        mealPlanDay = mockMealPlanDay();
        Ingredient ingredient = new Ingredient("Apple", 4, 0.75, "Fruit");
        mealPlanDay.addIngredient(ingredient);
        mealPlanDay.deleteIngredient(ingredient);
        assertEquals(0, mealPlanDay.getIngredientList().size());
    }

    /**
     * Test getting the list of recipes for a meal plan day.
     */
    @Test
    public void getRecipeListTest() {
        mealPlanDay = mockMealPlanDay();
        Recipe recipe = new Recipe("Pasta", 20, 2, "Cooking", "I love pasta!", "");
        ScaledRecipe scaledRecipe = new ScaledRecipe(recipe, 10);
        mealPlanDay.addRecipe(scaledRecipe);
        ArrayList<ScaledRecipe> recipeList = new ArrayList<>();
        recipeList.add(scaledRecipe);
        assertEquals(recipeList, mealPlanDay.getRecipeList());
    }

    /**
     * Test getting the list of ingredients for a meal plan day.
     */
    @Test
    public void getIngredientListTest() {
        mealPlanDay = mockMealPlanDay();
        Ingredient ingredient = new Ingredient("Apple", 4, 0.75, "Fruits");
        mealPlanDay.addIngredient(ingredient);
        ArrayList<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(ingredient);
        assertEquals(ingredientList, mealPlanDay.getIngredientList());
    }

    /**
     * Test getting the title of the meal plan day.
     */
    @Test
    public void getTitleTest() {
        mealPlanDay = mockMealPlanDay();
        assertEquals("Sunday", mealPlanDay.getTitle());
    }

    /**
     * Test setting the title of the meal plan day.
     */
    @Test
    public void setTitleTest() {
        mealPlanDay = mockMealPlanDay();
        mealPlanDay.setTitle("Saturday");
        assertEquals("Saturday", mealPlanDay.getTitle());
    }
}
