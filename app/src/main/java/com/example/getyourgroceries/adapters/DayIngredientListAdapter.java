package com.example.getyourgroceries.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.getyourgroceries.R;
import com.example.getyourgroceries.entity.Ingredient;
import com.example.getyourgroceries.entity.MealPlanDay;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DayIngredientListAdapter extends ArrayAdapter<Ingredient> {
    private final ArrayList<Ingredient> ingredients;
    private final Context context;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    /**
     * Class constructor.
     * @param context Context of the app.
     * @param ingredients List of meal plans.
     */
    public DayIngredientListAdapter(Context context, ArrayList<Ingredient> ingredients) {
        super(context, 0, ingredients);
        this.ingredients = ingredients;
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
            view = LayoutInflater.from(context).inflate(R.layout.recipe_ingredient_item, parent, false);
        }
        Ingredient ingredient = ingredients.get(position);
        ((TextView)view.findViewById(R.id.recipe_ingredient_name)).setText(ingredient.getDescription());
        ((TextView)view.findViewById(R.id.recipe_ingredient_qty)).setText("Quantity: "+ ingredient.getAmount());
        ((TextView)view.findViewById(R.id.recipe_ingredient_category)).setText("Category: "+ ingredient.getCategory());
        ((TextView)view.findViewById(R.id.recipe_ingredient_unit)).setText("Unit Cost: $"+ df.format(ingredient.getUnit()));

        return view;
    }
}
