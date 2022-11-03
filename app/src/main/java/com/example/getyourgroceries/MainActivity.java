/* MainActivity class. */
package com.example.getyourgroceries;

// Import statements.
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import android.view.Window;

import com.example.getyourgroceries.fragments.RecipeListFragment;
import com.example.getyourgroceries.IngredientListFragment;
import com.example.getyourgroceries.HomeScreenFragment;
import com.example.getyourgroceries.MealPlansFragment;
import com.example.getyourgroceries.ShoppingListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.Objects;

/**
 * Create an object to be the start point of the app.
 */
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    // Attributes.
    BottomNavigationView bottomNavigationView;
    IngredientListFragment ingredientListFragment = new com.example.getyourgroceries.IngredientListFragment();
    HomeScreenFragment homeScreenFragment = new com.example.getyourgroceries.HomeScreenFragment();
    RecipeListFragment recipeListFragment = new RecipeListFragment();
    MealPlansFragment mealPlansFragment = new com.example.getyourgroceries.MealPlansFragment();
    ShoppingListFragment shoppingListFragment = new com.example.getyourgroceries.ShoppingListFragment();

    /**
     * Create the app.
     * @param savedInstanceState The saved state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_main);

//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home_icon);
    }

    /**
     * Create the appropriate fragment.
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
                getSupportFragmentManager().beginTransaction().replace(R.id.container, shoppingListFragment).commit();
                return true;
        }
        return false;
    }
}