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
import com.example.getyourgroceries.entity.StoredIngredient;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Create an object to represent the list of ingredients.
 */
public class MealIngredientStorageAdapter extends ArrayAdapter<StoredIngredient> {
    private final ArrayList<StoredIngredient> ingredients;
    private final Context context;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    /**
     * Constructor
     *
     * @param context     for the adapter
     * @param ingredients list of ingredients for the adapter
     */
    public MealIngredientStorageAdapter(Context context, ArrayList<StoredIngredient> ingredients) {
        super(context, 0, ingredients);
        this.ingredients = ingredients;
        this.context = context;
    }

    /**
     * Update the view.
     *
     * @param position    Position of the ingredient.
     * @param convertView The view to convert.
     * @param parent      The parent view.
     * @return The updated view.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Create the view if it doesn't exist.
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.ingredient_item_meal, parent, false);
        }

        // Show the ingredient.
        StoredIngredient ingredient = ingredients.get(position);
        ((TextView) view.findViewById(R.id.ingredient_name)).setText(ingredient.getDescription());
        ((TextView)view.findViewById(R.id.ingredient_unit)).setText(df.format(ingredient.getUnit()));
        ((TextView)view.findViewById(R.id.ingredient_category)).setText(ingredient.getCategory());

        return view;
    }
}
