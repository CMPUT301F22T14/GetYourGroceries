package com.example.getyourgroceries.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.getyourgroceries.R;
import com.example.getyourgroceries.adapters.ShoppingListAdapter;
import com.example.getyourgroceries.entity.Ingredient;

import java.util.ArrayList;

public class ShoppingListFragment extends Fragment {

    ListView shoppingListView;
    public ShoppingListFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_shopping_list, container, false);
        shoppingListView = view.findViewById(R.id.shoppingListView);
        ArrayList<Ingredient> shoppingItems = new ArrayList<>();
        ArrayAdapter<Ingredient> adapter = new ShoppingListAdapter(requireActivity().getBaseContext(), shoppingItems);
        shoppingListView.setAdapter(adapter);
        adapter.add(new Ingredient("test", 12, 13.23, "Fruits"));
        adapter.notifyDataSetChanged();

        return view;
    }
}