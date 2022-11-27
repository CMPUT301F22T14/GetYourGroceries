/* HomeScreenFragment class. */
package com.example.getyourgroceries;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.getyourgroceries.entity.IngredientStorage;
import com.example.getyourgroceries.fragments.MainBottomSheet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Create an object to represent the home screen.
 */
public class HomeScreenFragment extends Fragment {
    /**
     * Empty constructor.
     */
    public HomeScreenFragment() {
    }

    /**
     * Create the view.
     *
     * @param inflater           Inflater to set an XML file.
     * @param container          Containing view.
     * @param savedInstanceState The saved state.
     * @return The created view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("Get Your Groceries");

        // Inflate the layout for this fragment.
        View v = inflater.inflate(R.layout.fragment_home_screen, container, false);
        TextView description = v.findViewById(R.id.home_description);
        description.setText("Plan your meals with ease and efficiency!");
        ImageView homelogo = v.findViewById(R.id.imageView3);
        FloatingActionButton quickaddButton = v.findViewById(R.id.quickadd_button);

        // make bottom drawer popup
        quickaddButton.setOnClickListener(l -> {
            MainBottomSheet modalBottomSheet = new MainBottomSheet();
            modalBottomSheet.show(requireActivity().getSupportFragmentManager(), MainBottomSheet.TAG);
        });
        return v;
    }
}