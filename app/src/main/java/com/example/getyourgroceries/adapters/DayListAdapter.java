package com.example.getyourgroceries.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.getyourgroceries.R;
import com.example.getyourgroceries.entity.IngredientStorage;
import com.example.getyourgroceries.entity.MealPlan;
import com.example.getyourgroceries.entity.MealPlanDay;


import java.util.ArrayList;

public class DayListAdapter extends ArrayAdapter<MealPlanDay> {
    private final ArrayList<MealPlanDay> days;
    private final Context context;
    ListView ingredientListView;

    /**
     * Class constructor.
     * @param context Context of the app.
     * @param days List of meal plans.
     */
    public DayListAdapter(Context context, ArrayList<MealPlanDay> days) {
        super(context, 0, days);
        this.days = days;
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

        //Create the view if it doesn't exist
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.day_view, parent, false);
        }
        MealPlanDay day = days.get(position);
        TextView dayName = view.findViewById(R.id.day_title);

        ListView ingredientListview = view.findViewById(R.id.day_ingredient_list);
        ingredientListview.setAdapter(new DayIngredientListAdapter(context, day.getIngredientList()));
        ListView recipeListview = view.findViewById(R.id.day_recipe_list);
        recipeListview.setAdapter(new DayRecipeListAdapter(context, day.getRecipeList()));

        dayName.setText(day.getTitle());

        Button addIngredient = view.findViewById(R.id.add_ingredient_day);
        addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.mealplan_add_ingredient,null);
                ingredientListView = layout.findViewById(R.id.ingredient_list_meal);
                ingredientListView.setAdapter(IngredientStorage.getInstance().getMealIngredientAdapter());
                AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
                alertbox.setView(layout);
                alertbox.setNeutralButton("OK",new DialogInterface.OnClickListener() {public void onClick(DialogInterface arg0, int arg1) {
                }});
                alertbox.show();

            }
        });

        Button addRecipe = view.findViewById(R.id.add_recipe_day);
        addRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        return view;
    }
}
