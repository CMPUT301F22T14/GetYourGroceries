/* HomeScreenFragment class. */
package com.example.getyourgroceries;

// Import statements.
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.getyourgroceries.fragments.IngredientChangeHandlerFragment;
import com.example.getyourgroceries.fragments.IngredientListFragment;
import com.example.getyourgroceries.fragments.RecipeChangeHandlerFragment;
import com.example.getyourgroceries.fragments.RecipeListFragment;
import com.example.getyourgroceries.fragments.RecipeViewFragment;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Create an object to represent the home screen.
 */
public class HomeScreenFragment extends Fragment {

    List<String> quickaddchoices;

    /**
     * Empty constructor.
     */
    public HomeScreenFragment() {}

    /**
     * Create the view.
     * @param inflater Inflater to set an XML file.
     * @param container Containing view.
     * @param savedInstanceState The saved state.
     * @return The created view.
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("Get Your Groceries");
        // Inflate the layout for this fragment.
        View v = inflater.inflate(R.layout.fragment_home_screen, container, false);

        Spinner quickaddSpinner = v.findViewById(R.id.QuickAddSpinner);
        quickaddchoices = new ArrayList<>();

        // add the choices to the Arraylist
        quickaddchoices.add("SELECT");
        quickaddchoices.add("Ingredient");
        quickaddchoices.add("Recipe");
        quickaddchoices.add("Meal Plan");
        Context context = this.getContext();

        // add the choices from the Arraylist to the Spinner
        quickaddSpinner.setAdapter(new ArrayAdapter<>(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, quickaddchoices));

        //redirect user to correct quick add page, based on their choice from the spinner
        quickaddSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String userChoice = quickaddchoices.get(position);
                Toast.makeText(context, userChoice, Toast.LENGTH_SHORT).show();
                //Toast.makeText(HomeScreenFragment.this, userChoice, Toast.LENGTH_SHORT).show();

                if (userChoice == "Ingredient"){
                    //Redirect the user to the Add Ingredient page
                    new IngredientListFragment();
                    IngredientChangeHandlerFragment ingredientChangeHandlerFragment = new IngredientChangeHandlerFragment();
                    requireActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out).replace(container.getId(), ingredientChangeHandlerFragment).addToBackStack(null).commit();
                }

                if (userChoice == "Recipe"){
                    //Redirect the user to the Add Recipe page
                    new RecipeListFragment();
                    RecipeChangeHandlerFragment recipeChangeHandlerFragment = new RecipeChangeHandlerFragment();
                    requireActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out).replace(container.getId(), recipeChangeHandlerFragment).addToBackStack(null).commit();
                    //requireActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out).replace(container.getId(), recipeChangeHandlerFragment, "EDIT_RECIPE").addToBackStack("EDIT_RECIPE").commit();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        TextView description = v.findViewById(R.id.home_description);
        ImageView homelogo = v.findViewById(R.id.home_logoimage);
        Button quickaddbutton = v.findViewById(R.id.quickaddbutton);

        description.setText("Plan your meals with ease and efficiency!");
        return v;



    }

}