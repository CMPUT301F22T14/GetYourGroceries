package com.example.getyourgroceries;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.getyourgroceries.control.IngredientDB;
import com.example.getyourgroceries.entity.StoredIngredient;
import com.google.android.material.textfield.TextInputLayout;

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
public class AddIngredientFragment extends Fragment {

    // Attributes.
    private DatePickerDialog.OnDateSetListener dateSetListener;

    /**
     * The AddIngredientFragment constructor.
     */
    public AddIngredientFragment() {
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
        View v = inflater.inflate(R.layout.add_ingredient, container, false);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        return v;
    }

    /**
     * The onViewCreated method will be called once the view has been created.
     * @param view The created view.
     * @param savedInstanceState The saved state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ConstraintLayout addIngredientLayout = requireActivity().findViewById(R.id.add_ingredient_layout);
        addIngredientLayout.setImportantForAutofill(View.IMPORTANT_FOR_AUTOFILL_NO);

        // Set up calendar.
        Calendar cal = Calendar.getInstance();
        AtomicInteger yearSet = new AtomicInteger(cal.get(Calendar.YEAR));
        AtomicInteger monthSet = new AtomicInteger(cal.get(Calendar.MONTH));
        AtomicInteger daySet = new AtomicInteger(cal.get(Calendar.DAY_OF_MONTH));
        TextView displayDate = requireActivity().findViewById(R.id.add_ingredient_expiry);
        displayDate.setTextSize(20);
        displayDate.setGravity(Gravity.CENTER_VERTICAL);
        displayDate.setOnClickListener(view2 -> {
            DatePickerDialog dialog = new DatePickerDialog(getContext(), 0, dateSetListener, yearSet.get(), monthSet.get(), daySet.get());
            dialog.getDatePicker().setMinDate(cal.getTimeInMillis());
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
        // TODO: Make the hint text gray.
        // TODO: Add rounded corners to the dropdown view.
        Spinner location = requireActivity().findViewById(R.id.add_ingredient_location);
        ArrayList<String> locations = new ArrayList<>();
        locations.add("Enter A Location");
        locations.add("Pantry");
        locations.add("Fridge");
        locations.add("Freezer");
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner, locations) {

            /**
             * The isEnabled method will disallow te first dropdown choice.
             * @param position The selected choice.
             * @return True if the position should be disallowed.
             */
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            /**
             * The getDropDownView will change the text colours of the choices appropriately.
             * @param position The selected choice.
             * @param convertView The old view to reuse.
             * @param parent The parent view.
             * @return The new view.
             */
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                if (position == 0) {
                    textView.setTextColor(Color.GRAY);
                } else {
                    textView.setTextColor(Color.WHITE);
                }
                return view;
            }
        };
        location.setAdapter(locationAdapter);

        // Set up the ingredient category spinner.
        // TODO: Make the hint text gray.
        // TODO: Add rounded corners to the dropdown view.
        // TODO: Add ingredient categories.
        Spinner category = requireActivity().findViewById(R.id.add_ingredient_category);
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Enter A Category");
        categories.add("Category 1");
        categories.add("Category 2");
        categories.add("Category 3");
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner, categories) {

            /**
             * The isEnabled method will disallow te first dropdown choice.
             * @param position The selected choice.
             * @return True if the position should be disallowed.
             */
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            /**
             * The getDropDownView will change the text colours of the choices appropriately.
             * @param position The selected choice.
             * @param convertView The old view to reuse.
             * @param parent The parent view.
             * @return The new view.
             */
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                if (position == 0) {
                    textView.setTextColor(Color.GRAY);
                } else {
                    textView.setTextColor(Color.WHITE);
                }
                return view;
            }
        };
        category.setAdapter(categoryAdapter);

        // Get the text layouts.
        TextInputLayout tilDescription = requireActivity().findViewById(R.id.add_ingredient_description_til);
        TextInputLayout tilQuantity = requireActivity().findViewById(R.id.add_ingredient_quantity_til);
        TextInputLayout tilExpiry = requireActivity().findViewById(R.id.add_ingredient_expiry_til);
        TextInputLayout tilLocation = requireActivity().findViewById(R.id.add_ingredient_location_til);
        TextInputLayout tilCategory = requireActivity().findViewById(R.id.add_ingredient_category_til);

        // Add the ingredient.
        Button confirmButton = requireActivity().findViewById(R.id.add_ingredient_confirm);
        confirmButton.setOnClickListener(view1 ->{

            // Create the required objects.
            EditText descriptionText = requireActivity().findViewById(R.id.add_ingredient_description);
            EditText quantityText = requireActivity().findViewById(R.id.add_ingredient_quantity);

            // Get the data.
            String description = descriptionText.getText().toString();
            String quantity = quantityText.getText().toString();
            String expiry = displayDate.getText().toString();
            String locationText = location.getSelectedItem().toString();
            String categoryText = category.getSelectedItem().toString();

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
            if (locationText.equals("Enter A Location")) {
                tilLocation.setError("Location cannot be empty!");
                error = 1;
            } else {
                tilLocation.setErrorEnabled(false);
            }
            if (categoryText.equals("Enter A Category")) {
                tilCategory.setError("Category cannot be empty!");
                error = 1;
            } else {
                tilCategory.setErrorEnabled(false);
            }
            if (error == 1) {
                return;
            }

            // Create the ingredient object.
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
            StoredIngredient ingredient = null;
            try {
                ingredient = new StoredIngredient(description, Integer.parseInt(quantity), 1.0, categoryText, formatter.parse(expiry), locationText);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // Add the ingredient to Firebase.
            IngredientDB db = new IngredientDB();
            db.addRecipe(ingredient);
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
