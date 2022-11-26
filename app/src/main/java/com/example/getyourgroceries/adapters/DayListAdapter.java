package com.example.getyourgroceries.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentManager;

import com.example.getyourgroceries.R;
import com.example.getyourgroceries.entity.Ingredient;
import com.example.getyourgroceries.entity.IngredientStorage;
import com.example.getyourgroceries.entity.MealPlanDay;
import com.example.getyourgroceries.entity.Recipe;
import com.example.getyourgroceries.entity.RecipeStorage;
import com.example.getyourgroceries.entity.ScaledRecipe;
import com.example.getyourgroceries.fragments.AddIngredientRecipeFragment;
import com.example.getyourgroceries.fragments.RecipeChangeHandlerFragment;
import com.example.getyourgroceries.interfaces.OnFragmentInteractionListener;

import java.util.ArrayList;

public class DayListAdapter extends ArrayAdapter<MealPlanDay> implements OnFragmentInteractionListener {
    private final ArrayList<MealPlanDay> days;
    private final Context context;
    FragmentManager fm;
    ListView dayIngredientListView;
    ListView recipeListview;
    MealPlanDay day;

    /**
     * Class constructor.
     *
     * @param context Context of the app.
     * @param days    List of meal plans.
     */
    public DayListAdapter(Context context, ArrayList<MealPlanDay> days, FragmentManager fm) {
        super(context, 0, days);
        this.days = days;
        this.context = context;
        this.fm = fm;
    }

    /**
     * Update the view.
     *
     * @param position    Position of the recipe in the list.
     * @param convertView The view to convert.
     * @param parent      The parent view.
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
        recipeListview = view.findViewById(R.id.day_recipe_list);
        recipeListview.setAdapter(new DayRecipeListAdapter(context, day.getRecipeList()));
        ViewCompat.setNestedScrollingEnabled(dayIngredientListView, true);
        ViewCompat.setNestedScrollingEnabled(recipeListview, true);

        dayName.setText(day.getTitle());
        Button addIngredient = view.findViewById(R.id.add_ingredient_day);

        DayListAdapter classAdapter = this;

        addIngredient.setOnClickListener(v -> {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.mealplan_add_ingredient, null);
            ListView ingredientListView = layout.findViewById(R.id.ingredient_list_meal);
            ingredientListView.setAdapter(IngredientStorage.getInstance().getMealIngredientAdapter());
            AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
            alertbox.setView(layout);
            AlertDialog a = alertbox.create();
            a.show();
            Button addNewIngredient = layout.findViewById(R.id.addMealPlanIngredient);
            addNewIngredient.setOnClickListener(view12 -> {
                new AddIngredientRecipeFragment(classAdapter, dayIngredientListAdapter).show(fm, "ADD_INGREDIENT_RECIPE");
                a.dismiss();
            });

            ingredientListView.setOnItemClickListener((adapterView, view1, i, l) -> {
                Ingredient ingredient = (Ingredient) ingredientListView.getItemAtPosition(i);
                AlertDialog.Builder builder = new AlertDialog.Builder(view1.getRootView().getContext());
                builder.setTitle("How many do you need?");
                // Set up the input
                final EditText input = new EditText(getContext());
                builder.setView(input);
                // Set up the buttons
                builder.setPositiveButton("OK", (dialog, which) -> {
                    day = days.get(position);
                    Ingredient newIngredient = new Ingredient(ingredient.getDescription(), Integer.parseInt(input.getText().toString()), ingredient.getUnit(), ingredient.getCategory());
                    day.addIngredient(newIngredient);
                    notifyDataSetChanged();
                    a.dismiss();
                });
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
                builder.show();
            });
        });

        Button addRecipe = view.findViewById(R.id.add_recipe_day);
        addRecipe.setOnClickListener(v -> {
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.mealplan_add_recipe,null);
            ListView recipeListView = layout.findViewById(R.id.recipe_list_meal);
            recipeListView.setAdapter(RecipeStorage.getInstance().getRecipeAdapter());

            AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
            alertbox.setView(layout);
            AlertDialog a = alertbox.create();
            a.show();
            //Not sure how to get it to reappear after adding ingredient
            Button addDayRecipe = layout.findViewById(R.id.addMealPlanRecipe);
            addDayRecipe.setOnClickListener(v1 -> {
                Bundle bundle = new Bundle();
                bundle.putInt("dayAdd", position);

                RecipeChangeHandlerFragment recipeChangeHandlerFragment = new RecipeChangeHandlerFragment();
                recipeChangeHandlerFragment.setArguments(bundle);
                //a.dismiss();
                a.hide();
                fm.beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out).
                        replace(R.id.container, recipeChangeHandlerFragment,"EDIT_RECIPE").addToBackStack(null).commit();
            });

            recipeListView.setOnItemClickListener((adapterView, view13, i, l) -> {
                AlertDialog.Builder scaleAlertBox = new AlertDialog.Builder(view13.getRootView().getContext());
                Recipe recipe = (Recipe) recipeListView.getItemAtPosition(i);
                scaleAlertBox.setTitle("Input desired scale (default 1)");

                final EditText input = new EditText(view13.getRootView().getContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                scaleAlertBox.setView(input);
                scaleAlertBox.setPositiveButton("OK", (dialog, which) -> {
                    int scale;
                    try {
                        scale = Integer.parseInt(String.valueOf(input.getText()));
                    } catch (NumberFormatException e) {
                        scale = 1;
                    }

                    day.addRecipe(new ScaledRecipe(recipe, scale));

                    notifyDataSetChanged();
                    a.dismiss();
                });

                scaleAlertBox.setNegativeButton("Cancel", (dialog, which) -> {

                });
                scaleAlertBox.show();
                //day.addIngredient(ingredient);
            });

        });

        recipeListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ScaledRecipe scaledRecipe = day.getRecipeList().get(position);

                AlertDialog.Builder scaleAlertBox = new AlertDialog.Builder(view.getRootView().getContext());
                scaleAlertBox.setTitle("Change Scale");

                final EditText input = new EditText(view.getRootView().getContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setText(String.valueOf(scaledRecipe.getScale()));
                scaleAlertBox.setView(input);
                scaleAlertBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int scale;
                        try {
                            scale = Integer.parseInt(String.valueOf(input.getText()));
                        } catch (NumberFormatException e) {
                            scale = 1;
                        }

                        scaledRecipe.setScale(scale);
                        day.updateRecipe(scaledRecipe, position);
                        notifyDataSetChanged();
                    }
                });

                scaleAlertBox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                scaleAlertBox.show();

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
    }

    /**
     * Executes when the user hits "ok" on the edit ingredient dialog
     *
     * @param newIngredient updated ingredient info
     * @param index         position in ingredient list
     */
    @Override
    public void onItemPressed(Ingredient newIngredient, int index) {
        // DO NOT IMPLEMENT
    }

    @Override
    public void onMealOkPressed(Ingredient newIngredient, DayIngredientListAdapter dayIngredientListAdapter) {
        dayIngredientListAdapter.add(newIngredient);
        dayIngredientListAdapter.notifyDataSetChanged();
    }
}
