package com.example.getyourgroceries.interfaces;
import com.example.getyourgroceries.entity.Recipe;

/**
 * Interface for call back when new recipe is added to a meal plan
 */
public interface OnMealPlanFragmentInteractionListener {
    void onSubmitPressed(Recipe newRecipe, int dayPosition);
}
