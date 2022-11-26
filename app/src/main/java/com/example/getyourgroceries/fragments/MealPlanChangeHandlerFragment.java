/* IngredientChangeHandlerFragment class. */
package com.example.getyourgroceries.fragments;

import android.app.AlertDialog;
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
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.getyourgroceries.R;
import com.example.getyourgroceries.adapters.DayListAdapter;
import com.example.getyourgroceries.entity.IngredientStorage;
import com.example.getyourgroceries.entity.MealPlan;
import com.example.getyourgroceries.entity.MealPlanDay;
import com.example.getyourgroceries.entity.MealPlanStorage;
import com.example.getyourgroceries.entity.Recipe;
import com.example.getyourgroceries.entity.RecipeStorage;
import com.example.getyourgroceries.entity.ScaledRecipe;
import com.example.getyourgroceries.interfaces.OnMealPlanFragmentInteractionListener;

import java.util.ArrayList;
import java.util.Objects;

/**
 * The AddIngredientFragment is the class for the add ingredient screen.
 * AddIngredientFragment extends {@link Fragment} and implements {@link OnMealPlanFragmentInteractionListener}
 */
public class MealPlanChangeHandlerFragment extends Fragment implements OnMealPlanFragmentInteractionListener {
    private View view;
    ArrayList<MealPlanDay> days;
    ArrayAdapter<MealPlanDay> daysAdapter;
    MealPlan editMealPlan;

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

        // if returning from another page
        if (view != null){
            return view;
        }

        // Create the view.
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        view = inflater.inflate(R.layout.change_mealplan, container, false);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            editMealPlan = MealPlanStorage.getInstance().getMealPlan(getArguments().getInt("editMealPlan"));
            assert actionBar != null;
            actionBar.setTitle("Edit Meal Plan");
            days = editMealPlan.getMealPlanDays();
        } else {
            assert actionBar != null;
            actionBar.setTitle("Add Meal Plan");
            days = new ArrayList<>();
        }
        ListView dayListView = view.findViewById(R.id.day_list);

        daysAdapter = new DayListAdapter(requireActivity().getBaseContext(), days,requireActivity().getSupportFragmentManager());
        dayListView.setAdapter(daysAdapter);
        Button addDay = view.findViewById(R.id.add_day);
        addDay.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Add new day");

            // Set up the input
            final EditText input = new EditText(getContext());
            builder.setView(input);

            // Set up the buttons
            builder.setPositiveButton("OK", (dialog, which) -> daysAdapter.add(new MealPlanDay(input.getText().toString())));
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
            builder.show();
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

                RecipeStorage.getInstance().addRecipe(recipe, true);
                days.get(dayPosition).addRecipe(new ScaledRecipe(recipe, scale));
                daysAdapter.notifyDataSetChanged();
            }

            days.get(dayPosition).addRecipe(new ScaledRecipe(recipe, scale));
            daysAdapter.notifyDataSetChanged();
        });

        // cancelling scale also cancels recipe add
        scaleAlertBox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

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
