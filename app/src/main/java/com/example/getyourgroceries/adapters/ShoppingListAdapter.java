/* IngredientStorageAdapter class. */
package com.example.getyourgroceries.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.example.getyourgroceries.R;
import com.example.getyourgroceries.entity.Ingredient;
import com.example.getyourgroceries.fragments.CollectIngredientFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Create an object to represent the list of ingredients.
 */
public class ShoppingListAdapter extends ArrayAdapter<Ingredient> {
    private final ArrayList<Ingredient> ingredients;
    private final Context context;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private FragmentManager fm;

    /**
     * Constructor
     *
     * @param context     of the fragment
     * @param ingredients list of ingredients
     * @param fm          fragment manager
     */
    public ShoppingListAdapter(Context context, ArrayList<Ingredient> ingredients, FragmentManager fm) {
        super(context, 0, ingredients);
        this.ingredients = ingredients;
        this.context = context;
        this.fm = fm;
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
            view = LayoutInflater.from(context).inflate(R.layout.shopping_item, parent, false);
        }

        // Show the ingredient.
        Ingredient ingredient = ingredients.get(position);

        // set textviews to proper values
        ((TextView) view.findViewById(R.id.shoppingItemTitle)).setText(ingredient.getDescription());
        ((TextView) view.findViewById(R.id.shoppingItemAmount)).setText(String.valueOf(ingredient.getAmount()));
        ((TextView) view.findViewById(R.id.shoppingItemCategory)).setText(ingredient.getCategory());
        ((TextView) view.findViewById(R.id.shoppingItemPrice)).setText(df.format(ingredient.getUnit()));

        Button collectBtn = view.findViewById(R.id.collect_ingredient);
        collectBtn.setOnClickListener(v -> {
            new CollectIngredientFragment(ingredient).show(fm, "COLLECT_INGREDIENT");
        });

        return view;
    }
}