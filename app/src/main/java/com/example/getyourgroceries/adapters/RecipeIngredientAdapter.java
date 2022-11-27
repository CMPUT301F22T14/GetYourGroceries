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

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Create an object to display recipe ingredients.
 */
public class RecipeIngredientAdapter extends ArrayAdapter<Ingredient> {
    private final ArrayList<Ingredient> ingredients;
    private final Context context;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public RecipeIngredientAdapter(Context context, ArrayList<Ingredient> ingredients){
        super(context,0, ingredients);
        this.ingredients = ingredients;
        this.context = context;
    }

    /**
     * Update the view.
     * @param position Position of the ingredient.
     * @param convertView The view to convert.
     * @param parent The parent view.
     * @return The updated view.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Create the view if it doesn't exist.
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.recipe_ingredient_item, parent,false);
        }

        // Show the ingredient.
        Ingredient ingredient = ingredients.get(position);
        ((TextView)view.findViewById(R.id.recipe_ingredient_name)).setText(ingredient.getDescription());
        ((TextView)view.findViewById(R.id.recipe_ingredient_qty)).setText(String.valueOf(ingredient.getAmount()));
        ((TextView)view.findViewById(R.id.recipe_ingredient_category)).setText(ingredient.getCategory());
        ((TextView)view.findViewById(R.id.recipe_ingredient_unit)).setText("$"+ df.format(ingredient.getUnit()));
        return view;
    }
}
