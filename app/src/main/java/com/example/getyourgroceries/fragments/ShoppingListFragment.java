package com.example.getyourgroceries.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.getyourgroceries.R;
import com.example.getyourgroceries.adapters.ShoppingListAdapter;
import com.example.getyourgroceries.entity.Ingredient;
import com.example.getyourgroceries.entity.RecipeStorage;
import com.google.android.material.materialswitch.MaterialSwitch;

import java.util.ArrayList;

public class ShoppingListFragment extends Fragment {
    ListView shoppingListView;
    Spinner sortDropDown;
    MaterialSwitch sorting_switch;

    public ShoppingListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        actionBar.setTitle("Shopping List");
        shoppingListView = view.findViewById(R.id.shoppingListView);
        ArrayList<Ingredient> shoppingItems = new ArrayList<>();
        ArrayAdapter<Ingredient> adapter = new ShoppingListAdapter(requireActivity().getBaseContext(), shoppingItems);
        shoppingListView.setAdapter(adapter);

        sorting_switch = view.findViewById(R.id.sorting_switch_shoppinglist);
        sortDropDown = view.findViewById(R.id.sort_shoppinglist_spinner);
        ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource(getContext(), R.array.shoppingListSortBy, R.layout.ingredient_spinner_selected);
        sortAdapter.setDropDownViewResource(R.layout.ingredient_spinner_dropdown);
        sortDropDown.setAdapter(sortAdapter);

        //Sorting
        sortDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                boolean desc = sorting_switch.isChecked();
                // TODO: sort based on desc variable
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO: sort as ascending
            }
        });

        sorting_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String type = sortDropDown.getSelectedItem().toString();
                if (type.equals("Description")) {
                    // TODO: sort based on description
                } else if (type.equals("Category")) {
                    // TODO: sort based on category
                }
            }
        });

        adapter.add(new Ingredient("test", 12, 13.23, "Fruits"));
        adapter.notifyDataSetChanged();

        return view;
    }
}