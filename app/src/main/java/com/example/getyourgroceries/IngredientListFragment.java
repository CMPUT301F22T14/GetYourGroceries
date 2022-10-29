package com.example.getyourgroceries;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.getyourgroceries.control.IngredientDB;
import com.example.getyourgroceries.entity.Ingredient;
import com.example.getyourgroceries.entity.IngredientStorage;

import java.util.Objects;

public class IngredientListFragment extends Fragment {

    ListView ingredientListView;

    public IngredientListFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ingredient_list, container, false);
        Button addIngredientButton = v.findViewById(R.id.addIngredientButton);
        IngredientDB db = new IngredientDB();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(false);

        
        // Button listener.
        addIngredientButton.setOnClickListener(view -> {
            assert container != null;
            IngredientChangeHandlerFragment ingredientChangeHandlerFragment = new IngredientChangeHandlerFragment();
            requireActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out).replace(container.getId(), ingredientChangeHandlerFragment).addToBackStack(null).commit();
        });

        //requireActivity().getFragmentManager().addOnBackStackChangedListener();
        // Output all of the ingredients from Firebase.

        IngredientStorage.ingredientAdapter = new IngredientStorageAdapter(getActivity().getBaseContext(), IngredientStorage.ingredientStorage);
        ingredientListView = v.findViewById(R.id.ingredientListView);

        ingredientListView.setOnItemClickListener((adapterView, view, i, l) -> {
            Bundle bundle = new Bundle();
            bundle.putInt("editIngredient", i);
            //pass item position in extra so new activity can find food item
            IngredientChangeHandlerFragment ingredientChangeHandlerFragment = new IngredientChangeHandlerFragment();
            ingredientChangeHandlerFragment.setArguments(bundle);
            requireActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out).replace(container.getId(), ingredientChangeHandlerFragment).addToBackStack(null).commit();
        });

        ingredientListView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Would you like to delete this ingredient?");
            builder.setTitle("Delete Ingredient");
            builder.setCancelable(true);
            builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
               Ingredient ingredient = (Ingredient) ingredientListView.getItemAtPosition(i);
               db.deleteIngredient(ingredient.getId());
            });
            builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                dialog.cancel();
            });
            AlertDialog alert = builder.create();
            alert.show();
            return true;
        });

        ingredientListView.setAdapter(IngredientStorage.ingredientAdapter);
        return v;
    }
}