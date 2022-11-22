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

import com.example.getyourgroceries.GlideApp;
import com.example.getyourgroceries.R;
import com.example.getyourgroceries.entity.MealPlanDay;
import com.example.getyourgroceries.entity.Recipe;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class DayRecipeListAdapter extends ArrayAdapter<Recipe> {
    private final ArrayList<Recipe> recipes;
    private StorageReference imageRef;
    private final Context context;
    FirebaseStorage storage;

    /**
     * Class constructor.
     * @param context Context of the app.
     * @param recipes List of meal plans.
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
        TextView recipeName = view.findViewById(R.id.recipe_title);
        TextView recipePrepTime = view.findViewById(R.id.recipe_prep_time);
        TextView recipeServings = view.findViewById(R.id.recipe_servings);
        TextView recipeCategory = view.findViewById(R.id.recipe_category);
        ImageView recipePhoto = view.findViewById(R.id.recipe_photo);

        int prep_hours = recipe.getPrepTime() / 60;
        int prep_min = recipe.getPrepTime() % 60;
        String prepTimeText = "Prep Time: " + prep_hours + "h " + prep_min + "m";
        String categoryText = "Category: " + recipe.getRecipeCategory();
        String servingsText = "Servings: " + recipe.getNumOfServings();
        recipeName.setText(recipe.getName());
        recipePrepTime.setText(prepTimeText);
        recipeCategory.setText(categoryText);
        recipeServings.setText(servingsText);

        // get photo
        storage = FirebaseStorage.getInstance();
        try {
            imageRef = storage.getReference().child(recipe.getPhoto());

            GlideApp.with(view)
                    .load(imageRef)
                    .override(300, 300)
                    .into(recipePhoto);
        } catch (IllegalArgumentException e) {
            recipePhoto.setImageResource(R.drawable.placeholder);
        }

        return view;
    }
}
