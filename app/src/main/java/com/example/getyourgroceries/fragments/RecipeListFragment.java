/* RecipeListFragment class. */
package com.example.getyourgroceries.fragments;

// Import statements.
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.getyourgroceries.R;
import com.example.getyourgroceries.adapters.RecipeAdapter;
import com.example.getyourgroceries.control.RecipeDB;
import com.example.getyourgroceries.entity.Ingredient;
import com.example.getyourgroceries.entity.RecipeStorage;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.getyourgroceries.entity.Recipe;
import java.util.ArrayList;

/**
 * Create an object to show the recipe list.
 */
public class RecipeListFragment extends Fragment {
    private static final String TAG = "RecipeListFragment";
    ArrayList<Recipe> recipeDataList;
    RecipeAdapter recipeAdapter;
    FirebaseFirestore db;

    ListView recipeList;
    Button addRecipeButton;

    /**
     * Empty constructor.
     */
    public RecipeListFragment(){}

    /**
     * Create the view.
     * @param inflater Object to connect an XML file.
     * @param container The parent view.
     * @param savedInstanceState The saved state.
     * @return The created view.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        RecipeDB recipeDB = new RecipeDB();

        RecipeStorage.recipeAdapter = new RecipeAdapter(requireActivity().getBaseContext(), RecipeStorage.recipeStorage);
        recipeList = v.findViewById(R.id.recipe_list);
        recipeList.setAdapter(RecipeStorage.recipeAdapter);



        // Listener to edit a recipe
        recipeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();

                Recipe editRecipe = (Recipe) recipeList.getItemAtPosition(position);
                bundle.putSerializable("editRecipe", editRecipe);

                RecipeChangeHandlerFragment recipeChangeHandlerFragment = new RecipeChangeHandlerFragment();
                recipeChangeHandlerFragment.setArguments(bundle);
                requireActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out).replace(container.getId(), recipeChangeHandlerFragment).addToBackStack(null).commit();
            }
        });

        // Listener to delete a recipe.
        recipeList.setOnItemLongClickListener((adapterView, view, i, l)-> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Would you like to delete this recipe?");
            builder.setTitle("Delete Recipe");
            builder.setCancelable(true);
            builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                Recipe recipe = (Recipe) recipeList.getItemAtPosition(i);
                recipeDB.deleteRecipe(recipe);
            });
            builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
            return true;
        });

        addRecipeButton = v.findViewById(R.id.addRecipeButton);

        addRecipeButton.setOnClickListener(v1 -> {
            RecipeChangeHandlerFragment recipeChangeHandlerFragment = new RecipeChangeHandlerFragment();
            requireActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out).replace(container.getId(), recipeChangeHandlerFragment).addToBackStack(null).commit();
        });

        return v;
    }
}