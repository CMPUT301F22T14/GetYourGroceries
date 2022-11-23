/* IngredientChangeHandlerFragment class. */
package com.example.getyourgroceries.fragments;

// Import statements.

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.getyourgroceries.R;
import com.example.getyourgroceries.adapters.DayListAdapter;
import com.example.getyourgroceries.entity.Ingredient;
import com.example.getyourgroceries.entity.IngredientStorage;
import com.example.getyourgroceries.entity.MealPlanDay;
import com.example.getyourgroceries.entity.Recipe;
import com.example.getyourgroceries.entity.StoredIngredient;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The AddIngredientFragment is the class for the add ingredient screen.
 * AddIngredientFragment extends {@link Fragment}.
 */
public class MealPlanChangeHandlerFragment extends Fragment {

    // Attributes.


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

        // Create the view.
        View v = inflater.inflate(R.layout.change_mealplan, container, false);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        return v;
    }

    /**
     * The onViewCreated method will be called once the view has been created.
     * @param view The created view.
     * @param savedInstanceState The saved state.
     */
    @SuppressLint("SimpleDateFormat")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        ListView dayListView = view.findViewById(R.id.day_list);
        ArrayList<MealPlanDay> days = new ArrayList<>();
        ArrayAdapter<MealPlanDay> daysAdapter = new DayListAdapter(requireActivity().getBaseContext(), days,requireActivity().getSupportFragmentManager());
        dayListView.setAdapter(daysAdapter);
        Button addDay = view.findViewById(R.id.add_day);

        MealPlanDay fakeDay = new MealPlanDay("test1");
//        fakeDay.addIngredient(new Ingredient("Apple", 12, 0.99, "Fruit"));
//        fakeDay.addIngredient(new Ingredient("Banana", 12, 0.99, "Fruit"));
//        fakeDay.addRecipe(new Recipe("Apple Pie", 60, 1, "Baking", "Let Cool", "/images/apple-pie"));
        daysAdapter.add(fakeDay);
        MealPlanDay fakeDay2 = new MealPlanDay("test1");
//        fakeDay.addIngredient(new Ingredient("Apple", 12, 0.99, "Fruit"));
//        fakeDay.addRecipe(new Recipe("Apple Pie", 60, 1, "Baking", "Let Cool", "/images/apple-pie"));
        daysAdapter.add(fakeDay2);


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
