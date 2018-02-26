package dobrowol.styloweplywanie.teammanagement;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import dobrowol.styloweplywanie.R;
import dobrowol.styloweplywanie.teammanagement.trainingdetails.AddTrainingDataFragment;
import dobrowol.styloweplywanie.teammanagement.trainingdetails.AddTrainingSetFragment;
import dobrowol.styloweplywanie.utils.TrainingData;

/**
 * Created by dobrowol on 01.06.17.
 */

public class AddTrainingDetails extends FragmentActivity
implements AddTrainingDataFragment.OnTrainigDataAddedListener{
    private TrainingData trainingData;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainingdetails);
        if (savedInstanceState != null) {
            return;
        }
        Log.d("DUPA", "DUPA AddTrainingDetails");
        if (findViewById(R.id.fragment_trainingdetails) != null) {

            // Create a new Fragment to be placed in the activity layout
            AddTrainingDataFragment firstFragment = new AddTrainingDataFragment();
            AddTrainingSetFragment secondFragment = new AddTrainingSetFragment();
            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());
            secondFragment.setArguments(getIntent().getExtras());
            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_trainingdetails, firstFragment).commit();
        }
    }

    @Override
    public void onTrainingDataReturn(TrainingData trainingD) {
        Log.d("DUPA", "DUPA AddTrainingDetailsClick");
            trainingData = trainingD;
            AddTrainingSetFragment trainingSetFragment = new AddTrainingSetFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_trainingdetails, trainingSetFragment);
            transaction.addToBackStack(null);
            transaction.commit();

    }
}
