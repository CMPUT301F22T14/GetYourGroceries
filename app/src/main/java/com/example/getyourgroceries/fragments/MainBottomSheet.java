package com.example.getyourgroceries.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.getyourgroceries.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class MainBottomSheet extends BottomSheetDialogFragment {
    public static String TAG = "ModalBottomSheet";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.main_bottom_drawer, container, false);

        Button addIngredient = v.findViewById(R.id.quickadd_ingredient);
        Button addRecipe = v.findViewById(R.id.quickadd_recipe);
        Button addMealPlan = v.findViewById(R.id.quickadd_mealplan);

        FrameLayout viewBottomSheet = v.findViewById(R.id.standard_bottom_sheet);

        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(viewBottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetBehavior.setSaveFlags(BottomSheetBehavior.SAVE_ALL);

        addIngredient.setOnClickListener(v2 -> {
            IngredientChangeHandlerFragment ingredientChangeHandlerFragment = new IngredientChangeHandlerFragment();
            dismiss();
            requireActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out).replace(R.id.container, ingredientChangeHandlerFragment).addToBackStack(null).commit();
        });

        addRecipe.setOnClickListener(v2 -> {
            RecipeChangeHandlerFragment recipeChangeHandlerFragment = new RecipeChangeHandlerFragment();
            dismiss();
            requireActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out).replace(R.id.container, recipeChangeHandlerFragment, "EDIT_RECIPE").addToBackStack("EDIT_RECIPE").commit();
        });

        addMealPlan.setOnClickListener(v2 -> {
            MealPlanChangeHandlerFragment mealPlanChangeHandlerFragment = new MealPlanChangeHandlerFragment();
            dismiss();
            requireActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out).replace(R.id.container, mealPlanChangeHandlerFragment).addToBackStack(null).commit();
        });

        return v;
    }
}
