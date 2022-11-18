package com.example.getyourgroceries.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.getyourgroceries.R;
import com.example.getyourgroceries.entity.MealPlan;
import java.util.ArrayList;

public class MealPlanAdapter extends ArrayAdapter<MealPlan> {
    private final ArrayList<MealPlan> plans;
    private final Context context;

    /**
     * Class constructor.
     * @param context Context of the app.
     * @param plans List of meal plans.
     */
    public MealPlanAdapter(Context context, ArrayList<MealPlan> plans) {
        super(context, 0, plans);
        this.plans = plans;
        this.context = context;
    }

    /**
     * Update the view.
     * @param position Position of the recipe in the list.
     * @param convertView The view to convert.
     * @param parent The parent view.
     * @return The updated view.
     */
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.recipe_item, parent, false);
        }

        // TODO: fill in actual meal plan view

        return view;
    }
}
