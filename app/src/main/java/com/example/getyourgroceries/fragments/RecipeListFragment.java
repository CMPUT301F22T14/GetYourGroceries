/* RecipeListFragment class. */
package com.example.getyourgroceries;

// Import statements.
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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

/**
 * Create an object to show the recipe list.
 */
public class RecipeListFragment extends Fragment {

    // Attributes.
    private static final String TAG = "RecipeListFragment";
    ArrayList<Recipe> recipeDataList;
    RecipeAdapter recipeAdapter;
    FirebaseFirestore db;
    ListView recipeList;

    /**
     * Empty constructor.
     */
    public RecipeListFragment(){

    }

    /**
     * Create the view.
     * @param inflater Object to connect an XML file.
     * @param container The parent view.
     * @param savedInstanceState The saved state.
     * @return The created view.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        recipeList = view.findViewById(R.id.recipe_list);
        db = FirebaseFirestore.getInstance();
        recipeDataList = new ArrayList<>();
        recipeAdapter = new RecipeAdapter(getContext(), recipeDataList);
        recipeList.setAdapter(recipeAdapter);
        final CollectionReference collectionReference = db.collection("Recipes");

        // Listen for updates from the database.
        collectionReference.addSnapshotListener((value, error) -> {
            recipeDataList.clear();
            assert value != null;
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
        });
        return view;
    }
}