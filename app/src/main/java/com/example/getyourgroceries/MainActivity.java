package com.example.getyourgroceries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home_icon);
    }
    IngredientListFragment ingredientListFragment = new IngredientListFragment();
    HomeScreenFragment homeScreenFragment = new HomeScreenFragment();
    RecipeListFragment recipeListFragment = new RecipeListFragment();
    MealPlansFragment mealPlansFragment = new MealPlansFragment();
    ShoppingListFragment shoppingListFragment = new ShoppingListFragment();
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home_icon:
                Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, homeScreenFragment).commit();
                return true;

            case R.id.ingredients_icon:
                Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, ingredientListFragment).commit();
                return true;

            case R.id.recipe_icon:
                Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, recipeListFragment).commit();
                return true;

            case R.id.meal_icon:
                Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, mealPlansFragment).commit();
                return true;
            case R.id.shopping_icon:
                Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
                getSupportFragmentManager().beginTransaction().replace(R.id.container, shoppingListFragment).commit();
                return true;
        }
        return false;
    }
}