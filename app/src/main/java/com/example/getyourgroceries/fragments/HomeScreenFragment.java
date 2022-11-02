/* HomeScreenFragment class. */
package com.example.getyourgroceries;

// Import statements.
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Create an object to represent the home screen.
 */
public class HomeScreenFragment extends Fragment {

    /**
     * Empty constructor.
     */
    public HomeScreenFragment() {}

    /**
     * Create the view.
     * @param inflater Inflater to set an XML file.
     * @param container Containing view.
     * @param savedInstanceState The saved state.
     * @return The created view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment.
        return inflater.inflate(R.layout.fragment_home_screen, container, false);
    }
}