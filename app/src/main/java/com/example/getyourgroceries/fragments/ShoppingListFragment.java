package com.example.getyourgroceries.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.example.getyourgroceries.entity.IngredientStorage;
import com.example.getyourgroceries.entity.MealPlan;
import com.example.getyourgroceries.entity.MealPlanDay;
import com.example.getyourgroceries.entity.MealPlanStorage;
import com.example.getyourgroceries.entity.ScaledRecipe;
import com.example.getyourgroceries.entity.StoredIngredient;
import com.google.android.material.materialswitch.MaterialSwitch;

import java.util.ArrayList;
import java.util.Iterator;

public class ShoppingListFragment extends Fragment {
    ListView shoppingListView;
    Spinner sortDropDown;
    MaterialSwitch sortingSwitch;

    public ShoppingListFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping_list, container, false);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        actionBar.setTitle("Shopping List");
        shoppingListView = view.findViewById(R.id.shoppingListView);
        ArrayList<Ingredient> shoppingItems = calculateItems();
        ArrayAdapter<Ingredient> adapter = new ShoppingListAdapter(requireActivity().getBaseContext(), shoppingItems, requireActivity().getSupportFragmentManager());
        shoppingListView.setAdapter(adapter);

        sortingSwitch = view.findViewById(R.id.sorting_switch_shoppinglist);
        sortDropDown = view.findViewById(R.id.sort_shoppinglist_spinner);
        ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource(getContext(), R.array.shoppingListSortBy, R.layout.ingredient_spinner_selected);
        sortAdapter.setDropDownViewResource(R.layout.ingredient_spinner_dropdown);
        sortDropDown.setAdapter(sortAdapter);

        //Sorting
        sortDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                boolean desc = sortingSwitch.isChecked();
                // TODO: sort based on desc variable
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO: sort as ascending
            }
        });

        sortingSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String type = sortDropDown.getSelectedItem().toString();
            if (type.equals("Description")) {
                // TODO: sort based on description
            } else if (type.equals("Category")) {
                // TODO: sort based on category
            }
        });

        adapter.notifyDataSetChanged();

        return view;
    }

    public ArrayList<Ingredient> calculateItems(){
        ArrayList<Ingredient> calculatedItems = new ArrayList<>();
        for (MealPlan mealPlan: MealPlanStorage.getInstance().getMealPlanList()){
            for (MealPlanDay mealPlanDay: mealPlan.getMealPlanDays()){
                for (Ingredient ingredient : mealPlanDay.getIngredientList()){
                    // now loop over calculated items and see if matching ingredient is there
                    boolean found = false;
                    for (Ingredient calcItem : calculatedItems){
                        if (calcItem.getDescription().equals(ingredient.getDescription())){
                            calcItem.setAmount(calcItem.getAmount() + ingredient.getAmount());
                            found = true;
                        }
                    }
                    // if not found then add to items
                    if (!found){
                        calculatedItems.add(new Ingredient(ingredient.getDescription(), ingredient.getAmount(), ingredient.getUnit(), ingredient.getCategory()));
                    }
                }
                for (ScaledRecipe recipe : mealPlanDay.getRecipeList()){
                    for (Ingredient ingredient : recipe.getRecipe().getIngredientList()){
                        boolean found = false;
                        for (Ingredient calcItem : calculatedItems){
                            if (calcItem.getDescription().equals(ingredient.getDescription())){
                                calcItem.setAmount(calcItem.getAmount() + (ingredient.getAmount())*recipe.getScale());
                                found = true;
                            }
                        }
                        // if not found then add to items
                        if (!found){
                            calculatedItems.add(new Ingredient(ingredient.getDescription(), ingredient.getAmount()*recipe.getScale(), ingredient.getUnit(), ingredient.getCategory()));
                        }
                    }
                }
            }
        }


        Iterator<Ingredient> itr = calculatedItems.iterator();
        while (itr.hasNext()){
            Ingredient ingredient = itr.next();
            for (StoredIngredient storedIngredient: IngredientStorage.getInstance().getIngredientList()){
                if (ingredient.getDescription().equals(storedIngredient.getDescription())){
                    int res = ingredient.getAmount() - storedIngredient.getAmount();
                    if (res < 0)
                        itr.remove();
                    else
                        ingredient.setAmount(res);
                }
            }
        }
        return calculatedItems;

    }
}