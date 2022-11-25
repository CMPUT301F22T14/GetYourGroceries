package com.example.getyourgroceries.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.example.getyourgroceries.MainActivity;
import com.example.getyourgroceries.R;
import com.example.getyourgroceries.adapters.DayIngredientListAdapter;
import com.example.getyourgroceries.adapters.DayListAdapter;
import com.example.getyourgroceries.entity.Ingredient;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AddIngredientRecipeFragment extends DialogFragment {
    private OnFragmentInteractionListener listener;
    private Ingredient ingredient;
    private EditText description;
    private AutoCompleteTextView category;
    private EditText amount;
    private EditText unit;
    private int index;
    private DayListAdapter dayListAdapter;
    private DayIngredientListAdapter dayIngredientListAdapter;

    public interface OnFragmentInteractionListener {
        void onOkPressed(Ingredient newIngredient);
        void onItemPressed(Ingredient newIngredient, int index);
        void onMealOkPressed(Ingredient newIngredient,DayIngredientListAdapter dayIngredientListAdapter);
    }

    AddIngredientRecipeFragment(Ingredient ingredient, int index) {
        this.ingredient = ingredient;
        this.index = index;
    }

    public AddIngredientRecipeFragment(){
    }

    public AddIngredientRecipeFragment(DayListAdapter dayListAdapter, DayIngredientListAdapter dayIngredientListAdapter) {
        this.dayListAdapter = dayListAdapter;
        this.dayIngredientListAdapter = dayIngredientListAdapter;
    }
    
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity act = (MainActivity) context;
        if (dayListAdapter != null){
            listener = dayListAdapter;
        }
        else{
            listener = (OnFragmentInteractionListener) act.getSupportFragmentManager().findFragmentByTag("EDIT_RECIPE");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_ingredient_recipe, null);
        description = view.findViewById(R.id.change_ingredient_description);
        amount = view.findViewById(R.id.change_ingredient_quantity);
        unit = view.findViewById(R.id.change_ingredient_unit);
        category = view.findViewById(R.id.change_ingredient_category);

        // create category categories
        Map<String, String> categoryIDs = new HashMap<>();
        ArrayList<String> categories = new ArrayList<>();
        categories.add("+ Save New Category");
        categories.add("- Delete Saved Category");
        CollectionReference categoryCollection = FirebaseFirestore.getInstance().collection("IngredientCategory");
        categoryCollection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    categories.add(Objects.requireNonNull(document.getData().get("Category")).toString());
                    categoryIDs.put(Objects.requireNonNull(document.getData().get("Category")).toString(), document.getId());
                }
            }
        });
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner, categories);
        if (ingredient != null) {
            category.setText(ingredient.getCategory());
        }
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
                        .setPositiveButton("OK", (dialog, which) -> {
                            String newCategory = newCategoryInput.getText().toString();
                            category.setText(newCategory);
                            Map<String, Object> insertCategory = new HashMap<>();
                            insertCategory.put("Category", newCategory);
                            categoryCollection.document().set(insertCategory);
                            dialog.dismiss();
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
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
                        .setPositiveButton("OK", (dialog, which) -> {
                            String deleteCategory = deleteCategoryInput.getText().toString();
                            String id = categoryIDs.get(deleteCategory);
                            if (id != null) {
                                categoryCollection.document(id).delete();
                            }
                            dialog.dismiss();
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
                        .create()
                        .show();
            }
        }));

        if(ingredient != null) {
            description.setText(ingredient.getDescription());
            amount.setText(ingredient.getAmount().toString());
            unit.setText(ingredient.getUnit().toString());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        if(ingredient != null) {
            builder = builder.setTitle("Edit Ingredient");
        } else {
            builder = builder.setTitle("Add Ingredient");
        }

        /* Let the user add an ingredient. */
        TextInputLayout
                descriptionTIL = view.findViewById(R.id.change_ingredient_description_til),
                quantityTIL = view.findViewById(R.id.change_ingredient_quantity_til),
                categoryTIL = view.findViewById(R.id.change_ingredient_category_til),
                unitTIL = view.findViewById(R.id.change_ingredient_unit_til);
        final AlertDialog dialog = builder
            .setView(view)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("OK", null).create();
        dialog.setOnShowListener(dialogInterface -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(view1 -> {
                String ingDescription = description.getText().toString();
                String ingAmount = amount.getText().toString();
                String ingUnit = unit.getText().toString();
                String ingCategory = category.getText().toString();

                /* Error checking. */
                int error = 0;
                if (ingDescription.equals("")) {
                    descriptionTIL.setError("Description cannot be empty!");
                    error = 1;
                } else {
                    descriptionTIL.setErrorEnabled(false);
                }
                if (ingCategory.equals("")) {
                    categoryTIL.setError("Category cannot be empty!");
                    error = 1;
                } else {
                    descriptionTIL.setErrorEnabled(false);
                }
                if (ingAmount.equals("")) {
                    quantityTIL.setError("Amount cannot be empty!");
                    error = 1;
                } else {
                    descriptionTIL.setErrorEnabled(false);
                }
                if (ingUnit.equals("")) {
                    unitTIL.setError("Unit price cannot be empty!");
                    error = 1;
                } else {
                    descriptionTIL.setErrorEnabled(false);
                }
                if (error == 0) {
                    int ingAmount2 = Integer.parseInt(ingAmount);
                    Double ingUnit2 = Double.parseDouble(ingUnit);
                    if (ingredient != null) {
                        listener.onItemPressed(new Ingredient(ingDescription, ingAmount2, ingUnit2, ingCategory), index);
                    }
                    else if (dayIngredientListAdapter != null){
                        listener.onMealOkPressed(new Ingredient(ingDescription, ingAmount2, ingUnit2, ingCategory),dayIngredientListAdapter);
                    }
                    else {
                        listener.onOkPressed(new Ingredient(ingDescription, ingAmount2, ingUnit2, ingCategory));
                    }
                    dialog.dismiss();
                }
            });
        });
        return dialog;
    }
}
