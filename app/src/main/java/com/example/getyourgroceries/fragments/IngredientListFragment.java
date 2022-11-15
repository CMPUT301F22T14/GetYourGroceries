/* IngredientListFragment class. */
package com.example.getyourgroceries.fragments;

import android.content.Context;
// Import statements.
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import com.example.getyourgroceries.IngredientStorageAdapter;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.getyourgroceries.IngredientStorageAdapter;
import com.example.getyourgroceries.R;
import com.example.getyourgroceries.control.IngredientDB;
import com.example.getyourgroceries.entity.Ingredient;
import com.example.getyourgroceries.entity.IngredientStorage;
import com.example.getyourgroceries.entity.StoredIngredient;

import java.util.Comparator;
import java.util.Objects;

/**
 * Create an object to represent the ingredient storage.
 * Extends {@link Fragment}.
 */
public class IngredientListFragment extends Fragment {
    Spinner sortDropDown;
    SwitchCompat sorting_switch;
    // Attributes.
    ListView ingredientListView;

    /**
     * Empty constructor.
     */
    public IngredientListFragment(){}

    /**
     * Create the view.
     * @param inflater Inflater to set an XML file.
     * @param container Containing view.
     * @param savedInstanceState The saved state.
     * @return The created view.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        View v = inflater.inflate(R.layout.fragment_ingredient_list, container, false);
        Button addIngredientButton = v.findViewById(R.id.addIngredientButton);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        sorting_switch = v.findViewById(R.id.sortingSwitchIngredient);
        // Button listener to add an ingredient.
        addIngredientButton.setOnClickListener(view -> {
            assert container != null;
            IngredientChangeHandlerFragment ingredientChangeHandlerFragment = new IngredientChangeHandlerFragment();
            requireActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out).replace(container.getId(), ingredientChangeHandlerFragment).addToBackStack(null).commit();
        });

        // Output all of the ingredients from Firebase.
        //IngredientStorage.ingredientAdapter = new IngredientStorageAdapter(requireActivity().getBaseContext(), IngredientStorage.ingredientStorage);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Would you like to delete this ingredient?");
            builder.setTitle("Delete Ingredient");
            builder.setCancelable(true);
            builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
               StoredIngredient ingredient = (StoredIngredient) ingredientListView.getItemAtPosition(i);
               IngredientStorage.getInstance().deleteIngredient(ingredient, true);
            });
            builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
            return true;
        });

        // Return the updated view.
        ingredientListView.setAdapter(IngredientStorage.getInstance().setupStorage(requireActivity().getBaseContext()));
        Context context = this.getContext();
        sortDropDown = v.findViewById(R.id.sortIngredientSpinner);
        ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource(context,R.array.ingredientSortBy,R.layout.ingredient_spinner_selected);
        sortAdapter.setDropDownViewResource(R.layout.ingredient_spinner_dropdown);
        sortDropDown.setAdapter(sortAdapter);

        //Sorting
        sortDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                boolean desc = sorting_switch.isChecked();
                IngredientStorage.getInstance().sortByCategory(position, desc);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sorting_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String type = sortDropDown.getSelectedItem().toString();
                if (type.equals("Description")){
                    IngredientStorage.getInstance().sortByCategory(0,isChecked);

                }
                else if (type.equals("Date")){
                    IngredientStorage.getInstance().sortByCategory(1,isChecked);

                }
                else if (type.equals("Location")){
                    IngredientStorage.getInstance().sortByCategory(2,isChecked);

                }
                else if (type.equals("Category")){
                    IngredientStorage.getInstance().sortByCategory(3,isChecked);
                }
            }
        });


        return v;
    }
    
}