/* IngredientListFragment class. */
package com.example.getyourgroceries;

import android.content.Context;
// Import statements.
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.getyourgroceries.control.IngredientDB;
import com.example.getyourgroceries.entity.Ingredient;
import com.example.getyourgroceries.entity.IngredientStorage;
import java.util.Objects;

/**
 * Create an object to represent the ingredient storage.
 * Extends {@link Fragment}.
 */
public class IngredientListFragment extends Fragment {
    Spinner sortDropDown;

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
        IngredientDB db = new IngredientDB();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        // Button listener to add an ingredient.
        addIngredientButton.setOnClickListener(view -> {
            assert container != null;
            IngredientChangeHandlerFragment ingredientChangeHandlerFragment = new IngredientChangeHandlerFragment();
            requireActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out).replace(container.getId(), ingredientChangeHandlerFragment).addToBackStack(null).commit();
        });

        // Output all of the ingredients from Firebase.
        IngredientStorage.ingredientAdapter = new IngredientStorageAdapter(requireActivity().getBaseContext(), IngredientStorage.ingredientStorage);
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
               Ingredient ingredient = (Ingredient) ingredientListView.getItemAtPosition(i);
               db.deleteIngredient(ingredient);
            });
            builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
            return true;
        });

        // Return the updated view.
        ingredientListView.setAdapter(IngredientStorage.ingredientAdapter);
//        IngredientDB db = new IngredientDB();
        /*for (int i = 0; i < ingredients.size(); i++) {
            getParentFragmentManager().beginTransaction().add(R.id.linearLayoutIngredients, IngredientFragment.newInstance(ingredients.get(i))).commit();
        }*/
        Context context = this.getContext();
        sortDropDown = v.findViewById(R.id.sortIngredientSpinner);
        ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource(context,R.array.ingredientSortBy,android.R.layout.simple_spinner_item);
        sortDropDown.setAdapter(sortAdapter);
        return v;
    }
}