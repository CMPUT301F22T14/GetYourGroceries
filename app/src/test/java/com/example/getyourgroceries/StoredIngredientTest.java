package com.example.getyourgroceries;

import static org.junit.Assert.assertEquals;

import com.example.getyourgroceries.entity.Ingredient;
import com.example.getyourgroceries.entity.StoredIngredient;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

public class StoredIngredientTest {
    Date newDate;

    /**
     * create a mock list for stored ingredient
     * @return mocked stored ingredient
     */
    public StoredIngredient MockStoredIngredient(){
        Ingredient ingredient = new Ingredient("Apple", 12, 0.99, "Pantry");
        newDate = new Date();
        return new StoredIngredient(ingredient, newDate, "Pantry");
    }

    /**
     * tests best before getter
     */
    @Test
    public void testGetBestBefore() {
        StoredIngredient storedIngredient = MockStoredIngredient();
        assertEquals(storedIngredient.getBestBefore(), newDate);
    }

    /**
     * tests best before setter
     */
    @Test
    public void testSetBestBefore() {
        StoredIngredient storedIngredient = MockStoredIngredient();
        Date updatedDate = new Date();
        storedIngredient.setBestBefore(updatedDate);
        assertEquals(storedIngredient.getBestBefore(), updatedDate);
    }

    /**
     * test location getter
     */
    @Test
    public void testGetLocation() {
       StoredIngredient storedIngredient = MockStoredIngredient();
       assertEquals(storedIngredient.getLocation(), "Pantry");
    }

    /**
     * test location setter
     */
    @Test
    public void testSetLocation() {
        StoredIngredient storedIngredient = MockStoredIngredient();
        storedIngredient.setLocation("Freezer");
        assertEquals(storedIngredient.getLocation(), "Freezer");
    }


}
