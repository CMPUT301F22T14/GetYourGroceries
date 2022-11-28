/* StoredIngredient class. */
package com.example.getyourgroceries.entity;

// Import statements.
import java.util.Date;

/**
 * Create an object to represent ingredients in storage.
 * Extends {@link Ingredient}.
 */
public class StoredIngredient extends Ingredient{

    // Attributes.
    private Date bestBefore;
    private String location;


    StoredIngredient() {}

    /**
     * Constructor for stored ingredients.
     * @param description The description.
     * @param amount The amount.
     * @param unit The unit.
     * @param category The category.
     * @param bestBefore The best before date.
     * @param location The storage location.
     */
    public StoredIngredient(String description, Integer amount, Double unit, String category, Date bestBefore, String location) {
        super(description, amount, unit, category);
        this.bestBefore = bestBefore;
        this.location = location;
    }

    /**
     * Constructor for stored ingredients given an existing ingredient.
     * @param ingredient An existing ingredient.
     * @param bestBefore The best before date.
     * @param location The storage location.
     */
    public StoredIngredient(Ingredient ingredient, Date bestBefore, String location) {
        super(ingredient.getDescription(), ingredient.getAmount(), ingredient.getUnit(), ingredient.getCategory());
        this.bestBefore = bestBefore;
        this.location = location;
    }


    /**
     * Constructor for stored ingredients given an existing ingredient.
     * @param ingredient An existing ingredient.
     * @param bestBefore The best before date.
     * @param location The storage location.
     */
    public StoredIngredient(Ingredient ingredient, Date bestBefore, String location,int quantity) {
        super(ingredient.getDescription(), ingredient.getAmount(), ingredient.getUnit(), ingredient.getCategory());
        this.bestBefore = bestBefore;
        this.location = location;
    }

    /**
     * Get the best before date.
     * @return The best before date.
     */
    public Date getBestBefore() {
        return bestBefore;
    }

    /**
     * Set the best before date.
     * @param bestBefore The best before data.
     */
    public void setBestBefore(Date bestBefore) {
        this.bestBefore = bestBefore;
    }

    /**
     * Get the storage location.
     * @return The storage location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Set the storage location.
     * @param location The storage location.
     */
    public void setLocation(String location) {
        this.location = location;
    }
}
