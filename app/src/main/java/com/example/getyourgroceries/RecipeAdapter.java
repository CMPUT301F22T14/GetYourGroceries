package com.example.getyourgroceries;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.getyourgroceries.entity.Recipe;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.LogDescriptor;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class RecipeAdapter extends ArrayAdapter<Recipe> {
    private ArrayList<Recipe> recipes;
    private Context context;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private StorageReference imageRef;

    private static final String TAG = "RecipeList";

    public RecipeAdapter(Context context, ArrayList<Recipe> recipes) {
        super(context, 0, recipes);
        this.recipes = recipes;
        this.context = context;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup
            parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.recipe_item, parent, false);
        }
        Recipe recipe = recipes.get(position);


        TextView recipeName = view.findViewById(R.id.recipe_name);
        TextView recipePrepTime = view.findViewById(R.id.recipe_prep_time);
        TextView recipeCategory = view.findViewById(R.id.recipe_category);
        ImageView recipePhoto = view.findViewById(R.id.recipe_photo);

        int prep_hours = recipe.getPrepTime() / 60;
        int prep_min = recipe.getPrepTime() % 60;
        String prepTimeText = "Prep Time: " + prep_hours + "h " + prep_min + "m";

        String categoryText = "Category: " + recipe.getRecipeCategory();

        recipeName.setText(recipe.getName());
        recipePrepTime.setText(prepTimeText);
        recipeCategory.setText(categoryText);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        imageRef = storageRef.child(recipe.getPhoto());
        Log.d(TAG, imageRef.getPath());

        Glide.with(context)
                .load(imageRef)
                .into(recipePhoto);
        Log.d(TAG, recipePhoto.toString());


        /*
        try {
            final File localPic = File.createTempFile(recipe.getName().toLowerCase(), ".jpg");
            storageRef.getFile(localPic)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap = BitmapFactory.decodeFile(localPic.getAbsolutePath());
                            recipePhoto.setImageBitmap(bitmap);
                            Log.d(TAG, "onSuccess: Retrieved picture");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: image not retrieved");
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        //recipePhoto.setClipToOutline(true);

        return view;
    }

}
