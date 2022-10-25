package com.example.getyourgroceries;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.getyourgroceries.control.IngredientDB;
import com.example.getyourgroceries.control.IngredientDBCallback;
import com.example.getyourgroceries.control.RecipeDB;
import com.example.getyourgroceries.control.RecipeDBCallback;
import com.example.getyourgroceries.entity.Recipe;
import com.example.getyourgroceries.entity.StoredIngredient;

import java.util.ArrayList;

public class RecipeListFragment extends Fragment {

    public RecipeListFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        RecipeDB db = new RecipeDB();
        db.getRecipes(new RecipeDBCallback() {
            @Override
            public void onCallback(ArrayList<Recipe> recipes) {
                recipes.forEach(recipe ->
                                Log.d("RECIPE LIST", recipe.getName()));
//                        getParentFragmentManager().beginTransaction().add(R.id.linearLayoutIngredients, RecipeListFragment.newInstance(recipe)).commit());
            }
        });
        return inflater.inflate(R.layout.fragment_recipe_list, container, false);
    }
}