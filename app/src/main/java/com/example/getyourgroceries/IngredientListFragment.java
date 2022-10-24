package com.example.getyourgroceries;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.getyourgroceries.entity.storedIngredient;

import java.util.Date;
import java.util.Objects;

public class IngredientListFragment extends Fragment {

    public IngredientListFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ingredient_list, container, false);
        Button addIngredientButton = v.findViewById(R.id.addIngredientButton);
        AddIngredientFragment addIngredientFragment = new AddIngredientFragment();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(false);

        // Button listener.
        addIngredientButton.setOnClickListener(view -> getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out).replace(container.getId(), addIngredientFragment).addToBackStack(null).commit());

        // Testing
        storedIngredient s = new storedIngredient("Apple", 12, 12.0, "Fruit", new Date(2012,12,12), "Pantry");
        getParentFragmentManager().beginTransaction().add(R.id.linearLayoutIngredients, IngredientFragment.newInstance(s)).commit();
        getParentFragmentManager().beginTransaction().add(R.id.linearLayoutIngredients, IngredientFragment.newInstance(s)).commit();
        getParentFragmentManager().beginTransaction().add(R.id.linearLayoutIngredients, IngredientFragment.newInstance(s)).commit();

        return v;
    }
}