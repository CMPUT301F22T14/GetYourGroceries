package com.example.getyourgroceries.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.getyourgroceries.IngredientStorageAdapter;
import com.example.getyourgroceries.R;
import com.example.getyourgroceries.adapters.RecipeIngredientAdapter;
import com.example.getyourgroceries.control.RecipeDB;
import com.example.getyourgroceries.entity.Ingredient;
import com.example.getyourgroceries.entity.IngredientStorage;
import com.example.getyourgroceries.entity.Recipe;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class RecipeChangeHandlerFragment extends Fragment implements AddIngredientRecipeFragment.OnFragmentInteractionListener {
    private Recipe editRecipe;
    private ArrayList<Ingredient> ingredientList;
    private RecipeIngredientAdapter ingredientAdapter;
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
     * @param inflater loads the fragment
     * @param container underlying fragment container
     * @param savedInstanceState information passed in
     * @return view to display
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_recipe_change_handler, container, false);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        return view;
    }

    /**
     * The onViewCreated method will be called once the view has been created.
     * @param view The created view.
     * @param savedInstanceState The saved state.
     */
    @SuppressLint("SimpleDateFormat")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Initialization.
        ConstraintLayout addIngredientLayout = requireActivity().findViewById(R.id.change_recipe_layout);
        addIngredientLayout.setImportantForAutofill(View.IMPORTANT_FOR_AUTOFILL_NO);
        ingredientAdapter = new RecipeIngredientAdapter(requireActivity().getBaseContext(), ingredientList);
        FragmentManager fmManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fmManager.beginTransaction();

        if (getArguments() != null) {
            editRecipe = (Recipe) getArguments().getSerializable("editRecipe");
        }

        // Set up category spinner.
        Spinner category = view.findViewById(R.id.change_recipe_category);
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Enter A Category");
        categories.add("Baking");
        categories.add("Frying");
        categories.add("Microwaving");
        categories.add("Cooking");
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner, categories) {
            /**
             * The isEnabled method will disallow the first dropdown choice.
             * @param position The selected choice.
             * @return True if the position should be disallowed.
             */
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            /**
             * The getDropDownView will change the text colours of the choices appropriately.
             * @param position The selected choice.
             * @param convertView The old view to reuse.
             * @param parent The parent view.
             * @return The new view.
             */
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                if (position == 0) {
                    textView.setTextColor(Color.GRAY);
                } else {
                    textView.setTextColor(Color.WHITE);
                }
                return view;
            }
        };
        category.setAdapter(categoryAdapter);

        //Populate fields if its an edit
        TextView nameText = view.findViewById(R.id.change_recipe_name);
        TextView prepTimeText = view.findViewById(R.id.change_recipe_prep_time);
        TextView servingsText = view.findViewById(R.id.change_recipe_servings);
        TextView commentsText = view.findViewById(R.id.change_recipe_comments);
        ListView ingredientListView = view.findViewById(R.id.add_ingredients_recipe);
        ingredientListView.setAdapter(ingredientAdapter);

        // Set the values to the previous values.
        if (editRecipe != null){
            nameText.setText(editRecipe.getName());
            prepTimeText.setText(String.valueOf(editRecipe.getPrepTime()));
            servingsText.setText(String.valueOf(editRecipe.getNumOfServings()));
            category.setSelection(categoryAdapter.getPosition(editRecipe.getRecipeCategory()));
            commentsText.setText(editRecipe.getComment());
            ingredientList.addAll(editRecipe.getIngredientList());
        }

        // Get the text layouts.
        TextInputLayout tilName = view.findViewById(R.id.change_recipe_name_til);
        TextInputLayout tilPrepTime = view.findViewById(R.id.change_recipe_prep_time_til);
        TextInputLayout tilServings = view.findViewById(R.id.change_recipe_servings_til);
        TextInputLayout tilCategory = view.findViewById(R.id.change_recipe_category_til);
        TextInputLayout tilComments = view.findViewById(R.id.change_recipe_comments_til);

        Button addIngredientBtn = view.findViewById(R.id.add_ingredient_btn);
        addIngredientBtn.setOnClickListener(view12 -> new AddIngredientRecipeFragment().show(getActivity().getSupportFragmentManager(), "ADD_INGREDIENT_RECIPE"));

        // Add the recipe.
        Button confirmButton = view.findViewById(R.id.change_recipe_confirm);
        confirmButton.setOnClickListener(view1 ->{
            // Get the data.
            String name = nameText.getText().toString();
            String prepTime = prepTimeText.getText().toString();
            String servings = servingsText.getText().toString();
            String categoryText = category.getSelectedItem().toString();
            String comments = commentsText.getText().toString();

            // Error checking.
            int error = 0;
            if (name.equals("")) {
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

            Recipe newRecipe = new Recipe(name, Integer.parseInt(prepTime), Integer.parseInt(servings), categoryText, comments, "recipes/apple.jpg", ingredientList);

            // If in edit mode, update the attributes.
            if (editRecipe != null){
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
     * @param item The item selected.
     * @return On success, true.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return requireActivity().getSupportFragmentManager().popBackStackImmediate();
    }

    /**
     * Executes when the user hits "ok" on the add ingredient dialog
     * @param newIngredient item to add to recipe
     */
    @Override
    public void onOkPressed(Ingredient newIngredient) {
        if(!ingredientList.contains(newIngredient)) {
            ingredientList.add(newIngredient);
            ingredientAdapter.notifyDataSetChanged();
        }
    }
}