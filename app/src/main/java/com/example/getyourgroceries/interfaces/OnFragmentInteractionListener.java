package com.example.getyourgroceries.interfaces;

import com.example.getyourgroceries.adapters.DayIngredientListAdapter;
import com.example.getyourgroceries.entity.Ingredient;

 /**
 * Interface for the call back when an ingredient as added to a new recipe or meal plan
 */
public interface OnFragmentInteractionListener {
    void onOkPressed(Ingredient newIngredient);
    void onItemPressed(Ingredient newIngredient, int index);
    void onMealOkPressed(Ingredient newIngredient, DayIngredientListAdapter dayIngredientListAdapter);
}
