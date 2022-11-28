package com.example.getyourgroceries.fragments;

import androidx.appcompat.app.AlertDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.getyourgroceries.MainActivity;
import com.example.getyourgroceries.R;
import com.example.getyourgroceries.entity.Ingredient;
import com.example.getyourgroceries.entity.StoredIngredient;
import com.example.getyourgroceries.interfaces.OnCollectIngredientFragmentInteractionListener;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CollectIngredientFragment extends DialogFragment {
    OnCollectIngredientFragmentInteractionListener listener;
    private Ingredient ingredient;

    /**
     * Constructor
     *
     * @param ingredient object being passed in
     */
    public CollectIngredientFragment(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    /**
     * Call when a fragment gets attached to its context.
     *
     * @param context The context of the fragment.
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity act = (MainActivity) context;

        listener = (OnCollectIngredientFragmentInteractionListener) act.getSupportFragmentManager().findFragmentByTag("SHOPPING_LIST");
    }

    /**
     * Allow users to add ingredients to recipes.
     *
     * @param savedInstanceState The saved state of the fragment.
     * @return A dialog that allows ingredient addition.
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.collect_ingredient, null);
        final Date[] ingDate = new Date[1];

        // Calendar View
        Calendar cal = Calendar.getInstance();
        TextView displayDate = view.findViewById(R.id.change_ingredient_expiry);
        displayDate.setTextSize(20);
        displayDate.setGravity(Gravity.CENTER_VERTICAL);
        displayDate.setOnClickListener(view2 -> {
            MaterialDatePicker datePicker =
                    MaterialDatePicker.Builder.datePicker()
                            .setTitleText("Select date")
                            .setSelection(cal.getTimeInMillis())
                            .build();

            datePicker.addOnPositiveButtonClickListener(selection -> {
                // for some reason it selects the day before so add 24hrs worth of milliseconds to make it the proper day
                cal.setTimeInMillis((long) ((long) selection + 8.64e+7));
                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
                String formattedDate = format.format(cal.getTime());
                try {
                    ingDate[0] = format.parse(formattedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                displayDate.setText(formattedDate);
            });

            datePicker.show(requireActivity().getSupportFragmentManager(), "");
        });

        // Set up location spinner.
        Map<String, String> locationIDs = new HashMap<>();
        ArrayList<String> locations = new ArrayList<>();
        locations.add("+ Save New Location");
        locations.add("- Delete Saved Location");
        CollectionReference locationCollection = FirebaseFirestore.getInstance().collection("Locations");
        locationCollection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    locations.add(Objects.requireNonNull(document.getData().get("Location")).toString());
                    locationIDs.put(Objects.requireNonNull(document.getData().get("Location")).toString(), document.getId());
                }
            }
        });
        AutoCompleteTextView location = view.findViewById(R.id.change_ingredient_location);
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(view.getRootView().getContext(), R.layout.spinner, locations);
        location.setAdapter(locationAdapter);
        location.setThreshold(200);
        location.setOnItemClickListener((adapterView, view12, i, l) -> {
            if (locations.get(i).equals("+ Save New Location")) {
                location.setText("");
                final EditText newLocationInput = new EditText(getContext());
                AlertDialog.Builder builder = new MaterialAlertDialogBuilder(getContext());
                builder
                        .setTitle("Add Location")
                        .setMessage("Enter a new location:")
                        .setCancelable(true)
                        .setView(newLocationInput)
                        .setPositiveButton("OK", (dialog, which) -> {
                            String newLocation = newLocationInput.getText().toString();
                            location.setText(newLocation);
                            Map<String, Object> insertLocation = new HashMap<>();
                            insertLocation.put("Location", newLocation);
                            locationCollection.document().set(insertLocation);
                            dialog.dismiss();
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
                        .create()
                        .show();
            } else if (locations.get(i).equals("- Delete Saved Location")) {
                location.setText("");
                final EditText deleteLocationInput = new EditText(getContext());
                AlertDialog.Builder builder = new MaterialAlertDialogBuilder(getContext());
                builder
                        .setTitle("Delete Location")
                        .setMessage("Delete an existing location:")
                        .setCancelable(true)
                        .setView(deleteLocationInput)
                        .setPositiveButton("OK", (dialog, which) -> {
                            String deleteLocation = deleteLocationInput.getText().toString();
                            String id = locationIDs.get(deleteLocation);
                            if (id != null) {
                                locationCollection.document(id).delete();
                            }
                            dialog.dismiss();
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
                        .create()
                        .show();
            }
        });

        TextInputLayout tilExpiry = view.findViewById(R.id.change_ingredient_expiry_til);
        TextInputLayout tilLocation = view.findViewById(R.id.change_ingredient_location_til);
        TextInputLayout tilQty = view.findViewById(R.id.change_ingredient_quantity_til);

        AlertDialog.Builder builder = new MaterialAlertDialogBuilder(getContext());
        final AlertDialog dialog = builder
                .setView(view)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", null).create();

        final String[] locationText = {""};
        dialog.setOnShowListener(dialogInterface -> {
            Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            button.setOnClickListener(l -> {
                String ingAmount = ((TextView) view.findViewById(R.id.change_ingredient_quantity)).getText().toString();
                locationText[0] = location.getText().toString();
                boolean hasError = false;

                if (locationText[0].equals("")) {
                    hasError = true;
                    tilLocation.setError("Location cannot be empty!");
                } else {
                    tilLocation.setErrorEnabled(false);
                }

                if (ingDate[0] == null) {
                    hasError = true;
                    tilExpiry.setError("Date cannot be empty!");
                } else {
                    tilExpiry.setErrorEnabled(false);
                }

                if (ingAmount.equals("")) {
                    tilQty.setError("Amount cannot be empty!");
                    hasError = true;
                } else {
                    tilQty.setErrorEnabled(false);
                }

                if (hasError) {
                    return;
                }
                listener.onSubmitPressed(new StoredIngredient(ingredient.getDescription(), Integer.parseInt(ingAmount), ingredient.getUnit(), ingredient.getCategory(), ingDate[0], locationText[0]));
                dialog.dismiss();
            });
        });

        return dialog;
    }
}
