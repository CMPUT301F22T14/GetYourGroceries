package com.example.getyourgroceries.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.getyourgroceries.R;
import com.example.getyourgroceries.entity.MealPlanDay;
import com.example.getyourgroceries.entity.Recipe;

import java.util.ArrayList;

public class DayRecipeListAdapter extends ArrayAdapter<Recipe> {
    private final ArrayList<Recipe> recipes;
    private final Context context;

    /**
     * Class constructor.
     * @param context Context of the app.
     * @param days List of meal plans.
     */
    public DayRecipeListAdapter(Context context, ArrayList<Recipe> recipes) {
        super(context, 0, recipes);
        this.recipes = recipes;
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

        // Create the view if it doesn't exist.
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.recipe_item, parent, false);
        }

        // Add the recipe.
        Recipe recipe = recipes.get(position);
        TextView recipeName = view.findViewById(R.id.mealPlan_name);
        TextView recipePrepTime = view.findViewById(R.id.mealPlan_next);
        TextView recipeCategory = view.findViewById(R.id.recipe_category);
        int prep_hours = recipe.getPrepTime() / 60;
        int prep_min = recipe.getPrepTime() % 60;
        String prepTimeText = "Prep Time: " + prep_hours + "h " + prep_min + "m";
        String categoryText = "Category: " + recipe.getRecipeCategory();
        recipeName.setText(recipe.getName());
        recipePrepTime.setText(prepTimeText);
        recipeCategory.setText(categoryText);

        return view;
    }
}
