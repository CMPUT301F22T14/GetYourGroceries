package com.example.getyourgroceries.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.getyourgroceries.R;
import com.example.getyourgroceries.entity.Ingredient;

public class AddIngredientRecipeFragment extends DialogFragment {
    private EditText description;
    private EditText amount;
    private OnFragmentInteractionListener listener;

    public interface OnFragmentInteractionListener {
        void onOkPressed(Ingredient newIngredient);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener)context;
        } else {
            throw new RuntimeException(context.toString() + "must implement OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_add_ingredient_recipe, null);
        description = view.findViewById(R.id.add_recipe_description);
        amount = view.findViewById(R.id.add_recipe_quantity);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view)
                .setTitle("Add City")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    String ingDescription = description.getText().toString();
                    int ingAmount = Integer.parseInt(amount.getText().toString());
                    listener.onOkPressed(new Ingredient(ingDescription, ingAmount, 0.99, "Fruit"));
                }).create();
    }
}
