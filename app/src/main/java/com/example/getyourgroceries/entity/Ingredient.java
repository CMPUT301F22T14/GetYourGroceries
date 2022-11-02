/* Ingredient class. */
package com.example.getyourgroceries.entity;

/**
 * Create an object to represent an ingredient/
 */
public class Ingredient {

    // Attributes.
    private String description;
    private Integer amount;
    private Double unit;
    private String category;
    private String id;
    protected Ingredient() {}

    /**
     * Constructor for ingredient.
     * @param description The description.
     * @param amount The amount.
     * @param unit The unit.
     * @param category The category.
     */
    public Ingredient(String description, Integer amount, Double unit, String category) {

        // Set the attributes
        this.description = description;
        this.amount = amount;
        this.unit = unit;
        this.category = category;
    }

    /**
     * Get the description.
     * @return The description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description.
     * @param description The description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the amount.
     * @return The amount.
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * Set the amount.
     * @param amount The amount.
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    /**
     * Get the unit.
     * @return The unit.
     */
    public Double getUnit() {
        return unit;
    }

    /**
     * Set the unit.
     * @param unit The unit.
     */
    public void setUnit(Double unit) {
        this.unit = unit;
    }

    /**
     * Get the category.
     * @return The category.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Set the category.
     * @param category The category.
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Get the ID.
     * @return The ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Set the ID.
     * @param id The ID.
     */
    public void setId(String id) {
        this.id = id;
    }
}
