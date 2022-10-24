package com.example.getyourgroceries;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.getyourgroceries.entity.storedIngredient;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IngredientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngredientFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "name";
    private static final String ARG_PARAM2 = "qty";
    private static final String ARG_PARAM3 = "bestbefore";
    private static final String ARG_PARAM4 = "location";
    private static final String ARG_PARAM5 = "category";

    // TODO: Rename and change types of parameters
    private String name;
    private Integer quantity;
    private Date bestBefore;
    private String location;
    private String category;

    public IngredientFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static IngredientFragment newInstance(storedIngredient ingredient) {
        IngredientFragment fragment = new IngredientFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, ingredient.getDescription());
        args.putInt(ARG_PARAM2, ingredient.getAmount());
        args.putSerializable(ARG_PARAM3, ingredient.getBestBefore());
        args.putString(ARG_PARAM4, ingredient.getLocation());
        args.putString(ARG_PARAM5, ingredient.getCategory());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_PARAM1);
            quantity = getArguments().getInt(ARG_PARAM2);
            bestBefore = (Date) getArguments().getSerializable(ARG_PARAM3);
            location = getArguments().getString(ARG_PARAM4);
            category = getArguments().getString(ARG_PARAM5);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ingredient, container, false);
        ((TextView)v.findViewById(R.id.ingredient_name)).setText(name);
        ((TextView)v.findViewById(R.id.ingredient_qty)).setText("Quantity: "+String.valueOf(quantity));
        ((TextView)v.findViewById(R.id.ingredient_bestbefore)).setText("BBD: "+(new SimpleDateFormat("MM/dd/yyy")).format(bestBefore));
        ((TextView)v.findViewById(R.id.ingredient_location)).setText("Location: "+location);
        ((TextView)v.findViewById(R.id.ingredient_category)).setText("Category: "+ category);
        return v;
    }
}