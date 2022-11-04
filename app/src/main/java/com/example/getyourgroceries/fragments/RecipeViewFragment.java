package com.example.getyourgroceries.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.getyourgroceries.R;
import com.example.getyourgroceries.entity.Recipe;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RecipeViewFragment extends Fragment {

    private static final String TAG = "RecipeViewFrag";
    private Recipe viewRecipe;
    FirebaseFirestore db;
    ImageButton editButton;
    ViewGroup containerView;

    public RecipeViewFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_recipe, container, false);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        containerView= container;



        return view;
    }

    /**
     * The onViewCreated method will be called once the view has been created.
     *
     * @param view               The created view.
     * @param savedInstanceState The saved state.
     */
    @SuppressLint("SimpleDateFormat")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // Initialization.
        ConstraintLayout viewIngredientLayout = requireActivity().findViewById(R.id.view_recipe_layout);
        viewIngredientLayout.setImportantForAutofill(View.IMPORTANT_FOR_AUTOFILL_NO);

        if (getArguments() != null) {
            viewRecipe = (Recipe) getArguments().getSerializable("viewRecipe");
        }

        //Populate fields if its a view
        ImageView image = requireActivity().findViewById(R.id.image);
        TextView title = requireActivity().findViewById(R.id.titleTextField);
        TextView prepTime = requireActivity().findViewById(R.id.prepTimeTextField);
        TextView category = requireActivity().findViewById(R.id.categoryTextField);
        TextView commentsText = requireActivity().findViewById(R.id.commentsTextField);


        // Set the values to the previous values.
        if (viewRecipe != null){
            title.setText(viewRecipe.getName());
            prepTime.setText(String.valueOf(viewRecipe.getPrepTime()));
            category.setText(String.valueOf(viewRecipe.getRecipeCategory()));
            commentsText.setText(viewRecipe.getComment());
            //servingsText.setText(String.valueOf(editRecipe.getNumOfServings()));
        }

        editButton = view.findViewById(R.id.edit_recipe_btn);

        editButton.setOnClickListener(v1 -> {
            Bundle bundle = new Bundle();
            Recipe editRecipe = viewRecipe;
            bundle.putSerializable("editRecipe", editRecipe);
            RecipeChangeHandlerFragment recipeChangeHandlerFragment = new RecipeChangeHandlerFragment();
            recipeChangeHandlerFragment.setArguments(bundle);
            requireActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out).replace(containerView.getId(), recipeChangeHandlerFragment).addToBackStack(null).commit();
        });


    }


}