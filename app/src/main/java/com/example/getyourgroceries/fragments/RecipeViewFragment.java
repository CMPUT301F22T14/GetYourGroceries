package com.example.getyourgroceries.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.getyourgroceries.R;
import com.example.getyourgroceries.entity.Recipe;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class RecipeViewFragment extends Fragment {

    private static final String TAG = "RecipeViewFrag";
    private Recipe viewRecipe;
    FirebaseFirestore db;

    public RecipeViewFragment() { super();}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_view_recipe, container, false);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        return view;
    }



}
