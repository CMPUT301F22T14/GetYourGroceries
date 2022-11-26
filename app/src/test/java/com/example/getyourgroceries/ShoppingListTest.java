package com.example.getyourgroceries;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import com.example.getyourgroceries.entity.Ingredient;
import com.example.getyourgroceries.entity.ShoppingList;
import org.junit.Test;
import java.util.ArrayList;

public class ShoppingListTest {
    private ShoppingList shoppingList;

    /**
     * create a mock list for shopping list
     * @return
     */
    public ShoppingList MockShoppingList(){
        shoppingList = new ShoppingList("grocery list", new ArrayList<>());
        return shoppingList;
    }

    /**
     * Test getting the name of a shopping list.
     */
    @Test
    public void testGetName() {
        shoppingList = MockShoppingList();
        assertEquals(shoppingList.getName(), "grocery list");
    }

    /**
     * Test editing the name of a shopping list.
     */
    @Test
    public void testEditName() {
        shoppingList = MockShoppingList();
        shoppingList.editName("Birthday Party List");
        assertEquals(shoppingList.getName(), "Birthday Party List");
    }

    /**
     * Test getting the list of ingredients of a shopping list.
     */
    @Test
    public void testGetIngredientList() {
        shoppingList = MockShoppingList();
        assertEquals(shoppingList.getIngredientList().size(), 0);
    }

    /**
     * tests adds ingredient to shopping list
     */
    @Test
    public void addIngredientTest() {
        shoppingList = MockShoppingList();
        Ingredient ingredient = new Ingredient("Apple", 2, 1.99, "Pantry");
        assertEquals(shoppingList.getIngredientList().size(), 0);
        shoppingList.addIngredient(ingredient);
        assertEquals(shoppingList.getIngredientList().size(), 1);
    }

    /**
     * Tests deleting ingredient from shopping list
     */
    @Test
    public void deleteIngredientTest() {
        shoppingList = MockShoppingList();
        Ingredient ingredient = new Ingredient("Apple", 2, 1.99, "Pantry");
        shoppingList.addIngredient(ingredient);
        assertEquals(shoppingList.getIngredientList().size(), 1);
        shoppingList.deleteIngredient(ingredient);
        assertEquals(shoppingList.getIngredientList().size(), 0);
    }

    /**
     * Tests deleting an ingredient that isn't in the list throws and exception
     */
    @Test
    public void deleteIngredientThrowsExceptionTest() {
        shoppingList = MockShoppingList();
        Ingredient ingredient = new Ingredient("Apple", 2, 1.99, "Pantry");
        shoppingList.addIngredient(ingredient);
        assertEquals(shoppingList.getIngredientList().size(), 1);
        shoppingList.deleteIngredient(ingredient);
        assertEquals(shoppingList.getIngredientList().size(), 0);
        assertThrows( IllegalArgumentException.class, () -> {shoppingList.deleteIngredient(ingredient); });
    }
}
