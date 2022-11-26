package com.example.getyourgroceries.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.getyourgroceries.GlideApp;
import com.example.getyourgroceries.R;
import com.example.getyourgroceries.adapters.DayIngredientListAdapter;
import com.example.getyourgroceries.adapters.RecipeIngredientAdapter;
import com.example.getyourgroceries.entity.Ingredient;
import com.example.getyourgroceries.entity.MealPlanDay;
import com.example.getyourgroceries.entity.MealPlanStorage;
import com.example.getyourgroceries.entity.Recipe;
import com.example.getyourgroceries.entity.RecipeStorage;
import com.example.getyourgroceries.interfaces.OnFragmentInteractionListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class RecipeChangeHandlerFragment extends Fragment implements OnFragmentInteractionListener {
    private ArrayList<Ingredient> ingredientList;
    private RecipeIngredientAdapter ingredientAdapter;
    private Recipe editRecipe;
    FirebaseStorage storage;
    StorageReference imageRef;
    StorageReference newImageRef;
    private static final String TAG = "RecipeChangeHandlerFrag";
    Bitmap myBitmap;
    ImageView image;
    boolean gotImage = false;

    Dialog photoDialog;

    public interface OnMealPlanFragmentInteractionListener {
        void onSubmitPressed(Recipe newRecipe, int dayPosition);
    }
    /**
     * Fragment constructor to initialize its database class
     */
    public RecipeChangeHandlerFragment() {
        super();

    }

    /**
     * Display logic when fragment is loaded
     *
     * @param inflater           loads the fragment
     * @param container          underlying fragment container
     * @param savedInstanceState information passed in
     * @return view to display
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_change_handler, container, false);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        return view;
    }

    /**
     * The onViewCreated method will be called once the view has been created.
     *
     * @param view               The created view.
     * @param savedInstanceState The saved state.
     */
    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        NestedScrollView addIngredientLayout = requireActivity().findViewById(R.id.change_recipe_layout);
        addIngredientLayout.setImportantForAutofill(View.IMPORTANT_FOR_AUTOFILL_NO);

        FragmentManager fmManager = requireActivity().getSupportFragmentManager();

        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();

        // add recipe to recipe list
        // add recipe to day
        // edit recipe

        // add recipe to recipe list
        // add recipe to day
        // edit recipe

        if (getArguments() != null) {
            if (getArguments().containsKey("dayAdd")) {
                actionBar.setTitle("Add Recipe to Meal Plan");
                ingredientList = new ArrayList<>();
            } else if (getArguments().containsKey("editRecipe")) {
                editRecipe = RecipeStorage.getInstance().getRecipe(getArguments().getInt("editRecipe"));
                actionBar.setTitle("Edit Recipe");
                ingredientList = editRecipe.getIngredientList();
            }
        } else {
            actionBar.setTitle("Add Recipe");
            ingredientList = new ArrayList<>();
        }

        ingredientAdapter = new RecipeIngredientAdapter(requireActivity().getBaseContext(), ingredientList);

        // Set up category spinner.
        Map<String, String> categoryIDs = new HashMap<>();
        ArrayList<String> categories = new ArrayList<>();
        categories.add("+ Save New Category");
        categories.add("- Delete Saved Category");
        CollectionReference categoryCollection = FirebaseFirestore.getInstance().collection("RecipeCategory");
        categoryCollection.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    categories.add(Objects.requireNonNull(document.getData().get("Category")).toString());
                    categoryIDs.put(Objects.requireNonNull(document.getData().get("Category")).toString(), document.getId());
                }
            }
        });
        AutoCompleteTextView category = requireActivity().findViewById(R.id.change_recipe_category);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner, categories);
        if (editRecipe != null) {
            category.setText(editRecipe.getRecipeCategory());
        }
        category.setAdapter(categoryAdapter);
        category.setThreshold(200);
        category.setOnItemClickListener(((adapterView, view1, i, l) -> {
            if (Objects.equals(categories.get(i), "+ Save New Category")) {
                category.setText("");
                final EditText newCategoryInput = new EditText(getContext());
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder
                        .setTitle("Add Category")
                        .setMessage("Enter a new category:")
                        .setCancelable(true)
                        .setView(newCategoryInput)
                        .setPositiveButton("OK", (dialog, which) -> {
                            String newCategory = newCategoryInput.getText().toString();
                            category.setText(newCategory);
                            Map<String, Object> insertCategory = new HashMap<>();
                            insertCategory.put("Category", newCategory);
                            categoryCollection.document().set(insertCategory);
                            dialog.dismiss();
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
                        .create()
                        .show();
            } else if (categories.get(i).equals("- Delete Saved Category")) {
                category.setText("");
                final EditText deleteCategoryInput = new EditText(getContext());
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder
                        .setTitle("Delete Category")
                        .setMessage("Delete an existing category:")
                        .setCancelable(true)
                        .setView(deleteCategoryInput)
                        .setPositiveButton("OK", (dialog, which) -> {
                            String deleteCategory = deleteCategoryInput.getText().toString();
                            String id = categoryIDs.get(deleteCategory);
                            if (id != null) {
                                categoryCollection.document(id).delete();
                            }
                            dialog.dismiss();
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
                        .create()
                        .show();
            }
        }));

        //Populate fields if its an edit
        TextInputEditText descriptionText = view.findViewById(R.id.change_recipe_description);
        TextInputEditText prepTimeText = view.findViewById(R.id.change_recipe_prep_time);
        TextInputEditText servingsText = view.findViewById(R.id.change_recipe_servings);
        TextInputEditText commentsText = view.findViewById(R.id.change_recipe_comments);
        image = view.findViewById(R.id.recipeImage);
        ListView ingredientListView = view.findViewById(R.id.add_ingredients_recipe);
        ingredientListView.setAdapter(ingredientAdapter);

        Button addRecipePhotoButton = view.findViewById(R.id.change_recipe_add_photo);

        ingredientListView.setOnItemClickListener((adapterView, view12, i, l) -> new AddIngredientRecipeFragment(ingredientList.get(i), i).show(requireActivity().getSupportFragmentManager(), "EDIT_INGREDIENT_RECIPE"));

        ingredientListView.setOnItemLongClickListener((adapterView, view2, i, l) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Would you like to delete this ingredient?");
            builder.setTitle("Delete Ingredient");
            builder.setCancelable(true);
            builder.setPositiveButton("Yes", (dialog, which) -> {
                Ingredient recipe = (Ingredient) ingredientListView.getItemAtPosition(i);
                ingredientList.remove(recipe);
                ingredientAdapter.notifyDataSetChanged();
            });
            builder.setNegativeButton("No", (dialog, which) -> dialog.cancel());
            AlertDialog alert = builder.create();
            alert.show();
            return true;
        });

        ViewCompat.setNestedScrollingEnabled(ingredientListView, true);

        // Set the values to the previous values.
        if (editRecipe != null) {
            descriptionText.setText(editRecipe.getName());
            prepTimeText.setText(String.valueOf(editRecipe.getPrepTime()));
            servingsText.setText(String.valueOf(editRecipe.getNumOfServings()));
            commentsText.setText(editRecipe.getComment());
            addRecipePhotoButton.setText("Change Photo");

            // get photo
            storage = FirebaseStorage.getInstance();
            try {
                imageRef = storage.getReference().child(editRecipe.getPhoto());

                GlideApp.with(view)
                        .load(imageRef)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(image);
            } catch (IllegalArgumentException e) {
                image.setImageResource(R.drawable.placeholder);
            }
        }

        // Get the text layouts.
        TextInputLayout tilName = view.findViewById(R.id.change_recipe_description_til);
        TextInputLayout tilPrepTime = view.findViewById(R.id.change_recipe_prep_time_til);
        TextInputLayout tilServings = view.findViewById(R.id.change_recipe_servings_til);
        TextInputLayout tilCategory = view.findViewById(R.id.change_recipe_category_til);

        Button addIngredientBtn = view.findViewById(R.id.change_recipe_add_ingredient);
        addIngredientBtn.setOnClickListener(view12 -> new AddIngredientRecipeFragment().show(requireActivity().getSupportFragmentManager(), "ADD_INGREDIENT_RECIPE"));

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{
                    Manifest.permission.CAMERA
            }, 100);
        }
        addRecipePhotoButton.setOnClickListener(v -> PhotoPickerDialog());

        // Add the recipe.
        Button confirmButton = view.findViewById(R.id.change_recipe_confirm);
        confirmButton.setOnClickListener(view1 -> {
            // Get the data.
            String description = Objects.requireNonNull(descriptionText.getText()).toString();
            String prepTime = Objects.requireNonNull(prepTimeText.getText()).toString();
            String servings = Objects.requireNonNull(servingsText.getText()).toString();
            String categoryText = category.getText().toString();
            String comments = Objects.requireNonNull(commentsText.getText()).toString();

            // Error checking.
            int error = 0;
            if (description.equals("")) {
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

            // add photo to firebase
            String new_photo = "";
            if (gotImage) {
                new_photo = uploadPhoto(description);
            }


            // If in edit mode, update the attributes.
            if (editRecipe != null) {
                editRecipe.setName(description);
                editRecipe.setPrepTime(Integer.parseInt(prepTime));
                editRecipe.setNumOfServings(Integer.parseInt(servings));
                editRecipe.setRecipeCategory(categoryText);
                editRecipe.setComment(comments);
                if (gotImage) {
                    editRecipe.setPhoto(new_photo);
                }
                RecipeStorage.getInstance().updateRecipe(editRecipe);
            } else {
                Recipe newRecipe = new Recipe(description, Integer.parseInt(prepTime), Integer.parseInt(servings), categoryText, comments, new_photo, ingredientList);
                RecipeStorage.getInstance().addRecipe(newRecipe, true);

                //if it was called from mealPlan page, call the function to add it to actual mealplan
                if (getArguments().containsKey("dayAdd")){
                    OnMealPlanFragmentInteractionListener frag = (OnMealPlanFragmentInteractionListener) fmManager.findFragmentByTag("MEAL_PLAN_EDIT");
                    frag.onSubmitPressed(newRecipe, getArguments().getInt("dayEdit"));
                }

            }
            fmManager.popBackStack();
            //fmManager.popBackStack();
        });
    }

    private String uploadPhoto(String description) {
        image.setDrawingCacheEnabled(true);
        image.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        String imageRefStr = "recipes/" + description.replaceAll("[^a-zA-Z]+", "") + ".jpg";
        newImageRef = storage.getReference().child(imageRefStr.toLowerCase());

        UploadTask uploadTask = newImageRef.putBytes(data);
        uploadTask.addOnFailureListener(exception -> Log.d(TAG, "onFailure: upload failed")).addOnSuccessListener(taskSnapshot -> Log.d(TAG, "onSuccess: file uploaded successfully"));

        return imageRefStr.toLowerCase();
    }

    public void PhotoPickerDialog() {

        storage = FirebaseStorage.getInstance();

        photoDialog = new Dialog(getContext());
        photoDialog.setContentView(R.layout.recipe_image_dialog);
        photoDialog.setTitle("Choose Image");

        ImageButton camera = photoDialog.findViewById(R.id.cameraButton);
        ImageButton gallery = photoDialog.findViewById(R.id.galleryButton);

        camera.setEnabled(true);
        gallery.setEnabled(true);

        camera.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 100);
        });

        gallery.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 3);
        });

        gotImage = true;
        photoDialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 || requestCode == 3) {
            if (resultCode == Activity.RESULT_OK) {
                assert data != null;
                myBitmap = (Bitmap) data.getExtras().get("data");
                image.setImageBitmap(myBitmap);
            }  // do nothing

        }
    }

    /**
     * The onOptionsItemSelected method will go to the previous fragment when the back button is pressed.
     *
     * @param item The item selected.
     * @return On success, true.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return requireActivity().getSupportFragmentManager().popBackStackImmediate();
    }

    /**
     * Executes when the user hits "ok" on the add ingredient dialog
     *
     * @param newIngredient item to add to recipe
     */
    @Override
    public void onOkPressed(Ingredient newIngredient) {
        if (!ingredientList.contains(newIngredient)) {
            ingredientList.add(newIngredient);
            ingredientAdapter.notifyDataSetChanged() ;
        }
    }

    /**
     * Executes when the user hits "ok" on the edit ingredient dialog
     * @param newIngredient updated ingredient info
     * @param index position in ingredient list
     */
    @Override
    public void onItemPressed(Ingredient newIngredient, int index) {
        ingredientList.set(index, newIngredient);
        ingredientAdapter.notifyDataSetChanged();
    }
    /**
     * Executes when the user hits "ok" on the edit ingredient dialog
     * @param newIngredient updated ingredient info
     * @param dayIngredientListAdapter Adapter
     */
    @Override
    public void onMealOkPressed(Ingredient newIngredient, DayIngredientListAdapter dayIngredientListAdapter) { // FOR MEAL PLANS
    }

}