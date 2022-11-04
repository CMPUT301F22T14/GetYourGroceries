package com.example.getyourgroceries;

import static org.junit.Assert.assertEquals;

import com.example.getyourgroceries.entity.Ingredient;
import com.example.getyourgroceries.entity.IngredientStorage;
import com.example.getyourgroceries.entity.StoredIngredient;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Optional;

public class IngredientTest {
    private Ingredient ingredient;
    private Ingredient ingredient2;

    /**
     * create a mock list for ingredient
     * @return mocked ingredient
     */
    public Ingredient MockIngredient(){
        ingredient = new Ingredient("Apple", 12, 0.99, "Fruit");
        return ingredient;
    }


//    public IngredientStorage MockIngredientStorage(){
//        IngredientStorage ingredientStorage = new IngredientStorage();
//        ingredient = new Ingredient("Apple", 12, 0.99, "Fruit");
//        ingredient2 = new Ingredient("Banana", 14, 2.00, "Not Fruit");
//        return IngredientStorage;
//    }

    /**
     * test description getter
     */
    @Test
    public void testGetDescription() {
        ingredient = MockIngredient();
        assertEquals("Apple", ingredient.getDescription());
    }

    /**
     * test description setter
     */
    @Test
    public void testSetDescription() {
        ingredient = MockIngredient();
        ingredient.setDescription("Orange");
        assertEquals("Orange", ingredient.getDescription());
    }

    /**
     * test amount getter
     */
    @Test
    public void testGetAmount() {
        ingredient = MockIngredient();
        assertEquals(Optional.of(12), Optional.of(ingredient.getAmount()));
    }

    /**
     * test amount setter
     */
    @Test
    public void testSetAmount() {
        ingredient = MockIngredient();
        ingredient.setAmount(11);
        assertEquals(Optional.of(11), Optional.of(ingredient.getAmount()));
    }

    /**
     * test unit getter
     */
    @Test
    public void testGetUnit() {
        ingredient = MockIngredient();
        assertEquals(Optional.of(0.99), Optional.of(ingredient.getUnit()));
    }

    /**
     * test unit setter
     */
    @Test
    public void testSetUnit() {
        ingredient = MockIngredient();
        ingredient.setUnit(1.99);
        assertEquals(Optional.of(1.99), Optional.of(ingredient.getUnit()));
    }

    /**
     * test category getter
     */
    @Test
    public void testGetCategory() {
        ingredient = MockIngredient();
        assertEquals("Fruit", ingredient.getCategory());
    }

    /**
     * test category setter
     */
    @Test
    public void testSetCategory() {
        ingredient = MockIngredient();
        ingredient.setCategory("Vegetable");
        assertEquals("Vegetable", ingredient.getCategory());
    }

}
