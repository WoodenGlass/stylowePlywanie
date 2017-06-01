package com.example.dobrowol.styloweplywanie.teammanagement;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.example.dobrowol.styloweplywanie.R;
import com.example.dobrowol.styloweplywanie.teammanagement.trainingdetails.AddTrainingDataFragment;

/**
 * Created by dobrowol on 01.06.17.
 */

public class AddTrainingDetails extends FragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainingdetails);
        if (savedInstanceState != null) {
            return;
        }

        // Create a new Fragment to be placed in the activity layout
        AddTrainingDataFragment firstFragment = new AddTrainingDataFragment();

        // In case this activity was started with special instructions from an
        // Intent, pass the Intent's extras to the fragment as arguments
        firstFragment.setArguments(getIntent().getExtras());

        // Add the fragment to the 'fragment_container' FrameLayout
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_trainingdetails, firstFragment).commit();

    }

}
