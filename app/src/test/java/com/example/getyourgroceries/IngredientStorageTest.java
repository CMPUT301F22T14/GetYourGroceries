package com.example.getyourgroceries;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import android.widget.ArrayAdapter;

import com.example.getyourgroceries.entity.Ingredient;
import com.example.getyourgroceries.entity.IngredientStorage;
import com.example.getyourgroceries.entity.ShoppingList;
import com.example.getyourgroceries.entity.StoredIngredient;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

public class IngredientStorageTest {

    private ArrayAdapter ingredientStorage;

    /**
     * create a mock list for shopping list
     * @return
     */
    public ArrayAdapter<StoredIngredient> MockIngredientStorage(){
//        ingredientStorage = new IngredientStorage();
        return IngredientStorage.getIngredientAdapter();
    }


    /**
     * tests adds ingredient to ingredient storage list
     */
    @Test
    public void addIngredientTest() {
        ingredientStorage = MockIngredientStorage();
        StoredIngredient ingredient = new StoredIngredient("Apple", 2, 1.99, "Category 1",new Date() ,"Fridge");
        IngredientStorage.setupAdapter(null);
        assertEquals(IngredientStorage.getIngredientAdapter().getCount(), 0);
        IngredientStorage.addIngredient(ingredient);
        assertEquals(IngredientStorage.getIngredientAdapter().getCount(), 1);
        assertEquals(ingredient, IngredientStorage.getIngredient(0));
    }

    /**
     * Tests deleting ingredient from ingredient storage
     */
    @Test
    public void deleteIngredientTest() {
        ingredientStorage = MockIngredientStorage();
        StoredIngredient ingredient = new StoredIngredient("Apple", 2, 1.99, "Category 1",new Date() ,"Fridge");
        IngredientStorage.setupAdapter(null);
        assertEquals(IngredientStorage.getIngredientAdapter().getCount(), 0);
        IngredientStorage.addIngredient(ingredient);
        assertEquals(IngredientStorage.getIngredientAdapter().getCount(), 1);
        IngredientStorage.deleteIngredient(ingredient);
        assertEquals(IngredientStorage.getIngredientAdapter().getCount(), 0);
    }

    /**
     * Tests deleting an ingredient that isn't in the list throws and exception
     */
    @Test
    public void deleteIngredientThrowsExceptionTest() {
        ingredientStorage = MockIngredientStorage();
        StoredIngredient ingredient = new StoredIngredient("Apple", 2, 1.99, "Category 1",new Date() ,"Fridge");
        IngredientStorage.setupAdapter(null);
        assertEquals(IngredientStorage.getIngredientAdapter().getCount(), 0);
        IngredientStorage.addIngredient(ingredient);
        assertEquals(IngredientStorage.getIngredientAdapter().getCount(), 1);
        IngredientStorage.deleteIngredient(ingredient);
        assertEquals(IngredientStorage.getIngredientAdapter().getCount(), 0);
        assertThrows( IllegalArgumentException.class, () -> {IngredientStorage.deleteIngredient(ingredient); });
    }

    /**
     * Tests clearing the ingredient storage
     */
    @Test
    public void clearIngredientTest() {
        ingredientStorage = MockIngredientStorage();
        StoredIngredient ingredient = new StoredIngredient("Apple", 2, 1.99, "Category 1",new Date() ,"Fridge");
        IngredientStorage.setupAdapter(null);
        assertEquals(IngredientStorage.getIngredientAdapter().getCount(), 0);
        IngredientStorage.addIngredient(ingredient);
        assertEquals(IngredientStorage.getIngredientAdapter().getCount(), 1);
        IngredientStorage.clearIngredients();
        assertEquals(IngredientStorage.getIngredientAdapter().getCount(), 0);
    }
}
