package com.example.getyourgroceries.interfaces;
import com.example.getyourgroceries.entity.Recipe;

public interface OnMealPlanFragmentInteractionListener {
    void onSubmitPressed(Recipe newRecipe, int dayPosition);
}
