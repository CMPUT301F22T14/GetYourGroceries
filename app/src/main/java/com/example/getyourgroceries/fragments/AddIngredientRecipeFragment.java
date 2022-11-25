package com.example.getyourgroceries.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.getyourgroceries.MainActivity;
import com.example.getyourgroceries.R;
import com.example.getyourgroceries.entity.Ingredient;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class AddIngredientRecipeFragment extends DialogFragment {
    private OnFragmentInteractionListener listener;
    private Ingredient ingredient;
    private EditText description;
    private AutoCompleteTextView category;
    private EditText amount;
    private EditText unit;
    private int index;

    public interface OnFragmentInteractionListener {
        void onOkPressed(Ingredient newIngredient);
        void onItemPressed(Ingredient newIngredient, int index);
    }

    AddIngredientRecipeFragment(Ingredient ingredient, int index) {
        this.ingredient = ingredient;
        this.index = index;
    }

    public AddIngredientRecipeFragment() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity act = (MainActivity) context;

        RecipeChangeHandlerFragment frag = (RecipeChangeHandlerFragment) act.getSupportFragmentManager().findFragmentByTag("EDIT_RECIPE");
        listener = (OnFragmentInteractionListener)frag;
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
        ArrayList<String> categories = new ArrayList<>();
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

        if(ingredient != null) {
            description.setText(ingredient.getDescription());
            amount.setText(ingredient.getAmount().toString());
            unit.setText(ingredient.getUnit().toString());
            category.setText(ingredient.getCategory());
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
            Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
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
                    } else {
                        listener.onOkPressed(new Ingredient(ingDescription, ingAmount2, ingUnit2, ingCategory));
                    }
                    dialog.dismiss();
                }
            });
        });
        return dialog;
    }
}
