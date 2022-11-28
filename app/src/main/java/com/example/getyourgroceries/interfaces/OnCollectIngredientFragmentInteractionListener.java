package com.example.getyourgroceries.interfaces;

import com.example.getyourgroceries.entity.StoredIngredient;

/**
 * Interface for shopping list collect ingredient callbacks
 */
public interface OnCollectIngredientFragmentInteractionListener {
    void onSubmitPressed(StoredIngredient newIngredient);
}
