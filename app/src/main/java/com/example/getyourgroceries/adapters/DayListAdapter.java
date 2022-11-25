package com.example.getyourgroceries.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.getyourgroceries.entity.Recipe;
import com.example.getyourgroceries.entity.RecipeStorage;
import com.example.getyourgroceries.fragments.AddIngredientRecipeFragment;
import com.example.getyourgroceries.fragments.IngredientChangeHandlerFragment;
import com.example.getyourgroceries.fragments.RecipeChangeHandlerFragment;


import java.util.ArrayList;

public class DayListAdapter extends ArrayAdapter<MealPlanDay> implements AddIngredientRecipeFragment.OnFragmentInteractionListener {
    private final ArrayList<MealPlanDay> days;
    private final Context context;
    ListView ingredientListView;
    FragmentManager fm;
    MealPlanDay day;
    ListView dayIngredientListView;
    private static final String TAG = "DayListAdapter";
//    DayIngredientListAdapter dayIngredientListAdapter;

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
        DayIngredientListAdapter dayIngredientListAdapter = new DayIngredientListAdapter(context, day.getIngredientList());
        dayIngredientListView.setAdapter(dayIngredientListAdapter);
        ListView recipeListview = view.findViewById(R.id.day_recipe_list);
        recipeListview.setAdapter(new DayRecipeListAdapter(context, day.getRecipeList()));
        ViewCompat.setNestedScrollingEnabled(dayIngredientListView, true);
        ViewCompat.setNestedScrollingEnabled(recipeListview, true);

        dayName.setText(day.getTitle());
        Button addIngredient = view.findViewById(R.id.add_ingredient_day);
        addIngredient.setOnClickListener(view12 -> new AddIngredientRecipeFragment(this,dayIngredientListAdapter).show(fm, "ADD_INGREDIENT_RECIPE"));

        Button addRecipe = view.findViewById(R.id.add_recipe_day);
        //Log.d(TAG, "onClick: got to fragment");
        addRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: got to fragment");
                LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = inflater.inflate(R.layout.mealplan_add_recipe,null);
                ListView recipeListView = layout.findViewById(R.id.recipe_list_meal);
                recipeListView.setAdapter(RecipeStorage.getInstance().getRecipeAdapter());

                AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
                alertbox.setView(layout);
                AlertDialog a = alertbox.create();
                a.show();
                Log.d(TAG, "onClick: got to fragment");
                //Not sure how to get it to reappear after adding ingredient
                Button addRecipe = layout.findViewById(R.id.addMealPlanRecipe);
                addRecipe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RecipeChangeHandlerFragment recipeChangeHandlerFragment = new RecipeChangeHandlerFragment();
                        //a.dismiss();
                        fm.beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out).
                                replace(R.id.container, recipeChangeHandlerFragment,"Test").addToBackStack(null).commit();


                    }
                });

                recipeListView.setOnItemClickListener((adapterView, view, i, l) -> {
                    AlertDialog.Builder scaleAlertBox = new AlertDialog.Builder(view.getRootView().getContext());
                    Recipe recipe = (Recipe) recipeListView.getItemAtPosition(i);
                    scaleAlertBox.setTitle("Input desired scale");

                    final EditText input = new EditText(view.getRootView().getContext());
                    input.setInputType(InputType.TYPE_CLASS_NUMBER);

                    scaleAlertBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            day.addRecipe(recipe);
                            notifyDataSetChanged();
                            a.dismiss();
                        }
                    });

                    scaleAlertBox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    scaleAlertBox.show();
                    //day.addIngredient(ingredient);


                });

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
//        dayIngredientListAdapter.notifyDataSetChanged();
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
    public void onMealOkPressed(Ingredient newIngredient,DayIngredientListAdapter dayIngredientListAdapter) {
        dayIngredientListAdapter.add(newIngredient);
//        days.get(position).addIngredient(newIngredient);
        dayIngredientListAdapter.notifyDataSetChanged();

    }


}
