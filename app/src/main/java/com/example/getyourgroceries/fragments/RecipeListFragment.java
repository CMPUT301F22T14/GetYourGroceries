/* RecipeListFragment class. */
package com.example.getyourgroceries.fragments;

// Import statements.
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.getyourgroceries.R;
import com.example.getyourgroceries.adapters.RecipeAdapter;
import com.example.getyourgroceries.control.RecipeDB;

import com.example.getyourgroceries.entity.Ingredient;

import com.example.getyourgroceries.entity.RecipeStorage;
import com.example.getyourgroceries.entity.IngredientStorage;
import com.example.getyourgroceries.entity.StoredIngredient;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;


import com.example.getyourgroceries.entity.RecipeStorage;


import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.getyourgroceries.entity.Recipe;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;


/**
 * Create an object to show the recipe list.
 */
public class RecipeListFragment extends Fragment {
    private static final String TAG = "RecipeListFragment";
    ArrayList<Recipe> recipeDataList;
    RecipeAdapter recipeAdapter;
    FirebaseFirestore db;

    ListView recipeList;
    Button addRecipeButton;
    Spinner sortDropDown;
    SwitchCompat sorting_switch;

    /**
     * Empty constructor.
     */
    public RecipeListFragment(){}

    /**
     * Create the view.
     * @param inflater Object to connect an XML file.
     * @param container The parent view.
     * @param savedInstanceState The saved state.
     * @return The created view.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("Recipe List");
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        setHasOptionsMenu(false);
        View v = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        RecipeStorage.recipeAdapter = new RecipeAdapter(requireActivity().getBaseContext(), RecipeStorage.recipeStorage);
        RecipeDB recipeDB = new RecipeDB();

        recipeList = v.findViewById(R.id.recipe_list);
        recipeList.setAdapter(RecipeStorage.recipeAdapter);

        // Listener to view a recipe
        recipeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                Recipe viewRecipe = (Recipe) recipeList.getItemAtPosition(position);
                bundle.putSerializable("viewRecipe", viewRecipe);
                RecipeViewFragment RecipeViewFragment = new RecipeViewFragment();
                RecipeViewFragment.setArguments(bundle);
                requireActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out).replace(container.getId(), RecipeViewFragment).addToBackStack(null).commit();
            }
        });

        // Listener to delete a recipe.
        recipeList.setOnItemLongClickListener((adapterView, view, i, l)-> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Would you like to delete this recipe?");
            builder.setTitle("Delete Recipe");
            builder.setCancelable(true);
            builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
                Recipe recipe = (Recipe) recipeList.getItemAtPosition(i);
                recipeDB.deleteRecipe(recipe);
            });
            builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
            return true;
        });
        sorting_switch = v.findViewById(R.id.sortingSwitchRecipe);

        // Listener to go to add recipe fragment
        addRecipeButton = v.findViewById(R.id.addRecipeButton);
        addRecipeButton.setOnClickListener(v1 -> {
            RecipeChangeHandlerFragment recipeChangeHandlerFragment = new RecipeChangeHandlerFragment();
            requireActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out).replace(container.getId(), recipeChangeHandlerFragment, "EDIT_RECIPE").addToBackStack("EDIT_RECIPE").commit();
        });

        Context context = this.getContext();
        sortDropDown = v.findViewById(R.id.sort_recipe_spinner);
        ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource(context,R.array.recipeSortBy,R.layout.ingredient_spinner_selected);
        sortAdapter.setDropDownViewResource(R.layout.ingredient_spinner_dropdown);
        sortDropDown.setAdapter(sortAdapter);

        //Sorting
        sortDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                boolean desc = sorting_switch.isChecked();
                sortCategory(position, desc);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                sortCategory(0,false);

            }
        });

        sorting_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String type = sortDropDown.getSelectedItem().toString();
                System.out.println("THIS IS HAPPENING"+ " " + type);
                if (type.equals("Name")){
                    sortCategory(0,isChecked);

                }
                else if (type.equals("Prep Time")){
                    sortCategory(1,isChecked);

                }
                else if (type.equals("Serving Count")){
                    sortCategory(2,isChecked);

                }
                else if (type.equals("Category")){
                    sortCategory(3,isChecked);
                }
            }
        });

        return v;
    }
    
    void sortCategory(int type,boolean desc){
        switch(type){
            case 0:
                if (desc) {
                    RecipeStorage.recipeAdapter.sort((o1, o2) -> o1.getName().compareTo(o2.getName())*-1);
                }
                else{
                    RecipeStorage.recipeAdapter.sort(Comparator.comparing(Recipe::getName));
                }
                RecipeStorage.recipeAdapter.notifyDataSetChanged();
                break;

            case 1:
                if (desc){
                    RecipeStorage.recipeAdapter.sort(Comparator.comparingInt(Recipe::getPrepTime).reversed());
                }
                else{
                    RecipeStorage.recipeAdapter.sort(Comparator.comparingInt(Recipe::getPrepTime));
                }
                RecipeStorage.recipeAdapter.notifyDataSetChanged();
                break;
            case 2:
                if (desc){
                    RecipeStorage.recipeAdapter.sort(Comparator.comparingInt(Recipe::getNumOfServings).reversed());
                    //RecipeStorage.recipeAdapter.sort((o1, o2) -> String.valueOf(o1.getNumOfServings()).compareTo(String.valueOf(o2.getNumOfServings()*-1)));
                }
                else{
                    RecipeStorage.recipeAdapter.sort(Comparator.comparingInt(Recipe::getNumOfServings));
                }
                RecipeStorage.recipeAdapter.notifyDataSetChanged();
                break;
            case 3:
                if (desc){
                    RecipeStorage.recipeAdapter.sort((o1, o2) -> o1.getRecipeCategory().compareTo(o2.getRecipeCategory())*-1);
                }
                else{
                    RecipeStorage.recipeAdapter.sort(Comparator.comparing(Recipe::getRecipeCategory));
                }
                RecipeStorage.recipeAdapter.notifyDataSetChanged();
                break;
        }

    }
}