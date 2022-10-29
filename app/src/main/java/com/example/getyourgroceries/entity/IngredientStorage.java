package com.example.getyourgroceries.entity;

import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Observable;

public class IngredientStorage {
    public static ArrayList<StoredIngredient> ingredientStorage = new ArrayList<>();
    public static ArrayAdapter<StoredIngredient> ingredientAdapter;
    //public static ArrayAdapter<StoredIngredient> ingredientArrayAdapter = ingredientStorage;

    /*public void addIngredient(StoredIngredient ingredient){
        ingredientStorage.add(ingredient);

        notifyObservers();
    }*/
    //TODO: add sorting capabilities here
}
