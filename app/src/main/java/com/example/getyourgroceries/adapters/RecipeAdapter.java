/* RecipeAdapter class. */

// Import statements.
package com.example.getyourgroceries;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.getyourgroceries.entity.Recipe;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Create an object to handle showing recipes in a list.
 */
public class RecipeAdapter extends ArrayAdapter<Recipe> {

    // Attributes.
    private final ArrayList<Recipe> recipes;
    private final Context context;
    private StorageReference imageRef;
    private static final String TAG = "RecipeList";

    /**
     * Class constructor.
     * @param context Context of the app.
     * @param recipes List of recipes.
     */
    public RecipeAdapter(Context context, ArrayList<Recipe> recipes) {
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
        TextView recipeName = view.findViewById(R.id.recipe_name);
        TextView recipePrepTime = view.findViewById(R.id.recipe_prep_time);
        TextView recipeCategory = view.findViewById(R.id.recipe_category);
        ImageView recipePhoto = view.findViewById(R.id.recipe_photo);
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
