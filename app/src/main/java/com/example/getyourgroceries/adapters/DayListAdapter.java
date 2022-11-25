package com.example.getyourgroceries.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.getyourgroceries.MainActivity;
import com.example.getyourgroceries.R;
import com.example.getyourgroceries.entity.Ingredient;
import com.example.getyourgroceries.entity.IngredientStorage;
import com.example.getyourgroceries.entity.MealPlan;
import com.example.getyourgroceries.entity.MealPlanDay;
import com.example.getyourgroceries.entity.MealPlanStorage;
import com.example.getyourgroceries.fragments.AddIngredientRecipeFragment;
import com.example.getyourgroceries.fragments.IngredientChangeHandlerFragment;


import java.util.ArrayList;

public class DayListAdapter extends ArrayAdapter<MealPlanDay> implements AddIngredientRecipeFragment.OnFragmentInteractionListener {
    private final ArrayList<MealPlanDay> days;
    private final Context context;
    ListView ingredientListView;
    FragmentManager fm;
    MealPlanDay day;
    ListView dayIngredientListView;
    DayIngredientListAdapter dayIngredientListAdapter;

    /**
     * Class constructor.
     * @param context Context of the app.
     * @param days List of meal plans.
     */
    public DayListAdapter(Context context, ArrayList<MealPlanDay> days, FragmentManager fm) {
        super(context, 0, days);
        this.days = days;
        this.context = context;
        this.fm = fm;
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
        day = days.get(position);
        TextView dayName = view.findViewById(R.id.day_title);
        dayIngredientListView = view.findViewById(R.id.day_ingredient_list);
        dayIngredientListAdapter = new DayIngredientListAdapter(context, day.getIngredientList());
        dayIngredientListView.setAdapter(dayIngredientListAdapter);
        ListView recipeListview = view.findViewById(R.id.day_recipe_list);
        recipeListview.setAdapter(new DayRecipeListAdapter(context, day.getRecipeList()));
        ViewCompat.setNestedScrollingEnabled(dayIngredientListView, true);
        ViewCompat.setNestedScrollingEnabled(recipeListview, true);

        dayName.setText(day.getTitle());



        Button addIngredient = view.findViewById(R.id.add_ingredient_day);
        addIngredient.setOnClickListener(view12 -> new AddIngredientRecipeFragment(this,position).show(fm, "ADD_INGREDIENT_RECIPE"));

        Button addRecipe = view.findViewById(R.id.add_recipe_day);
        addRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    /**
     * Executes when the user hits "ok" on the add ingredient dialog
     *
     * @param newIngredient item to add to recipe
     */
    @Override
    public void onOkPressed(Ingredient newIngredient) {
        day.addIngredient(newIngredient);
        dayIngredientListAdapter.notifyDataSetChanged();
//        if (!ingredientList.contains(newIngredient)) {
//            ingredientList.add(newIngredient);
//            ingredientAdapter.notifyDataSetChanged();
//        }
    }

    /**
     * Executes when the user hits "ok" on the edit ingredient dialog
     * @param newIngredient updated ingredient info
     * @param index position in ingredient list
     */
    @Override
    public void onItemPressed(Ingredient newIngredient, int index) {
//        ingredientList.set(index, newIngredient);
//        ingredientAdapter.notifyDataSetChanged();
    }
    @Override
    public void onMealOkPressed(Ingredient newIngredient,int position) {
        days.get(position).addIngredient(newIngredient);
//        day.addIngredient(newIngredient);
        dayIngredientListAdapter.notifyDataSetChanged();
//        if (!ingredientList.contains(newIngredient)) {
//            ingredientList.add(newIngredient);
//            ingredientAdapter.notifyDataSetChanged();
//        }
    }


}
