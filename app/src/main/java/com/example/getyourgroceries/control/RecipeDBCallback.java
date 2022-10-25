package com.example.getyourgroceries.control;

import com.example.getyourgroceries.entity.Recipe;

import java.util.ArrayList;

public interface RecipeDBCallback {
    void onCallback(ArrayList<Recipe> value);
}
