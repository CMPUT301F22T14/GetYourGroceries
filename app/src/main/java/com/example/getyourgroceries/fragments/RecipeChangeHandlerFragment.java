package com.example.getyourgroceries.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

import com.example.getyourgroceries.R;
import com.example.getyourgroceries.adapters.RecipeIngredientAdapter;
import com.example.getyourgroceries.control.RecipeDB;
import com.example.getyourgroceries.entity.Ingredient;
import com.example.getyourgroceries.entity.Recipe;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class RecipeChangeHandlerFragment extends Fragment implements AddIngredientRecipeFragment.OnFragmentInteractionListener {
    private final ArrayList<Ingredient> ingredientList;
    private RecipeIngredientAdapter ingredientAdapter;
    private Recipe editRecipe;
    RecipeDB db;

    /**
     * Fragment constructor to initialize its database class
     */
    public RecipeChangeHandlerFragment() {
        super();
        db = new RecipeDB();
        ingredientList = new ArrayList<>();
    }

    /**
     * Display logic when fragment is loaded
     *
     * @param inflater           loads the fragment
     * @param container          underlying fragment container
     * @param savedInstanceState information passed in
     * @return view to display
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_change_handler, container, false);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        return view;
    }

    /**
     * The onViewCreated method will be called once the view has been created.
     *
     * @param view               The created view.
     * @param savedInstanceState The saved state.
     */
    @SuppressLint("SimpleDateFormat")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        NestedScrollView addIngredientLayout = requireActivity().findViewById(R.id.change_recipe_layout);
        addIngredientLayout.setImportantForAutofill(View.IMPORTANT_FOR_AUTOFILL_NO);
        ingredientAdapter = new RecipeIngredientAdapter(requireActivity().getBaseContext(), ingredientList);
        FragmentManager fmManager = getActivity().getSupportFragmentManager();

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();

        if (getArguments() != null) {
            editRecipe = (Recipe) getArguments().getSerializable("editRecipe");
            actionBar.setTitle("Edit Recipe");
        } else{
            actionBar.setTitle("Add Recipe");
        }

        // Set up category spinner.
        AutoCompleteTextView category = view.findViewById(R.id.change_recipe_category);
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Baking");
        categories.add("Frying");
        categories.add("Microwaving");
        categories.add("Cooking");
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner, categories);
        category.setAdapter(categoryAdapter);
        category.setThreshold(200);

        //Populate fields if its an edit
        TextInputEditText descriptionText = view.findViewById(R.id.change_recipe_description);
        TextInputEditText prepTimeText = view.findViewById(R.id.change_recipe_prep_time);
        TextInputEditText servingsText = view.findViewById(R.id.change_recipe_servings);
        TextInputEditText commentsText = view.findViewById(R.id.change_recipe_comments);
        ListView ingredientListView = view.findViewById(R.id.add_ingredients_recipe);
        ingredientListView.setAdapter(ingredientAdapter);

        ingredientListView.setOnItemClickListener((adapterView, view12, i, l) -> new AddIngredientRecipeFragment(ingredientList.get(i), i).show(getActivity().getSupportFragmentManager(), "EDIT_INGREDIENT_RECIPE"));

        ingredientListView.setOnItemLongClickListener((adapterView, view2, i, l) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Would you like to delete this ingredient?");
            builder.setTitle("Delete Ingredient");
            builder.setCancelable(true);
            builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                Ingredient recipe = (Ingredient) ingredientListView.getItemAtPosition(i);
                ingredientList.remove(recipe);
                ingredientAdapter.notifyDataSetChanged();
            });
            builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
            return true;
        });

        ViewCompat.setNestedScrollingEnabled(ingredientListView, true);

        // Set the values to the previous values.
        if (editRecipe != null) {
            descriptionText.setText(editRecipe.getName());
            prepTimeText.setText(String.valueOf(editRecipe.getPrepTime()));
            servingsText.setText(String.valueOf(editRecipe.getNumOfServings()));
            category.setText(editRecipe.getRecipeCategory());
            commentsText.setText(editRecipe.getComment());
            ingredientList.addAll(editRecipe.getIngredientList());
        }

        // Get the text layouts.
        TextInputLayout tilName = view.findViewById(R.id.change_recipe_description_til);
        TextInputLayout tilPrepTime = view.findViewById(R.id.change_recipe_prep_time_til);
        TextInputLayout tilServings = view.findViewById(R.id.change_recipe_servings_til);
        TextInputLayout tilCategory = view.findViewById(R.id.change_recipe_category_til);

        Button addIngredientBtn = view.findViewById(R.id.change_recipe_add_ingredient);
        addIngredientBtn.setOnClickListener(view12 -> new AddIngredientRecipeFragment().show(getActivity().getSupportFragmentManager(), "ADD_INGREDIENT_RECIPE"));

        // Add the recipe.
        Button confirmButton = view.findViewById(R.id.change_recipe_confirm);
        confirmButton.setOnClickListener(view1 -> {
            // Get the data.
            String description = descriptionText.getText().toString();
            String prepTime = prepTimeText.getText().toString();
            String servings = servingsText.getText().toString();
            String categoryText = category.getText().toString();
            String comments = commentsText.getText().toString();

            // Error checking.
            int error = 0;
            if (description.equals("")) {
                tilName.setError("Name cannot be empty!");
                error = 1;
            } else {
                tilName.setErrorEnabled(false);
            }
            if (prepTime.equals("")) {
                tilPrepTime.setError("Prep Time must be an integer!");
                error = 1;
            } else {
                tilPrepTime.setErrorEnabled(false);
            }
            if (servings.equals("")) {
                tilServings.setError("Servings must be a positive integer!");
                error = 1;
            } else {
                tilServings.setErrorEnabled(false);
            }
            if (categoryText.equals("Enter A Category")) {
                tilCategory.setError("Category cannot be empty!");
                error = 1;
            } else {
                tilCategory.setErrorEnabled(false);
            }
            if (error == 1) {
                return;
            }

            Recipe newRecipe = new Recipe(description, Integer.parseInt(prepTime), Integer.parseInt(servings), categoryText, comments, "recipes/apple.jpg", ingredientList);

            // If in edit mode, update the attributes.
            if (editRecipe != null) {
                newRecipe.setId(editRecipe.getId());
                db.updateRecipe(newRecipe);
            } else {
                db.addRecipe(newRecipe);
            }
            fmManager.popBackStack();
            fmManager.popBackStack();
        });
    }

    /**
     * The onOptionsItemSelected method will go to the previous fragment when the back button is pressed.
     *
     * @param item The item selected.
     * @return On success, true.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return requireActivity().getSupportFragmentManager().popBackStackImmediate();
    }

    /**
     * Executes when the user hits "ok" on the add ingredient dialog
     *
     * @param newIngredient item to add to recipe
     */
    @Override
    public void onOkPressed(Ingredient newIngredient) {
        if (!ingredientList.contains(newIngredient)) {
            ingredientList.add(newIngredient);
            ingredientAdapter.notifyDataSetChanged();
        }
    }

    /**
     * Executes when the user hits "ok" on the edit ingredient dialog
     * @param newIngredient updated ingredient info
     * @param index position in ingredient list
     */
    @Override
    public void onItemPressed(Ingredient newIngredient, int index) {
        ingredientList.set(index, newIngredient);
        ingredientAdapter.notifyDataSetChanged();
    }
}