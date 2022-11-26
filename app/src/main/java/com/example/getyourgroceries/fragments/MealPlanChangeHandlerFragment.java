/* IngredientChangeHandlerFragment class. */
package com.example.getyourgroceries.fragments;

// Import statements.
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.getyourgroceries.R;
import com.example.getyourgroceries.adapters.DayListAdapter;
import com.example.getyourgroceries.entity.MealPlanDay;
import com.example.getyourgroceries.entity.Recipe;
import com.example.getyourgroceries.entity.RecipeStorage;
import com.example.getyourgroceries.entity.ScaledRecipe;
import com.example.getyourgroceries.entity.StoredIngredient;
import com.example.getyourgroceries.interfaces.OnMealPlanFragmentInteractionListener;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The AddIngredientFragment is the class for the add ingredient screen.
 * AddIngredientFragment extends {@link Fragment}.
 */
public class MealPlanChangeHandlerFragment extends Fragment implements OnMealPlanFragmentInteractionListener {

    // Attributes.
    private View view;
    ArrayList<MealPlanDay> days;
    ArrayAdapter<MealPlanDay> daysAdapter;

    /**
     * The AddIngredientFragment constructor.
     */
    public MealPlanChangeHandlerFragment() {
        super();
    }

    /**
     * The onCreateView method will be called when this fragment becomes active.
     * @param inflater Allows the new view to be created.
     * @param container The container of the view.
     * @param savedInstanceState The saved state.
     * @return A view that will be shown on the screen.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view != null){
            return view;
        }

        // Create the view.
        view = inflater.inflate(R.layout.change_mealplan, container, false);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        ListView dayListView = view.findViewById(R.id.day_list);
        days = new ArrayList<>();
        daysAdapter = new DayListAdapter(requireActivity().getBaseContext(), days,requireActivity().getSupportFragmentManager());
        dayListView.setAdapter(daysAdapter);
        Button addDay = view.findViewById(R.id.add_day);
        addDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Add new day");

                // Set up the input
                final EditText input = new EditText(getContext());
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                //input.setInputType(InputType.NA | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        daysAdapter.add(new MealPlanDay(input.getText().toString()));
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });

        return view;
    }


    public void onSubmitPressed(Recipe recipe, int dayPosition){

        AlertDialog.Builder scaleAlertBox = new AlertDialog.Builder(view.getRootView().getContext());
        scaleAlertBox.setTitle("Input desired scale (default 1)");

        final EditText input = new EditText(view.getRootView().getContext());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        scaleAlertBox.setView(input);
        scaleAlertBox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                int scale;
                try {
                    scale = Integer.parseInt(String.valueOf(input.getText()));
                } catch (NumberFormatException e) {
                    scale = 1;
                }

                days.get(dayPosition).addRecipe(new ScaledRecipe(recipe, scale));
                daysAdapter.notifyDataSetChanged();
            }
        });

        scaleAlertBox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //RecipeStorage recipeStorage = RecipeStorage.getInstance();
            }
        });

        scaleAlertBox.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                RecipeStorage recipeStorage = RecipeStorage.getInstance();
                recipeStorage.deleteRecipe(recipe, true);
            }
        });

        scaleAlertBox.show();


    }




    /**
     * The onOptionsItemSelected method will go to the previous fragment when the back button is pressed.
     * @param item The item selected.
     * @return On success, true.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return requireActivity().getSupportFragmentManager().popBackStackImmediate();
    }
}
