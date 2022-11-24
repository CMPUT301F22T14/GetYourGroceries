/* IngredientChangeHandlerFragment class. */
package com.example.getyourgroceries.fragments;

// Import statements.
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.getyourgroceries.R;
import com.example.getyourgroceries.control.IngredientDB;
import com.example.getyourgroceries.entity.IngredientStorage;
import com.example.getyourgroceries.entity.MealPlanStorage;
import com.example.getyourgroceries.entity.StoredIngredient;
import com.google.android.material.textfield.TextInputLayout;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The AddIngredientFragment is the class for the add ingredient screen.
 * AddIngredientFragment extends {@link Fragment}.
 */
public class IngredientChangeHandlerFragment extends Fragment {
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private StoredIngredient editIngredient = null;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    /**
     * The AddIngredientFragment constructor.
     */
    public IngredientChangeHandlerFragment() {
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
        View v = inflater.inflate(R.layout.change_ingredient, container, false);
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

        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();


        // Initialization.
        ConstraintLayout addIngredientLayout = requireActivity().findViewById(R.id.change_ingredient_layout);
        addIngredientLayout.setImportantForAutofill(View.IMPORTANT_FOR_AUTOFILL_NO);

        //Hide keyboard when you click outside textViews
        addIngredientLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean change) {
                if (change) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });

        // set an ingredient to edit if argument exists
        if (getArguments() != null){
           editIngredient = IngredientStorage.getInstance().getIngredient(getArguments().getInt("editIngredient"));
            actionBar.setTitle("Edit Ingredient");
        } else{
            actionBar.setTitle("Add Ingredient");
        }

        // Set up calendar.
        Calendar cal = Calendar.getInstance();
        if (getArguments() != null) {
            cal.setTime(editIngredient.getBestBefore());
        }
        AtomicInteger yearSet = new AtomicInteger(cal.get(Calendar.YEAR));
        AtomicInteger monthSet = new AtomicInteger(cal.get(Calendar.MONTH));
        AtomicInteger daySet = new AtomicInteger(cal.get(Calendar.DAY_OF_MONTH));
        TextView displayDate = requireActivity().findViewById(R.id.change_ingredient_expiry);
        displayDate.setTextSize(20);
        displayDate.setGravity(Gravity.CENTER_VERTICAL);
        displayDate.setOnClickListener(view2 -> {
            DatePickerDialog dialog = new DatePickerDialog(getContext(), 0, dateSetListener, yearSet.get(), monthSet.get(), daySet.get());
            dialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
            dialog.show();
        });

        // Change the calendar.
        dateSetListener = (datePicker, year, month, day) -> {
            yearSet.set(year);
            monthSet.set(month);
            daySet.set(day);
            String date = (month+1) + "/" + day + "/" + year;
            displayDate.setText(date);
        };

        // Set up location spinner.
        AutoCompleteTextView location = requireActivity().findViewById(R.id.change_ingredient_location);
        ArrayList<String> locations = new ArrayList<>();
        locations.add("Pantry");
        locations.add("Fridge");
        locations.add("Freezer");
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner, locations);
        location.setAdapter(locationAdapter);
        //always show all options
        location.setThreshold(200);

        // Set up the ingredient category spinner.
        AutoCompleteTextView category = requireActivity().findViewById(R.id.change_ingredient_category);
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Fruit");
        categories.add("Vegetable");
        categories.add("Meat");
        categories.add("Carbs");

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner, categories);
        category.setAdapter(categoryAdapter);
        //always show all options
        category.setThreshold(200);

        TextView descriptionText = requireActivity().findViewById(R.id.change_ingredient_description);
        TextView quantityText = requireActivity().findViewById(R.id.change_ingredient_quantity);
        TextView unitText = requireActivity().findViewById(R.id.change_ingredient_unit);

        // Format unit price.
        unitText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            /**
             * Format the unit price when focus changes.
             * @param view The containing view.
             * @param b Focus status.
             */
            @Override
            public void onFocusChange(View view, boolean b) {

                // When the focus leaves, round the unit price to 2 decimals.
                if (!b && !unitText.getText().toString().isEmpty())
                    unitText.setText(df.format(Double.parseDouble(unitText.getText().toString())));
            }
        });

        // Set the values to the previous values if in edit mode.
        if (editIngredient != null){
            descriptionText.setText(editIngredient.getDescription());
            quantityText.setText(String.valueOf(editIngredient.getAmount()));
            displayDate.setText((new SimpleDateFormat("MM/dd/yyy")).format(editIngredient.getBestBefore()));
            location.setText(editIngredient.getLocation());
            category.setText((editIngredient.getCategory()));
            unitText.setText(df.format(editIngredient.getUnit()));
        }

        // Get the text layouts for error messages.
        TextInputLayout tilDescription = requireActivity().findViewById(R.id.change_ingredient_description_til);
        TextInputLayout tilQuantity = requireActivity().findViewById(R.id.change_ingredient_quantity_til);
        TextInputLayout tilExpiry = requireActivity().findViewById(R.id.change_ingredient_expiry_til);
        TextInputLayout tilLocation = requireActivity().findViewById(R.id.change_ingredient_location_til);
        TextInputLayout tilCategory = requireActivity().findViewById(R.id.change_ingredient_category_til);
        TextInputLayout tilUnit = requireActivity().findViewById(R.id.change_ingredient_unit_til);

        // Add the ingredient.
        Button confirmButton = requireActivity().findViewById(R.id.change_ingredient_confirm);
        confirmButton.setOnClickListener(view1 ->{

            // Get the data.
            String description = descriptionText.getText().toString();
            String quantity = quantityText.getText().toString();
            String expiry = displayDate.getText().toString();
            String locationText = location.getText().toString();
            String categoryText = category.getText().toString();
            String unitCost = unitText.getText().toString();

            // Error checking.
            int error = 0;
            if (description.equals("")) {
                tilDescription.setError("Description cannot be empty!");
                error = 1;
            } else {
                tilDescription.setErrorEnabled(false);
            }
            if (quantity.equals("")) {
                tilQuantity.setError("Quantity cannot be empty!");
                error = 1;
            } else {
                tilQuantity.setErrorEnabled(false);
            }
            if (expiry.equals("")) {
                tilExpiry.setError("Date cannot be empty!");
                error = 1;
            } else {
                tilExpiry.setErrorEnabled(false);
            }
            if (locationText.equals("")) {
                tilLocation.setError("Location cannot be empty!");
                error = 1;
            } else {
                tilLocation.setErrorEnabled(false);
            }
            if (categoryText.equals("")) {
                tilCategory.setError("Category cannot be empty!");
                error = 1;
            } else {
                tilCategory.setErrorEnabled(false);
            }
            if (unitCost.equals("")){
                tilUnit.setError("Cost cannot be empty!");
                error = 1;
            } else {
                tilUnit.setErrorEnabled(false);
            }
            if (error == 1) {
                return;
            }
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);

            // If in edit mode, update the attributes.
            if (editIngredient != null){
                editIngredient.setDescription(description);
                editIngredient.setAmount(Integer.parseInt(quantity));
                editIngredient.setCategory(categoryText);
                editIngredient.setLocation(locationText);
                editIngredient.setUnit(Double.parseDouble(unitCost));
                try{
                    editIngredient.setBestBefore(formatter.parse(expiry));
                }
                catch (ParseException e){
                    e.printStackTrace();
                }
                IngredientStorage.getInstance().updateIngredient(editIngredient);
                //IngredientDB db = new IngredientDB();
                //db.updateIngredient(editIngredient);
                requireActivity().getSupportFragmentManager().popBackStackImmediate();
                return;
            }

            // Create the ingredient object.
            StoredIngredient ingredient = null;
            try {
                ingredient = new StoredIngredient(description, Integer.parseInt(quantity), Double.parseDouble(unitCost), categoryText, formatter.parse(expiry), locationText);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // Add the ingredient to Firebase.
            IngredientStorage.getInstance().addIngredient(ingredient, true);
            MealPlanStorage.getInstance().setRecentIngredient(ingredient);
            requireActivity().getSupportFragmentManager().popBackStackImmediate();
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
