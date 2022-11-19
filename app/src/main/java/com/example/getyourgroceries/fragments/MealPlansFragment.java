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
        MealPlan x = new MealPlan("Bulk Up", "Apple Pie","123");
        MealPlan y = new MealPlan("Bulk Up", "Apple Pie","123");
        MealPlan z = new MealPlan("Bulk Up", "Apple Pie","123");
        MealPlan a = new MealPlan("Bulk Up", "Apple Pie","123");
        MealPlan b = new MealPlan("Bulk Up", "Apple Pie","123");
        MealPlan c = new MealPlan("Bulk Up", "Apple Pie","123");
        MealPlan d = new MealPlan("Bulk Up", "Apple Pie","123");
        MealPlan e = new MealPlan("Bulk Up", "Apple Pie","123");
        MealPlan f = new MealPlan("Bulk Up", "Apple Pie","123");
        MealPlan g = new MealPlan("Bulk Up", "Apple Pie","123");
        MealPlan h = new MealPlan("Bulk Up", "Apple Pie","123");
        MealPlan i = new MealPlan("Bulk Up", "Apple Pie","123");
        MealPlan j = new MealPlan("Bulk Up", "Apple Pie","123");
        MealPlan k = new MealPlan("Bulk Up", "Apple Pie","123");
        MealPlan l = new MealPlan("Bulk Up", "Apple Pie","123");
        MealPlan m = new MealPlan("Bulk Up", "Apple Pie","123");
        MealPlan n = new MealPlan("Bulk Up", "Apple Pie","123");
        MealPlan o = new MealPlan("Bulk Up", "Apple Pie","123");
        mealPlanDataList= new ArrayList<MealPlan>();
        mealPlanDataList.add(x);
        mealPlanDataList.add(y);
        mealPlanDataList.add(z);
        mealPlanDataList.add(a);
        mealPlanDataList.add(b);
        mealPlanDataList.add(c);
        mealPlanDataList.add(d);
        mealPlanDataList.add(e);
        mealPlanDataList.add(f);
        mealPlanDataList.add(g);
        mealPlanDataList.add(h);
        mealPlanDataList.add(i);
        mealPlanDataList.add(j);
        mealPlanDataList.add(k);
        mealPlanDataList.add(l);
        mealPlanDataList.add(m);
        mealPlanDataList.add(n);
        mealPlanDataList.add(o);

        View v = inflater.inflate(R.layout.fragment_meal_plans, container, false);
        mealPlanList = v.findViewById(R.id.meal_plan_list);
        mealPlanAdapter = new MealPlanAdapter(getActivity(),mealPlanDataList);
        mealPlanList.setAdapter(mealPlanAdapter);

        return v;
    }
}