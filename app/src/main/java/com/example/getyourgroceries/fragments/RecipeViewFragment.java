package com.example.getyourgroceries.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
<<<<<<< Updated upstream
import android.widget.Spinner;
=======
import android.widget.ListView;
>>>>>>> Stashed changes
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
<<<<<<< Updated upstream
import androidx.constraintlayout.widget.ConstraintLayout;
=======
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
>>>>>>> Stashed changes
import androidx.fragment.app.Fragment;

import com.example.getyourgroceries.GlideApp;
import com.example.getyourgroceries.R;
<<<<<<< Updated upstream
=======
import com.example.getyourgroceries.adapters.RecipeIngredientAdapter;
>>>>>>> Stashed changes
import com.example.getyourgroceries.entity.Recipe;
import com.example.getyourgroceries.entity.RecipeStorage;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

public class RecipeViewFragment extends Fragment {
    private static final String TAG = "RecipeViewFrag";
    private Recipe viewRecipe;
    FirebaseStorage storage;
    ImageButton editButton;
    ViewGroup containerView;
    StorageReference imageRef;

    public RecipeViewFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_recipe, container, false);
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("View Recipe");
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        containerView = container;



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

<<<<<<< Updated upstream
=======
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();

>>>>>>> Stashed changes
        if (getArguments() != null) {
            viewRecipe = RecipeStorage.getInstance().getRecipe(getArguments().getInt("viewRecipe"));
        }

        //Populate fields if its a view
        ImageView image = requireActivity().findViewById(R.id.image);
        TextView title = requireActivity().findViewById(R.id.titleTextField);
        TextView prepTime = requireActivity().findViewById(R.id.prepTimeTextField);
        TextView category = requireActivity().findViewById(R.id.categoryTextField);
        TextView commentsText = requireActivity().findViewById(R.id.commentsTextField);

<<<<<<< Updated upstream
=======
        // Output all of the ingredients from Firebase.
        ingredientListView = requireActivity().findViewById(R.id.ingredientListView);
>>>>>>> Stashed changes

        // Set the values to the previous values.
        if (viewRecipe != null){
            title.setText(viewRecipe.getName());
            int prep_hours = viewRecipe.getPrepTime() / 60;
            int prep_min = viewRecipe.getPrepTime() % 60;
            String prepTimeText = prep_hours + "h " + prep_min + "m";
            prepTime.setText(prepTimeText);
            category.setText(viewRecipe.getRecipeCategory());
            commentsText.setText("Comments:\n" + viewRecipe.getComment());
            //servingsText.setText(String.valueOf(editRecipe.getNumOfServings()));

            ViewCompat.setNestedScrollingEnabled(ingredientListView, true);

            // get photo
            storage = FirebaseStorage.getInstance();
            try {
                imageRef = storage.getReference().child(viewRecipe.getPhoto());

                GlideApp.with(view)
                        .load(imageRef)
                        .into(image);
            } catch (IllegalArgumentException e) {
                image.setImageResource(R.drawable.placeholder);
            }
        }

        editButton = view.findViewById(R.id.edit_recipe_btn);

        editButton.setOnClickListener(v1 -> {
            Bundle bundle = new Bundle();
            //Recipe editRecipe = viewRecipe;
            bundle.putInt("editRecipe", getArguments().getInt("viewRecipe"));
            RecipeChangeHandlerFragment recipeChangeHandlerFragment = new RecipeChangeHandlerFragment();
            recipeChangeHandlerFragment.setArguments(bundle);
            requireActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out).replace(containerView.getId(), recipeChangeHandlerFragment, "EDIT_RECIPE").addToBackStack("EDIT_RECIPE").commit();
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