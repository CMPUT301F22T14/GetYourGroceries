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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.getyourgroceries.R;
import com.example.getyourgroceries.adapters.RecipeIngredientAdapter;
import com.example.getyourgroceries.control.RecipeDB;
import com.example.getyourgroceries.entity.Ingredient;
import com.example.getyourgroceries.entity.Recipe;
import com.example.getyourgroceries.entity.RecipeStorage;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class RecipeChangeHandlerFragment extends Fragment implements AddIngredientRecipeFragment.OnFragmentInteractionListener {
    private ArrayList<Ingredient> ingredientList;
    private RecipeIngredientAdapter ingredientAdapter;
    private Recipe editRecipe;

    /**
     * Fragment constructor to initialize its database class
     */
    public RecipeChangeHandlerFragment() {
        super();
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

        FragmentManager fmManager = getActivity().getSupportFragmentManager();

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();

        if (getArguments() != null) {
            editRecipe = RecipeStorage.getInstance().getRecipe(getArguments().getInt("editRecipe"));
            actionBar.setTitle("Edit Recipe");
            ingredientList = editRecipe.getIngredientList();
        } else{
            actionBar.setTitle("Add Recipe");
        }
        ingredientAdapter = new RecipeIngredientAdapter(requireActivity().getBaseContext(), ingredientList);

        // Set up category spinner.
        Map<String, String> categoryIDs = new HashMap<>();
        ArrayList<String> categories = new ArrayList<>();
        categories.add("+ Save New Category");
        categories.add("- Delete Saved Category");
        CollectionReference categoryCollection = FirebaseFirestore.getInstance().collection("RecipeCategory");
        categoryCollection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    categories.add(Objects.requireNonNull(document.getData().get("Category")).toString());
                    categoryIDs.put(Objects.requireNonNull(document.getData().get("Category")).toString(), document.getId());
                }
            }
        });
        AutoCompleteTextView category = requireActivity().findViewById(R.id.change_recipe_category);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner, categories);
        category.setAdapter(categoryAdapter);
        category.setThreshold(200);
        category.setOnItemClickListener(((adapterView, view1, i, l) -> {
            if (Objects.equals(categories.get(i), "+ Save New Category")) {
                category.setText("");
                final EditText newCategoryInput = new EditText(getContext());
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder
                        .setTitle("Add Category")
                        .setMessage("Enter a new category:")
                        .setCancelable(true)
                        .setView(newCategoryInput)
                        .setPositiveButton("OK", (DialogInterface.OnClickListener) (dialog, which) -> {
                            String newCategory = newCategoryInput.getText().toString();
                            category.setText(newCategory);
                            Map<String, Object> insertCategory = new HashMap<>();
                            insertCategory.put("Category", newCategory);
                            categoryCollection.document().set(insertCategory);
                            dialog.dismiss();
                        })
                        .setNegativeButton("Cancel", (DialogInterface.OnClickListener) (dialog, which) -> {
                            dialog.cancel();
                        })
                        .create()
                        .show();
            } else if (categories.get(i).equals("- Delete Saved Category")) {
                category.setText("");
                final EditText deleteCategoryInput = new EditText(getContext());
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder
                        .setTitle("Delete Category")
                        .setMessage("Delete an existing category:")
                        .setCancelable(true)
                        .setView(deleteCategoryInput)
                        .setPositiveButton("OK", (DialogInterface.OnClickListener) (dialog, which) -> {
                            String deleteCategory = deleteCategoryInput.getText().toString();
                            String id = categoryIDs.get(deleteCategory);
                            if (id != null) {
                                categoryCollection.document(id).delete();
                            }
                            dialog.dismiss();
                        })
                        .setNegativeButton("Cancel", (DialogInterface.OnClickListener) (dialog, which) -> {
                            dialog.cancel();
                        })
                        .create()
                        .show();
            }
        }));

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

            // If in edit mode, update the attributes.
            if (editRecipe != null) {
                editRecipe.setName(description);
                editRecipe.setPrepTime(Integer.parseInt(prepTime));
                editRecipe.setNumOfServings(Integer.parseInt(servings));
                editRecipe.setRecipeCategory(categoryText);
                editRecipe.setComment(comments);
                editRecipe.setPhoto("recipes/apple.jpg");
                RecipeStorage.getInstance().updateRecipe(editRecipe);
            } else {
                Recipe newRecipe = new Recipe(description, Integer.parseInt(prepTime), Integer.parseInt(servings), categoryText, comments, "recipes/apple.jpg", ingredientList);
                RecipeStorage.getInstance().addRecipe(newRecipe, true);
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