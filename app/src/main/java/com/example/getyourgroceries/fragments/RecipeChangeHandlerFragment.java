package com.example.getyourgroceries.fragments;

import static com.example.getyourgroceries.entity.IngredientStorage.ingredientAdapter;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.getyourgroceries.R;
import com.example.getyourgroceries.control.IngredientDB;
import com.example.getyourgroceries.entity.Ingredient;
import com.example.getyourgroceries.entity.Recipe;
import com.example.getyourgroceries.entity.StoredIngredient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class RecipeChangeHandlerFragment extends Fragment {

    private static final String TAG = "RecipeChangeHandlerFrag";

    private Recipe editRecipe;
    FirebaseFirestore db;

    public RecipeChangeHandlerFragment() { super();}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_recipe_change_handler, container, false);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        return view;
    }

    /**
     * The onViewCreated method will be called once the view has been created.
     * @param view The created view.
     * @param savedInstanceState The saved state.
     */
    @SuppressLint("SimpleDateFormat")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // Initialization.
        ConstraintLayout addIngredientLayout = requireActivity().findViewById(R.id.change_recipe_layout);
        addIngredientLayout.setImportantForAutofill(View.IMPORTANT_FOR_AUTOFILL_NO);

        if (getArguments() != null) {
            editRecipe = (Recipe) getArguments().getSerializable("editRecipe");
        }

        // Set up category spinner.
        Spinner category = requireActivity().findViewById(R.id.change_recipe_category);
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Enter A Category");
        categories.add("Baking");
        categories.add("Frying");
        categories.add("Microwaving");
        categories.add("Cooking");
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner, categories) {

            /**
             * The isEnabled method will disallow the first dropdown choice.
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


        //Populate fields if its an edit
        TextView nameText = requireActivity().findViewById(R.id.change_recipe_name);
        TextView prepTimeText = requireActivity().findViewById(R.id.change_recipe_prep_time);
        TextView servingsText = requireActivity().findViewById(R.id.change_recipe_servings);
        TextView commentsText = requireActivity().findViewById(R.id.change_recipe_comments);


        // Set the values to the previous values.
        if (editRecipe != null){
            nameText.setText(editRecipe.getName());
            prepTimeText.setText(String.valueOf(editRecipe.getPrepTime()));
            servingsText.setText(String.valueOf(editRecipe.getNumOfServings()));
            category.setSelection(categoryAdapter.getPosition(editRecipe.getRecipeCategory()));
            commentsText.setText(editRecipe.getComment());
        }

        // Get the text layouts.
        TextInputLayout tilName = requireActivity().findViewById(R.id.change_recipe_name_til);
        TextInputLayout tilPrepTime = requireActivity().findViewById(R.id.change_recipe_prep_time_til);
        TextInputLayout tilServings = requireActivity().findViewById(R.id.change_recipe_servings_til);
        TextInputLayout tilCategory = requireActivity().findViewById(R.id.change_recipe_category_til);
        TextInputLayout tilComments = requireActivity().findViewById(R.id.change_recipe_comments_til);

        Button addIngredientBtn = view.findViewById(R.id.add_ingredient_btn);
        addIngredientBtn.setOnClickListener(view12 -> new AddIngredientRecipeFragment().show(getActivity().getSupportFragmentManager(), "ADD_INGREDIENT"));


        // Add the recipe.
        Button confirmButton = requireActivity().findViewById(R.id.change_recipe_confirm);
        confirmButton.setOnClickListener(view1 ->{

            // Get the data.
            String name = nameText.getText().toString();
            String prepTime = prepTimeText.getText().toString();
            String servings = servingsText.getText().toString();
            String categoryText = category.getSelectedItem().toString();
            String comments = commentsText.getText().toString();

            // Error checking.
            int error = 0;
            if (name.equals("")) {
                tilName.setError("Name cannot be empty!");
                error = 1;
            } else {
                tilName.setErrorEnabled(false);
            }
            if (prepTime.equals("")) {
                tilPrepTime.setError("Prep Time must be an integer!");
                error = 1;
            } else {
                tilPrepTime.setErrorEnabled(false);
            }
            if (servings.equals("")) {
                tilServings.setError("Servings must be a positive integer!");
                error = 1;
            } else {
                tilServings.setErrorEnabled(false);
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

            db = FirebaseFirestore.getInstance();
            final CollectionReference collectionReference = db.collection("Recipes");

            Map<String, Object> data = new HashMap<>();

            data.put("name", name);
            data.put("prepTime", Integer.parseInt(prepTime));
            data.put("numOfServings", Integer.parseInt(servings));
            data.put("recipeCategory", categoryText);
            data.put("comment", comments);
            data.put("photo", "recipes/apple.jpg");

            // If in edit mode, update the attributes.
            if (editRecipe != null){

                collectionReference.document(editRecipe.getId())
                        .set(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d(TAG, "onSuccess: object updated!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                              @Override
                              public void onFailure(@NonNull Exception e) {
                                  Log.w(TAG, "onFailure: error updating document", e);
                              }
                        });

                                /*
                                 * get id from bundle passer, then update based on id
                                 * */
            } else {

                collectionReference
                        .add(data)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "onSuccess: ID =" + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "onFailure: Error adding document", e);
                            }
                        });


            }
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