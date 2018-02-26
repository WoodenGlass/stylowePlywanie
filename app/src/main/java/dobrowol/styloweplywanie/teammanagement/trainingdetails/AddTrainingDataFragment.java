package dobrowol.styloweplywanie.teammanagement.trainingdetails;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import dobrowol.styloweplywanie.R;
import dobrowol.styloweplywanie.utils.TrainingData;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by dobrowol on 08.05.17.
 */

public class AddTrainingDataFragment extends Fragment implements View.OnClickListener {
    ImageButton addTrainingDataButton;
    private static final int GET_TRAINING_SETS_REQUEST = 1;
    public static final String TRAINING_SET_KEY = "TRAINING_DATE";
    public static String TRAINING_DATA="TRAINING_DATA";
    TrainingData trainingData;
    EditText trainingName;
    EditText trainingTime;
    OnTrainigDataAddedListener callback;

    public interface OnTrainigDataAddedListener {
        /** Called by HeadlinesFragment when a list item is selected */
        public void onTrainingDataReturn(TrainingData trainingData);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.activity_addtrainingdata, container, false);
        addTrainingDataButton = (ImageButton) ll.findViewById(R.id.button_acceptTrainingData);
        addTrainingDataButton.setOnClickListener(this);
        Log.d("DUPA", "DUPA AddTrainingDataFragment");

        trainingName = (EditText) ll.findViewById(R.id.editText_trainingName);
        trainingTime = (EditText) ll.findViewById(R.id.editText_trainingTime);
        trainingData = new TrainingData();
        // Inflate the layout for this fragment
        return ll;

    }


    private void startAddTrainingSetActivity() {

        //Intent intent = new Intent(this, AddTrainingSetActivity.class);
        ///String trainingDate = date.getDay() + "/" + date.getMonth() + "/" + date.getYear();
        //Bundle bundle = new Bundle();
        //bundle.putSerializable(TRAINING_DATA, trainingData);
        //bundle.putSerializable(TRAINING_SET_KEY, date.getDate());
        //intent.putExtras(bundle);
        //startActivityForResult(intent, GET_TRAINING_SETS_REQUEST);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button_acceptTrainingData:
                trainingData.name = trainingName.getText().toString();
                String time = trainingTime.getText().toString();
                DateFormat formatter = new SimpleDateFormat("hh:mm:ss a");
                try {
                    trainingData.time = formatter.parse(time);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Log.d("DUPA", "DUPA");
                callback.onTrainingDataReturn(trainingData);
                break;
        }

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity;

        if (context instanceof Activity) {
            activity = (Activity) context;

            try {
                callback = (OnTrainigDataAddedListener) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString()
                        + " must implement OnTrainigDataAddedListener");
            }
        }

    }
    /*protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == GET_TRAINING_SETS_REQUEST) {
            if(resultCode == Activity.RESULT_OK){
                ArrayList<TrainingSet> trainingSets = (ArrayList<TrainingSet>) data.getExtras().getSerializable(TRAINING_DATA);
                trainingData.trainingSets = trainingSets;
                //trainingAdapter.setSamples(trainings);
            }
        }
    }*/
}
