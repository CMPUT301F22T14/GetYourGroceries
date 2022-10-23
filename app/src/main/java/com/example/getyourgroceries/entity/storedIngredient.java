package com.example.getyourgroceries.entity;

import java.util.Date;

public class storedIngredient extends Ingredient{
    private Date bestBefore;
    private String location;

    public storedIngredient(String description, Integer amount, Double unit, String category, Date bestBefore, String location) {
        super(description, amount, unit, category);
        this.bestBefore = bestBefore;
        this.location = location;
    }

    public storedIngredient(Ingredient ingredient, Date bestBefore, String location){
        super(ingredient.getDescription(), ingredient.getAmount(), ingredient.getUnit(), ingredient.getCategory());
        this.bestBefore = bestBefore;
        this.location = location;
    }

    public Date getBestBefore() {
        return bestBefore;
    }

    public void setBestBefore(Date bestBefore) {
        this.bestBefore = bestBefore;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
