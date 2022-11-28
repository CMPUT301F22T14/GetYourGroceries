/* RecipeListFragment class. */
package com.example.getyourgroceries.fragments;

import androidx.appcompat.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.getyourgroceries.R;

import com.example.getyourgroceries.entity.RecipeStorage;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.materialswitch.MaterialSwitch;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.getyourgroceries.entity.Recipe;

import java.util.Objects;


/**
 * Create an object to show the recipe list.
 */
public class RecipeListFragment extends Fragment {
    ListView recipeList;
    Button addRecipeButton;
    Spinner sortDropDown;
    MaterialSwitch sortingSwitch;

    /**
     * Empty constructor.
     */
    public RecipeListFragment() {}

    /**
     * Create the view.
     *
     * @param inflater            Object to connect an XML file.
     * @param container          The parent view.
     * @param savedInstanceState The saved state.
     * @return The created view.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("Recipe List");
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        setHasOptionsMenu(false);
        View v = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        recipeList = v.findViewById(R.id.recipe_list);

        recipeList.setAdapter(RecipeStorage.getInstance().getRecipeAdapter());

        // Listener to view a recipe
        recipeList.setOnItemClickListener((parent, view, position, id) -> {
            Bundle bundle = new Bundle();
            bundle.putInt("viewRecipe", position);
            RecipeViewFragment RecipeViewFragment = new RecipeViewFragment();
            RecipeViewFragment.setArguments(bundle);
            requireActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out).replace(container.getId(), RecipeViewFragment).addToBackStack(null).commit();
        });

        // Listener to delete a recipe.
        recipeList.setOnItemLongClickListener((adapterView, view, i, l) -> {
            AlertDialog.Builder builder = new MaterialAlertDialogBuilder(getContext());
            builder.setMessage("Would you like to delete this recipe?");
            builder.setTitle("Delete Recipe");
            builder.setCancelable(true);
            builder.setPositiveButton("Yes", (dialog, which) -> {
                Recipe recipe = (Recipe) recipeList.getItemAtPosition(i);
                RecipeStorage.getInstance().deleteRecipe(recipe, true);
            });
            builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
            return true;
        });
        sortingSwitch = v.findViewById(R.id.sorting_switch_recipe);

        // Listener to go to add recipe fragment
        addRecipeButton = v.findViewById(R.id.addRecipeButton);
        addRecipeButton.setOnClickListener(v1 -> {
            RecipeChangeHandlerFragment recipeChangeHandlerFragment = new RecipeChangeHandlerFragment();
            requireActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out).replace(container.getId(), recipeChangeHandlerFragment, "EDIT_RECIPE").addToBackStack("EDIT_RECIPE").commit();
        });

        Context context = this.getContext();
        sortDropDown = v.findViewById(R.id.sort_recipe_spinner);
        ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource(context, R.array.recipeSortBy, R.layout.ingredient_spinner_selected);
        sortAdapter.setDropDownViewResource(R.layout.ingredient_spinner_dropdown);
        sortDropDown.setAdapter(sortAdapter);

        // Sorting
        sortDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                boolean desc = sortingSwitch.isChecked();
                RecipeStorage.getInstance().sortCategory(position, desc);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                RecipeStorage.getInstance().sortCategory(0, false);
            }
        });

        // Sort recipe list
        sortingSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String type = sortDropDown.getSelectedItem().toString();
            switch (type) {
                case "Name":
                    RecipeStorage.getInstance().sortCategory(0, isChecked);
                    break;
                case "Prep Time":
                    RecipeStorage.getInstance().sortCategory(1, isChecked);
                    break;
                case "Serving Count":
                    RecipeStorage.getInstance().sortCategory(2, isChecked);
                    break;
                case "Category Type":
                    RecipeStorage.getInstance().sortCategory(3, isChecked);
                    break;
            }
        });

        return v;
    }
}