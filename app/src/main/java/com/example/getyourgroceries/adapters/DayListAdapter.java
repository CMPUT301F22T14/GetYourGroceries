package com.example.getyourgroceries.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
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
import com.example.getyourgroceries.fragments.IngredientChangeHandlerFragment;


import java.util.ArrayList;

public class DayListAdapter extends ArrayAdapter<MealPlanDay> {
    private final ArrayList<MealPlanDay> days;
    private final Context context;
    ListView ingredientListView;
    FragmentManager fm;


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
        MealPlanDay day = days.get(position);
        TextView dayName = view.findViewById(R.id.day_title);

        ListView ingredientListview2 = view.findViewById(R.id.day_ingredient_list);
        ingredientListview2.setAdapter(new DayIngredientListAdapter(context, day.getIngredientList()));
        ListView recipeListview = view.findViewById(R.id.day_recipe_list);
        recipeListview.setAdapter(new DayRecipeListAdapter(context, day.getRecipeList()));
        ViewCompat.setNestedScrollingEnabled(ingredientListview2, true);
        ViewCompat.setNestedScrollingEnabled(recipeListview, true);

        dayName.setText(day.getTitle());

        if (MealPlanStorage.getInstance().getRecentIngredient() != null){
            day.addIngredient(MealPlanStorage.getInstance().getRecentIngredient());
            MealPlanStorage.getInstance().setRecentIngredient(null);
        }

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
                AlertDialog a = alertbox.create();
                a.show();
                //Not sure how to get it to reappear after adding ingredient
                Button addIngredient = layout.findViewById(R.id.addMealPlanIngredient);
                addIngredient.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IngredientChangeHandlerFragment ingredientChangeHandlerFragment = new IngredientChangeHandlerFragment();
                        a.dismiss();
                        fm.beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out).
                                replace(R.id.container, ingredientChangeHandlerFragment,"Test").addToBackStack(null).commit();


//                        while(MealPlanStorage.getInstance().getRecentIngredient() == null){
//                        }
//                        day.addIngredient(MealPlanStorage.getInstance().getRecentIngredient());
//                        MealPlanStorage.getInstance().setRecentIngredient(null);


                        //day.addIngredient(); Somehow get the ingredient you just added


                    }
                });
//                while(MealPlanStorage.getInstance().getRecentIngredient() == null){
//                }
//                    day.addIngredient(MealPlanStorage.getInstance().getRecentIngredient());
//                    MealPlanStorage.getInstance().setRecentIngredient(null);

//                while(fm.findFragmentByTag("Test") != null){
//                }
//                day.addIngredient(MealPlanStorage.getInstance().getRecentIngredient());
                //Onclick for adding ingredient to day
                ingredientListView.setOnItemClickListener((adapterView, view, i, l) -> {
                    Ingredient ingredient = (Ingredient) ingredientListView.getItemAtPosition(i);
                    day.addIngredient(ingredient);
                    notifyDataSetChanged();
                    a.dismiss();
                });

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
