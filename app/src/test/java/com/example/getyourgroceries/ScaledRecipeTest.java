package com.example.getyourgroceries;

import static org.junit.Assert.assertEquals;
import com.example.getyourgroceries.entity.Recipe;
import com.example.getyourgroceries.entity.ScaledRecipe;
import org.junit.Test;

/**
 * Create an object to test the ScaledRecipe class.
 * Tests {@link com.example.getyourgroceries.entity.ScaledRecipe}.
 */
public class ScaledRecipeTest {
    private ScaledRecipe scaledRecipe;

    /**
     * Create a mock scaled recipe.
     * @returns The created recipe.
     */
    public ScaledRecipe MockScaledRecipe() {
        scaledRecipe = new ScaledRecipe(new Recipe("Scrambled Eggs", 10, 1, "Breakfast", "I love eggs!", ""), 3);
        return scaledRecipe;
    }

    /**
     * Test getting the recipe of a scaled recipe.
     */
    @Test
    public void testGetRecipe() {
        scaledRecipe = MockScaledRecipe();
        assertEquals(scaledRecipe.getRecipe().getName(), "Scrambled Eggs");
    }

    /**
     * Test getting the scale of a scaled recipe.
     */
    @Test
    public void testGetScale() {
        scaledRecipe = MockScaledRecipe();
        assertEquals(scaledRecipe.getScale(), 3);
    }

    /**
     * Test setting the recipe of a scaled recipe.
     */
    @Test
    public void testSetRecipe() {
        scaledRecipe = MockScaledRecipe();
        scaledRecipe.setRecipe(new Recipe("Grilled Cheese Sandwich", 15, 2, "Lunch", "Cheat meal!", ""));
        assertEquals(scaledRecipe.getRecipe().getName(), "Grilled Cheese Sandwich");
    }

    /**
     * Test setting the scale of a scaled recipe.
     */
    @Test
    public void testSetScale() {
        scaledRecipe = MockScaledRecipe();
        scaledRecipe.setScale(6);
        assertEquals(scaledRecipe.getScale(), 6);
    }
}