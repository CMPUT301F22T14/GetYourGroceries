package com.example.getyourgroceries.control;

import com.example.getyourgroceries.entity.StoredIngredient;

import java.util.ArrayList;

public interface IngredientDBCallback {
    void onCallback(ArrayList<StoredIngredient> value);
}

