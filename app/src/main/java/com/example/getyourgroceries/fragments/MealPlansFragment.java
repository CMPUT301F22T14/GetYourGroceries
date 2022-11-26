package com.example.getyourgroceries.fragments;

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
import com.example.getyourgroceries.entity.MealPlanDay;
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
    public MealPlansFragment(){}

    /**
     * Initialize the view.
     * @param inflater The XML file to inflate.
     * @param container The view container.
     * @param savedInstanceState The saved state.
     * @return The created view.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Meal Plan List");
        MealPlan x = new MealPlan("123","Bulksssss Up");
        MealPlanDay y= new MealPlanDay("1");
        x.addDay(y);
        MealPlanStorage.getInstance().addMealPlan(x,true);
        mealPlanDataList= new ArrayList<>();
        mealPlanDataList.add(x);
        View v = inflater.inflate(R.layout.fragment_meal_plans, container, false);
        mealPlanList = v.findViewById(R.id.meal_plan_list);
        mealPlanAdapter = new MealPlanAdapter(getActivity(),mealPlanDataList);
        mealPlanList.setAdapter(mealPlanAdapter);
        Button addMeal = v.findViewById(R.id.add_meal_plan);
        addMeal.setOnClickListener(v1 -> {
            MealPlanChangeHandlerFragment mealPlanChangeHandlerFragment = new MealPlanChangeHandlerFragment();
            requireActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out).replace(container.getId(), mealPlanChangeHandlerFragment, "MEAL_PLAN_EDIT").addToBackStack(null).commit();
        });
        return v;
    }

}