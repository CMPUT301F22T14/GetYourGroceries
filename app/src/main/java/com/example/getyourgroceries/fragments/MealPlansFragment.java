package com.example.getyourgroceries.fragments;

import androidx.appcompat.app.AlertDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.getyourgroceries.R;
import com.example.getyourgroceries.adapters.MealPlanAdapter;
import com.example.getyourgroceries.entity.MealPlan;
import com.example.getyourgroceries.entity.MealPlanStorage;

import java.util.ArrayList;

/**
 * Create an object to show meal plans.
 * MealPlansFragment extends {@link Fragment}.
 */
public class MealPlansFragment extends Fragment {
    ArrayList<MealPlan> mealPlanDataList;
    MealPlanAdapter mealPlanAdapter;
    ListView mealPlanList;

    public MealPlansFragment() {}

    /**
     * Initialize the view.
     *
     * @param inflater           The XML file to inflate.
     * @param container          The view container.
     * @param savedInstanceState The saved state.
     * @return The created view.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Meal Plan List");
        MealPlanStorage.getInstance().updateStorage();

        View v = inflater.inflate(R.layout.fragment_meal_plans, container, false);
        mealPlanList = v.findViewById(R.id.meal_plan_list);
        mealPlanAdapter = new MealPlanAdapter(getActivity(), mealPlanDataList);
        mealPlanList.setAdapter(MealPlanStorage.getInstance().getMealPlanAdapter());
        Button addMeal = v.findViewById(R.id.add_meal_plan);

        // edit a meal plan on press
        mealPlanList.setOnItemClickListener((adapterView, view, i, l) -> {
            Bundle bundle = new Bundle();
            bundle.putInt("editMealPlan", i);
            MealPlanChangeHandlerFragment mealPlanChangeHandlerFragment = new MealPlanChangeHandlerFragment();
            mealPlanChangeHandlerFragment.setArguments(bundle);
            assert container != null;
            requireActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out).replace(container.getId(), mealPlanChangeHandlerFragment, "MEAL_PLAN_EDIT").addToBackStack(null).commit();
        });

        // delete a meal plan on long press
        mealPlanList.setOnItemLongClickListener((adapterView, view, i, l) -> {
            AlertDialog.Builder builder = new MaterialAlertDialogBuilder(getContext());
            builder.setMessage("Would you like to delete this meal plan?");
            builder.setTitle("Delete Meal Plan");
            builder.setCancelable(true);
            builder.setPositiveButton("Yes", (dialog, which) -> {
                MealPlan mealPlan = (MealPlan) mealPlanList.getItemAtPosition(i);
                MealPlanStorage.getInstance().deleteMealPlan(mealPlan, true);
            });
            builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
            return true;
        });
        // button will add new meal plan
        addMeal.setOnClickListener(v1 -> {
            MealPlanChangeHandlerFragment mealPlanChangeHandlerFragment = new MealPlanChangeHandlerFragment();
            requireActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out).replace(container.getId(), mealPlanChangeHandlerFragment, "MEAL_PLAN_EDIT").addToBackStack(null).commit();
        });

        return v;
    }

}