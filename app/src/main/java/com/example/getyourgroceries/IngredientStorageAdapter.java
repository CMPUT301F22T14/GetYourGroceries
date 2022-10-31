/* IngredientStorageAdapter class. */
package com.example.getyourgroceries;

// Import statements.
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.getyourgroceries.entity.StoredIngredient;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Create an object to represent the list of ingredients.
 */
public class IngredientStorageAdapter extends ArrayAdapter<StoredIngredient> {

    // Attributes.
    private final ArrayList<StoredIngredient> ingredients;
    private final Context context;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    public IngredientStorageAdapter(Context context, ArrayList<StoredIngredient> ingredients){
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
            view = LayoutInflater.from(context).inflate(R.layout.ingredient_item, parent,false);
        }

        // Show the ingredient.
        StoredIngredient ingredient = ingredients.get(position);
        ((TextView)view.findViewById(R.id.ingredient_name)).setText(ingredient.getDescription());
        ((TextView)view.findViewById(R.id.ingredient_qty)).setText("Quantity: "+ ingredient.getAmount());
        ((TextView)view.findViewById(R.id.ingredient_bestbefore)).setText("BB: "+(new SimpleDateFormat("MM/dd/yyy")).format(ingredient.getBestBefore()));
        ((TextView)view.findViewById(R.id.ingredient_location)).setText("Location: "+ingredient.getLocation());
        ((TextView)view.findViewById(R.id.ingredient_category)).setText("Category: "+ ingredient.getCategory());
        ((TextView)view.findViewById(R.id.ingredient_unit)).setText("Unit Cost: $"+ df.format(ingredient.getUnit()));
        return view;
    }
}