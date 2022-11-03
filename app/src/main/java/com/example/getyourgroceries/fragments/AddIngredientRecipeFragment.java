package com.example.getyourgroceries.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.getyourgroceries.MainActivity;
import com.example.getyourgroceries.R;
import com.example.getyourgroceries.entity.Ingredient;

import java.util.ArrayList;

public class AddIngredientRecipeFragment extends DialogFragment {
    private EditText description;
    private EditText amount;
    private EditText unit;
    private Spinner category;
    private OnFragmentInteractionListener listener;

    public interface OnFragmentInteractionListener {
        void onOkPressed(Ingredient newIngredient);
    }

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
        description = view.findViewById(R.id.add_recipe_description);
        amount = view.findViewById(R.id.add_recipe_quantity);
        unit = view.findViewById(R.id.add_recipe_unit);
        category = view.findViewById(R.id.add_recipe_category);

        // create category categories
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

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view)
                .setTitle("Add Ingredient")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    String ingDescription = description.getText().toString();
                    int ingAmount = Integer.parseInt(amount.getText().toString());
                    double ingUnit = Double.parseDouble(unit.getText().toString());
                    String ingCategory = category.getSelectedItem().toString();
                    listener.onOkPressed(new Ingredient(ingDescription, ingAmount, ingUnit, ingCategory));
                }).create();
    }
}
