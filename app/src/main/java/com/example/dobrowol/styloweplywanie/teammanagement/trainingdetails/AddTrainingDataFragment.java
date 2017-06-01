package com.example.dobrowol.styloweplywanie.teammanagement.trainingdetails;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.dobrowol.styloweplywanie.R;
import com.example.dobrowol.styloweplywanie.utils.TrainingData;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       /* addTrainingDataButton = (ImageButton) container.findViewById(R.id.button_acceptTrainingData);
        addTrainingDataButton.setOnClickListener(this);

        trainingName = (EditText) container.findViewById(R.id.editText_trainingName);
        trainingTime = (EditText) container.findViewById(R.id.editText_trainingTime);*/
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_addtrainingdata, container, false);

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
                startAddTrainingSetActivity();
                break;
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
