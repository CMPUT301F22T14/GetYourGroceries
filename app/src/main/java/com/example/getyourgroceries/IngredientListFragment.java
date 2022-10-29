package com.example.getyourgroceries;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.getyourgroceries.control.IngredientDB;
import com.example.getyourgroceries.entity.IngredientStorage;
import com.example.getyourgroceries.entity.StoredIngredient;

import java.util.ArrayList;
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

        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(false);

        
        // Button listener.
        addIngredientButton.setOnClickListener(view -> {
            assert container != null;
            AddIngredientFragment addIngredientFragment = new AddIngredientFragment();
            requireActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out).replace(container.getId(), addIngredientFragment).addToBackStack(null).commit();
        });

        //requireActivity().getFragmentManager().addOnBackStackChangedListener();
        // Output all of the ingredients from Firebase.

        IngredientStorage.ingredientAdapter = new IngredientStorageAdapter(getActivity().getBaseContext(), IngredientStorage.ingredientStorage);
        ingredientListView = v.findViewById(R.id.ingredientListView);

        ingredientListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putInt("editIngredient", i);
                //pass item position in extra so new activity can find food item
                AddIngredientFragment addIngredientFragment = new AddIngredientFragment();
                addIngredientFragment.setArguments(bundle);
                requireActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out).replace(container.getId(), addIngredientFragment).addToBackStack(null).commit();
            }
        });
        ingredientListView.setAdapter(IngredientStorage.ingredientAdapter);
        IngredientDB db = new IngredientDB();
        /*for (int i = 0; i < ingredients.size(); i++) {
            getParentFragmentManager().beginTransaction().add(R.id.linearLayoutIngredients, IngredientFragment.newInstance(ingredients.get(i))).commit();
        }*/
        return v;
    }
}