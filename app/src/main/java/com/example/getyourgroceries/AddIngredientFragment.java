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
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

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
        displayDate.setGravity(Gravity.CENTER);
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
            String date = month + "/" + day + "/" + year;
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
        locations.add("Enter A Category");
        locations.add("Category 1");
        locations.add("Category 2");
        locations.add("Category 3");
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner, locations) {

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
