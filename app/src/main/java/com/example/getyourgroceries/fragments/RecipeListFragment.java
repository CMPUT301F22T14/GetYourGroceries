package com.example.getyourgroceries;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.getyourgroceries.control.RecipeDB;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.getyourgroceries.entity.Recipe;


import java.util.ArrayList;

public class RecipeListFragment extends Fragment {

    private static final String TAG = "RecipeListFragment";
    ArrayList<Recipe> recipeDataList;
    RecipeAdapter recipeAdapter;
    FirebaseFirestore db;
    ListView recipeList;

    public RecipeListFragment(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        recipeList = view.findViewById(R.id.recipe_list);


        db = FirebaseFirestore.getInstance();

        recipeDataList = new ArrayList<>();
        recipeAdapter = new RecipeAdapter(getContext(), recipeDataList);
        recipeList.setAdapter(recipeAdapter);

        final CollectionReference collectionReference = db.collection("Recipes");

        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                recipeDataList.clear();
                for(QueryDocumentSnapshot doc: value) {
                    Log.d(TAG, (String) doc.getData().get("name"));
                    String name = (String) doc.getData().get("name");
                    int prepTime = Integer.parseInt(String.valueOf(doc.getData().get("prepTime")));
                    int servings = Integer.parseInt(String.valueOf(doc.getData().get("numOfServings")));
                    String category = (String) doc.getData().get("recipeCategory");
                    String comment = (String) doc.getData().get("comment");
                    String photo = (String) doc.getData().get("photo");
                    Log.d(TAG, photo);

                    recipeDataList.add(new Recipe(name, prepTime, servings, category, comment, "recipes/apple.jpg"));
                }

                recipeAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }
}