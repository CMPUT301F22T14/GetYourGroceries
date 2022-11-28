package com.example.getyourgroceries.adapters;

import androidx.appcompat.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.example.getyourgroceries.R;
import com.example.getyourgroceries.entity.Ingredient;
import com.example.getyourgroceries.entity.IngredientStorage;
import com.example.getyourgroceries.entity.MealPlanDay;
import com.example.getyourgroceries.entity.Recipe;
import com.example.getyourgroceries.entity.RecipeStorage;
import com.example.getyourgroceries.entity.ScaledRecipe;
import com.example.getyourgroceries.entity.StoredIngredient;
import com.example.getyourgroceries.fragments.AddIngredientRecipeFragment;
import com.example.getyourgroceries.fragments.RecipeChangeHandlerFragment;
import com.example.getyourgroceries.interfaces.OnFragmentInteractionListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Adapter for displaying meal plan days
 */
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

        //configure ingredient view for day
        dayIngredientListView = view.findViewById(R.id.day_ingredient_list);
        DayIngredientListAdapter dayIngredientListAdapter = new DayIngredientListAdapter(context, day.getIngredientList());
        dayIngredientListView.setAdapter(dayIngredientListAdapter);

        // configure recipe view for day
        recipeListview = view.findViewById(R.id.day_recipe_list);
        DayRecipeListAdapter dayRecipeListAdapter = new DayRecipeListAdapter(context, day.getRecipeList());
        recipeListview.setAdapter(dayRecipeListAdapter);

        dayIngredientListView.setNestedScrollingEnabled(true);
        recipeListview.setNestedScrollingEnabled(true);

        dayName.setText(day.getTitle());
        Button addIngredient = view.findViewById(R.id.add_ingredient_day);

        DayListAdapter classAdapter = this;
        dayIngredientListView.setOnItemClickListener((adapterView, view12, i, l) -> new AddIngredientRecipeFragment(days, i, position, classAdapter, dayIngredientListAdapter).show(fm, "EDIT_INGREDIENT_RECIPE"));

        Button deleteButton = view.findViewById(R.id.delete_day);
        deleteButton.setOnClickListener((v) -> {
            days.remove(position);
            // Fix ordering on days
            int i = 1;
            for (MealPlanDay day: days) {
                day.setTitle("Day " + i);
                i++;
            }

            this.notifyDataSetChanged();
        });
        // Long press to delete ingredient
        dayIngredientListView.setOnItemLongClickListener((adapterView, view2, i, l) -> {
            AlertDialog.Builder builder = new MaterialAlertDialogBuilder(view2.getRootView().getContext());
            builder.setMessage("Would you like to delete this ingredient?");
            builder.setTitle("Delete Ingredient");
            builder.setCancelable(true);
            builder.setPositiveButton("Yes", (dialog, which) -> {
                Ingredient ingredient = (Ingredient) dayIngredientListAdapter.getItem(i);
                dayIngredientListAdapter.remove(ingredient);
                dayIngredientListAdapter.notifyDataSetChanged();
            });
            builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
            return true;
        });

        // Long press to delete recipe
        recipeListview.setOnItemLongClickListener((adapterView, view2, i, l) -> {
            AlertDialog.Builder builder = new MaterialAlertDialogBuilder(view2.getRootView().getContext());
            builder.setMessage("Would you like to delete this recipe?");
            builder.setTitle("Delete Recipe");
            builder.setCancelable(true);
            builder.setPositiveButton("Yes", (dialog, which) -> {
                ScaledRecipe recipe = (ScaledRecipe) dayRecipeListAdapter.getItem(i);
                dayRecipeListAdapter.remove(recipe);
                dayRecipeListAdapter.notifyDataSetChanged();
            });
            builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
            return true;
        });

        // Add New Ingredient
        addIngredient.setOnClickListener(v -> {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.mealplan_add_ingredient, null);
            ListView ingredientListView = layout.findViewById(R.id.ingredient_list_meal);

            //shallow copy to prevent changes to original list
            // only list ingredients with different description, category, and price
            ArrayList<StoredIngredient> ingredients = new ArrayList<>(IngredientStorage.getInstance().getIngredientList());
            HashSet<Object> seen = new HashSet<>();
            ingredients.removeIf(c -> !seen.add(Arrays.asList(c.getDescription(), c.getCategory(), c.getUnit())));

            ingredientListView.setAdapter(new MealIngredientStorageAdapter(context,ingredients));
            AlertDialog.Builder alertbox = new MaterialAlertDialogBuilder(v.getRootView().getContext());
            alertbox.setView(layout);
            AlertDialog a = alertbox.create();
            a.show();
            Button addNewIngredient = layout.findViewById(R.id.addMealPlanIngredient);
            // inflate new fragment if button pressed
            addNewIngredient.setOnClickListener(view12 -> {
                new AddIngredientRecipeFragment(classAdapter, dayIngredientListAdapter).show(fm, "ADD_INGREDIENT_RECIPE");
                a.dismiss();
            });

            // input quantity on press
            ingredientListView.setOnItemClickListener((adapterView, view1, i, l) -> {
                Ingredient ingredient = (Ingredient) ingredientListView.getItemAtPosition(i);
                AlertDialog.Builder builder = new AlertDialog.Builder(view1.getRootView().getContext());
                builder.setTitle("How many do you need?(default 1)");
                // Set up the input
                final EditText input = new EditText(getContext());
                input.setHint("1");
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);
                // Set up the buttons
                builder.setPositiveButton("OK", (dialog, which) -> {
                    MealPlanDay newDay = days.get(position);
                    String countText = input.getText().toString();
                    int count = 1;
                    if(!"".equals(countText)) {
                        Integer.parseInt(countText);
                    }
                    Ingredient newIngredient = new Ingredient(ingredient.getDescription(), count, ingredient.getUnit(), ingredient.getCategory());
                    newDay.addIngredient(newIngredient);
                    notifyDataSetChanged();
                    a.dismiss();
                });
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
                builder.show();
            });
        });

        // Add new recipe
        Button addRecipe = view.findViewById(R.id.add_recipe_day);
        addRecipe.setOnClickListener(v -> {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.mealplan_add_recipe, null);
            ListView recipeListView = layout.findViewById(R.id.recipe_list_meal);
            recipeListView.setAdapter(RecipeStorage.getInstance().getRecipeAdapter());

            AlertDialog.Builder alertbox = new MaterialAlertDialogBuilder(v.getRootView().getContext());
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
                a.hide();
                fm.beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out).
                        replace(R.id.container, recipeChangeHandlerFragment, "EDIT_RECIPE").addToBackStack(null).commit();
            });

            // Add existing recipe
            recipeListView.setOnItemClickListener((adapterView, view13, i, l) -> {
                AlertDialog.Builder scaleAlertBox = new MaterialAlertDialogBuilder(view13.getRootView().getContext());
                Recipe recipe = (Recipe) recipeListView.getItemAtPosition(i);
                scaleAlertBox.setTitle("What is your desired scale?(default 1)");

                final EditText input = new EditText(view13.getRootView().getContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setHint("1");
                scaleAlertBox.setView(input);
                scaleAlertBox.setPositiveButton("OK", (dialog, which) -> {
                    String scaleTest = input.getText().toString();
                    int scale = 1;
                    if(!scaleTest.equals("")) {
                        scale = Integer.parseInt(scaleTest);
                    }

                    MealPlanDay newDay = days.get(position);
                    newDay.addRecipe(new ScaledRecipe(recipe, scale));

                    notifyDataSetChanged();
                    a.dismiss();
                });

                scaleAlertBox.setNegativeButton("Cancel", (dialog, which) -> {});
                scaleAlertBox.show();
            });

        });

        recipeListview.setOnItemClickListener((parent1, view14, position1, id) -> {
            ScaledRecipe scaledRecipe = days.get(position).getRecipeList().get(position1);

            AlertDialog.Builder scaleAlertBox = new MaterialAlertDialogBuilder(view14.getRootView().getContext());
            scaleAlertBox.setTitle("Change Scale");
            // setup input
            final EditText input = new EditText(view14.getRootView().getContext());
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            input.setText(String.valueOf(scaledRecipe.getScale()));
            scaleAlertBox.setView(input);
            scaleAlertBox.setPositiveButton("OK", (dialog, which) -> {
                int scale;
                try {
                    scale = Integer.parseInt(String.valueOf(input.getText()));
                } catch (NumberFormatException e) {
                    scale = 1;
                }

                scaledRecipe.setScale(scale);
                days.get(position).updateRecipe(scaledRecipe, position1);
                notifyDataSetChanged();
            });

            scaleAlertBox.setNegativeButton("Cancel", (dialog, which) -> {
            });

            scaleAlertBox.show();
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

    /**
     * Adds new ingredient to day
     *
     * @param newIngredient            object to add
     * @param dayIngredientListAdapter adapter for ingredients list
     */
    @Override
    public void onMealOkPressed(Ingredient newIngredient, DayIngredientListAdapter dayIngredientListAdapter) {
        dayIngredientListAdapter.add(newIngredient);
        dayIngredientListAdapter.notifyDataSetChanged();
    }

    /**
     * Updates ingredient in a day
     *
     * @param newIngredient            to add to ingredient day list
     * @param index                    of the ingredient in the list
     * @param position                 of the day in the day list
     * @param dayIngredientListAdapter adapter for ingredients list
     */
    @Override
    public void onMealItemPressed(Ingredient newIngredient, int index, int position, DayIngredientListAdapter dayIngredientListAdapter) {
        days.get(position).getIngredientList().set(index, newIngredient);
        dayIngredientListAdapter.notifyDataSetChanged();
    }
}
