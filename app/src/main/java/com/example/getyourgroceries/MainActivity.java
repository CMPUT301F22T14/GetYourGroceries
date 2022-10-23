package com.example.getyourgroceries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home_icon);
    }
    IngredientList ingredientList = new IngredientList();
    HomeScreen homeScreen = new HomeScreen();
    RecipeList recipeList = new RecipeList();
    MealPlans mealPlans = new MealPlans();
    ShoppingList shoppingList = new ShoppingList();
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home_icon:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, homeScreen).commit();
                return true;

            case R.id.ingredients_icon:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, ingredientList).commit();
                return true;

            case R.id.recipe_icon:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, recipeList).commit();
                return true;

            case R.id.meal_icon:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, mealPlans).commit();
                return true;
            case R.id.shopping_icon:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, shoppingList).commit();
                return true;
        }
        return false;
    }
}