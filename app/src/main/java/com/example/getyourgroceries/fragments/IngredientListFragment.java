/* IngredientListFragment class. */
package com.example.getyourgroceries.fragments;

import android.content.Context;
import androidx.appcompat.app.AlertDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.getyourgroceries.R;
import com.example.getyourgroceries.entity.IngredientStorage;
import com.example.getyourgroceries.entity.StoredIngredient;

import java.util.Objects;

/**
 * Create an object to represent the ingredient storage.
 * Extends {@link Fragment}.
 */
public class IngredientListFragment extends Fragment {
    Spinner sortDropDown;
    SwitchCompat sortingSwitch;
    ListView ingredientListView;

    /**
     * Empty constructor.
     */
    public IngredientListFragment() {}

    /**
     * Create the view.
     *
     * @param inflater           Inflater to set an XML file.
     * @param container          Containing view.
     * @param savedInstanceState The saved state.
     * @return The created view.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        IngredientStorage.getInstance().updateStorage();
        //set title
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("Ingredient Storage");

        // Inflate the layout for this fragment.
        View v = inflater.inflate(R.layout.fragment_ingredient_list, container, false);
        Button addIngredientButton = v.findViewById(R.id.addIngredientButton);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        sortingSwitch = v.findViewById(R.id.sortingSwitchIngredient);
        // Button listener to add an ingredient.
        addIngredientButton.setOnClickListener(view -> {
            assert container != null;
            IngredientChangeHandlerFragment ingredientChangeHandlerFragment = new IngredientChangeHandlerFragment();
            requireActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out).replace(container.getId(), ingredientChangeHandlerFragment).addToBackStack(null).commit();
        });

        // Output all of the ingredients from Firebase.
        ingredientListView = v.findViewById(R.id.ingredientListView);

        // Listener to edit an ingredient.
        ingredientListView.setOnItemClickListener((adapterView, view, i, l) -> {
            Bundle bundle = new Bundle();
            bundle.putInt("editIngredient", i);
            IngredientChangeHandlerFragment ingredientChangeHandlerFragment = new IngredientChangeHandlerFragment();
            ingredientChangeHandlerFragment.setArguments(bundle);
            assert container != null;
            requireActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out).replace(container.getId(), ingredientChangeHandlerFragment).addToBackStack(null).commit();
        });

        // Listener to delete an ingredient.
        ingredientListView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            AlertDialog.Builder builder = new MaterialAlertDialogBuilder(getContext());
            builder.setMessage("Would you like to delete this ingredient?");
            builder.setTitle("Delete Ingredient");
            builder.setCancelable(true);
            builder.setPositiveButton("Yes", (dialog, which) -> {
                StoredIngredient ingredient = (StoredIngredient) ingredientListView.getItemAtPosition(i);
                IngredientStorage.getInstance().deleteIngredient(ingredient, true);
            });
            builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
            return true;
        });

        // Return the updated view.
        ingredientListView.setAdapter(IngredientStorage.getInstance().getIngredientAdapter());
        Context context = this.getContext();
        sortDropDown = v.findViewById(R.id.sortIngredientSpinner);
        ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource(context, R.array.ingredientSortBy, R.layout.ingredient_spinner_selected);
        sortAdapter.setDropDownViewResource(R.layout.ingredient_spinner_dropdown);
        sortDropDown.setAdapter(sortAdapter);

        // Sorting
        sortDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // sort descending order
                boolean desc = sortingSwitch.isChecked();
                IngredientStorage.getInstance().sortByCategory(position, desc);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // sort ascending order
                IngredientStorage.getInstance().sortByCategory(0, false);
            }
        });

        // Sort Ingredient List
        sortingSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String type = sortDropDown.getSelectedItem().toString();
            switch (type) {
                case "Description":
                    IngredientStorage.getInstance().sortByCategory(0, isChecked);
                    break;
                case "Date":
                    IngredientStorage.getInstance().sortByCategory(1, isChecked);
                    break;
                case "Location":
                    IngredientStorage.getInstance().sortByCategory(2, isChecked);
                    break;
                case "Category":
                    IngredientStorage.getInstance().sortByCategory(3, isChecked);
                    break;
            }
        });
        return v;
    }
}