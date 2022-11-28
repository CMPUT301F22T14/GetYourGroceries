/* MainActivity class. */
package com.example.getyourgroceries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.getyourgroceries.entity.IngredientStorage;
import com.example.getyourgroceries.entity.MealPlanStorage;
import com.example.getyourgroceries.entity.RecipeStorage;
import com.example.getyourgroceries.fragments.HomeScreenFragment;
import com.example.getyourgroceries.fragments.RecipeListFragment;

import com.example.getyourgroceries.fragments.IngredientListFragment;
import com.example.getyourgroceries.fragments.MealPlansFragment;
import com.example.getyourgroceries.fragments.ShoppingListFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

/**
 * Create an object to be the start point of the app.
 */
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView bottomNavigationView;
    IngredientListFragment ingredientListFragment = new IngredientListFragment();
    HomeScreenFragment homeScreenFragment = new HomeScreenFragment();
    RecipeListFragment recipeListFragment = new RecipeListFragment();
    MealPlansFragment mealPlansFragment = new com.example.getyourgroceries.fragments.MealPlansFragment();
    ShoppingListFragment shoppingListFragment = new com.example.getyourgroceries.fragments.ShoppingListFragment();

    /**
     * Create the app.
     *
     * @param savedInstanceState The saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IngredientStorage.getInstance().setupStorage(getBaseContext());
        RecipeStorage.getInstance().setupStorage(getBaseContext());
        MealPlanStorage.getInstance().setupStorage(getBaseContext());

        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home_icon);
    }

    /**
     * Create the appropriate fragment.
     *
     * @param item The navigation bar.
     * @return True on success.
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Call the appropriate method based on the user selection.
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
                getSupportFragmentManager().beginTransaction().replace(R.id.container, shoppingListFragment, "SHOPPING_LIST").commit();
                return true;
        }
        return false;
    }
}