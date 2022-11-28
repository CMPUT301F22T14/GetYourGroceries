/* Recipe class. */
package com.example.getyourgroceries.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Create an object to represent a recipe.
 */
public class Recipe {
    private String name;
    private int prepTime;
    private int numOfServings;
    private String recipeCategory;
    private String comment;
    private String photo;
    private final ArrayList<Ingredient> ingredientList;
    private String id;

    /**
     * Constructor for recipe.
     *
     * @param name           The name.
     * @param prepTime       The preparation time.
     * @param numOfServings  The number of servings.
     * @param recipeCategory The category.
     * @param comment        The comments.
     * @param photo          The photo.
     */
    public Recipe(String name, int prepTime, int numOfServings, String recipeCategory, String comment, String photo) {
        this.name = name;
        this.prepTime = prepTime;
        this.numOfServings = numOfServings;
        this.recipeCategory = recipeCategory;
        this.comment = comment;
        this.photo = photo;
        this.ingredientList = new ArrayList<>();
    }

    /**
     * Constructor for recipe including ingredient list.
     *
     * @param name           The name.
     * @param prepTime       The preparation time.
     * @param numOfServings  The number of servings.
     * @param recipeCategory The category.
     * @param comment        The comments.
     * @param photo          The photo.
     * @param ingredients    The list of ingredients
     */
    public Recipe(String name, int prepTime, int numOfServings, String recipeCategory, String comment, String photo, ArrayList<Ingredient> ingredients) {
        this.name = name;
        this.prepTime = prepTime;
        this.numOfServings = numOfServings;
        this.recipeCategory = recipeCategory;
        this.comment = comment;
        this.photo = photo;
        this.ingredientList = ingredients;
    }

    /**
     * Empty constructor for firebase storage
     */
    public Recipe() {
        ingredientList = new ArrayList<>();
    }

    /**
     * Get the name.
     *
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name.
     *
     * @param name The name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the preparation time.
     *
     * @return The preparation time.
     */
    public int getPrepTime() {
        return prepTime;
    }

    /**
     * Set the preparation time.
     *
     * @param prepTime The preparation time.
     */
    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    /**
     * Get the number of servings.
     *
     * @return The number of servings.
     */
    public int getNumOfServings() {
        return numOfServings;
    }

    /**
     * Set the number of servings.
     *
     * @param numOfServings The number of servings.
     */
    public void setNumOfServings(int numOfServings) {
        this.numOfServings = numOfServings;
    }

    /**
     * Get the category.
     *
     * @return The category.
     */
    public String getRecipeCategory() {
        return recipeCategory;
    }

    /**
     * Set the category.
     *
     * @param recipeCategory The category.
     */
    public void setRecipeCategory(String recipeCategory) {
        this.recipeCategory = recipeCategory;
    }

    /**
     * Get the comment.
     *
     * @return The comment.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Set the comment.
     *
     * @param comment The comment.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Get the photo.
     *
     * @return The photo.
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * Set the photo.
     *
     * @param photo The photo.
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * Get the ingredients.
     *
     * @return The ingredients.
     */
    public ArrayList<Ingredient> getIngredientList() {
        return ingredientList;
    }

    /**
     * Add an ingredient to the recipe.
     *
     * @param ingredient The ingredient to add.
     */
    public void addIngredient(Ingredient ingredient) {

        // Add the ingredient if it is not already present.
        if (!ingredientList.contains(ingredient)) {
            ingredientList.add(ingredient);
        }
    }

    /**
     * Delete an ingredient from the recipe.
     *
     * @param ingredient The ingredient to delete.
     */
    public void deleteIngredient(Ingredient ingredient) throws IllegalArgumentException {
        if (!ingredientList.contains(ingredient)) {
            throw new IllegalArgumentException();
        }
        ingredientList.remove(ingredient);
    }

    /**
     * Get the ID.
     *
     * @return The ID.
     */
    public String getId() {
        return id;
    }

    /**
     * Set the ID.
     *
     * @param id The ID.
     */
    public void setId(String id) {
        this.id = id;
    }
}
