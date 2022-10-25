package com.example.getyourgroceries.entity;

import java.util.ArrayList;

public class Recipe {
    private String name;
    private int prepTime;
    private int numOfServings;
    private String recipeCategory;
    private String comment;
    private String photo;
    private ArrayList<Ingredient> ingredientList;
    private String id;

    public Recipe() {

    }

    public Recipe(String name, int prepTime, int numOfServings, String recipeCategory, String comment, String photo) {
        this.name = name;
        this.prepTime = prepTime;
        this.numOfServings = numOfServings;
        this.recipeCategory = recipeCategory;
        this.comment = comment;
        this.photo = photo;
        this.ingredientList = new ArrayList<Ingredient>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public int getNumOfServings() {
        return numOfServings;
    }

    public void setNumOfServings(int numOfServings) {
        this.numOfServings = numOfServings;
    }

    public String getRecipeCategory() {
        return recipeCategory;
    }

    public void setRecipeCategory(String recipeCategory) {
        this.recipeCategory = recipeCategory;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public ArrayList<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void addIngredient(Ingredient ingredient) {

        if (!ingredientList.contains(ingredient)) {
            ingredientList.add(ingredient);
        } else {
            // already contains ingredient
        }
    }

    public void deleteIngredient(Ingredient ingredient) {

        if (ingredientList.contains(ingredient)) {
            ingredientList.remove(ingredient);
        } else {
            // does not contain ingredient
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
