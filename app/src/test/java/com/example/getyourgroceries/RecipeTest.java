package com.example.getyourgroceries;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import com.example.getyourgroceries.entity.Ingredient;
import com.example.getyourgroceries.entity.Recipe;

import org.junit.Test;

public class RecipeTest {
    private Recipe recipe;

    /**
     * create a mock recipe
     * @return mocked recipe
     */
    public Recipe MockShoppingList(){
        recipe = new Recipe("Apple Pie", 60, 1, "Baking", "Let Cool", "/images/apple-pie");
        return recipe;
    }

    /**
     * tests adds ingredient to recipe
     */
    @Test
    public void addIngredientTest() {
        recipe = MockShoppingList();
        Ingredient ingredient = new Ingredient("Apple", 2, 1.99, "Pantry");
        assertEquals(recipe.getIngredientList().size(), 0);
        recipe.addIngredient(ingredient);
        assertEquals(recipe.getIngredientList().size(), 1);
    }

    /**
     * Tests deleting ingredient from recipe
     */
    @Test
    public void deleteIngredientTest() {
        recipe = MockShoppingList();
        Ingredient ingredient = new Ingredient("Apple", 2, 1.99, "Pantry");
        recipe.addIngredient(ingredient);
        assertEquals(recipe.getIngredientList().size(), 1);
        recipe.deleteIngredient(ingredient);
        assertEquals(recipe.getIngredientList().size(), 0);
    }

    /**
     * Tests deleting an ingredient that isn't in the recipe throws and exception
     */
    @Test
    public void deleteIngredientThrowsExceptionTest() {
        recipe = MockShoppingList();
        Ingredient ingredient = new Ingredient("Apple", 2, 1.99, "Pantry");
        recipe.addIngredient(ingredient);
        assertEquals(recipe.getIngredientList().size(), 1);
        recipe.deleteIngredient(ingredient);
        assertEquals(recipe.getIngredientList().size(), 0);
        assertThrows( IllegalArgumentException.class, () -> {recipe.deleteIngredient(ingredient); });
    }
}
