package com.example.getyourgroceries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.getyourgroceries.adapters.MealPlanAdapter;
import com.example.getyourgroceries.entity.MealPlan;
import com.example.getyourgroceries.entity.MealPlanDay;
import com.example.getyourgroceries.entity.Recipe;


import java.util.ArrayList;

public class MealPlansFragment extends Fragment {
    ArrayList<MealPlan> mealPlanDataList;
    MealPlanAdapter mealPlanAdapter;
    ListView mealPlanList;

    public MealPlansFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        MealPlan x = new MealPlan("123","Bulksss Up");
        MealPlanDay y= new MealPlanDay("1");
        x.addDay(y);
        
        mealPlanDataList= new ArrayList<MealPlan>();
        mealPlanDataList.add(x);

        View v = inflater.inflate(R.layout.fragment_meal_plans, container, false);
        mealPlanList = v.findViewById(R.id.meal_plan_list);
        mealPlanAdapter = new MealPlanAdapter(getActivity(),mealPlanDataList);
        mealPlanList.setAdapter(mealPlanAdapter);
        return v;
    }
}