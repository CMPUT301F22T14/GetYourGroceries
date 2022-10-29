package com.example.getyourgroceries;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.getyourgroceries.entity.StoredIngredient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class IngredientStorageAdapter extends ArrayAdapter<StoredIngredient> {

    private ArrayList<StoredIngredient> ingredients;
    private Context context;
    public IngredientStorageAdapter(Context context, ArrayList<StoredIngredient> ingredients){
        super(context,0, ingredients);
        this.ingredients = ingredients;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.ingredient_item, parent,false);
        }

        StoredIngredient ingredient = ingredients.get(position);


        ((TextView)view.findViewById(R.id.ingredient_name)).setText(ingredient.getDescription());
        ((TextView)view.findViewById(R.id.ingredient_qty)).setText("Quantity: "+String.valueOf(ingredient.getAmount()));
        ((TextView)view.findViewById(R.id.ingredient_bestbefore)).setText("BBD: "+(new SimpleDateFormat("MM/dd/yyy")).format(ingredient.getBestBefore()));
        ((TextView)view.findViewById(R.id.ingredient_location)).setText("Location: "+ingredient.getLocation());
        ((TextView)view.findViewById(R.id.ingredient_category)).setText("Category: "+ ingredient.getCategory());


        return view;

    }

}