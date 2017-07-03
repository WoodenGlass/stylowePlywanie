package com.example.dobrowol.styloweplywanie.teammanagement.trainingdetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.dobrowol.styloweplywanie.R;
import com.example.dobrowol.styloweplywanie.teammanagement.AddTrainingActivity;
import com.example.dobrowol.styloweplywanie.utils.SwimmingStyles;
import com.example.dobrowol.styloweplywanie.utils.TrainingData;
import com.example.dobrowol.styloweplywanie.utils.TrainingSet;
import com.example.dobrowol.styloweplywanie.utils.TrainingSetAdapter;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by dobrowol on 10.04.17.
 */

public class AddTrainingSetFragment extends Fragment implements View.OnClickListener, TrainingSetAdapter.TrainingSetSelectedListener {
    public static final String RETURNED_DATA_KEY = "TrainingDataSet";
    private Date trainingDate;

    private TrainingSetAdapter adapter;
    private FloatingActionButton addButton;
    private ImageButton addTraining;

    private ArrayList<TrainingSet> trainingSets;
    private TrainingData trainingData;

    RecyclerView setsView;

    private EditText distance;
    private EditText description;
    private AutoCompleteTextView style;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       /* setsView = (RecyclerView) container.findViewById(R.id.training_sets_view);
        distance = (EditText) container.findViewById(R.id.editText_distance);
        description = (EditText) container.findViewById(R.id.editText_description);
        style = (AutoCompleteTextView) container.findViewById(R.id.autoComplete_style);
        addButton = (FloatingActionButton) container.findViewById(R.id.floatingActionButton_trainingSet);
        addTraining = (ImageButton) container.findViewById(R.id.tick_button);
       /* ;*/
        return inflater.inflate(R.layout.activity_addtrainingset, container, false);

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       /* adapter = new TrainingSetAdapter(this);

        setsView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        setsView.setAdapter(adapter);
        trainingSets = new ArrayList<>();
        /*Intent intent = getIntent();
        if (intent != null & intent.hasExtra(AddTrainingActivity.TRAINING_SET_KEY)) {
            trainingDate =  (Date) intent.getExtras().getSerializable(AddTrainingActivity.TRAINING_SET_KEY);
            trainingData = (TrainingData) intent.getExtras().getSerializable(AddTrainingActivity.TRAINING_DATA);
        }*/

/*
        adapter.setItems(trainingSets);

        addButton.setOnClickListener(this);
        addTraining.setOnClickListener(this);

        String[] swimmingStyles = swimmingStylesNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getActivity(),android.R.layout.simple_list_item_1,swimmingStyles);
        style.setAdapter(adapter);*/

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_addtrainingset);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.floatingActionButton_trainingSet:
                int dist = Integer.parseInt(distance.getText().toString());
                String desc = description.getText().toString();
                SwimmingStyles st = SwimmingStyles.valueOf(style.getText().toString());
                TrainingSet set = new TrainingSet(dist, st, desc);
                trainingSets.add(set);
                adapter.setItems(trainingSets);
                clearEditTexts();
                break;
            case R.id.tick_button:
                Intent returnIntent = new Intent();
                Bundle bundle = new Bundle();
                trainingData.trainingSets = trainingSets;
                bundle.putSerializable(AddTrainingActivity.TRAINING_DATA,trainingData);
                returnIntent.putExtras(bundle);
                //setResult(Activity.RESULT_OK,returnIntent);
                //finish();
        }
    }

    private void clearEditTexts() {
        style.setText("");
        distance.setText("");
        description.setText("");
    }

    @Override
    public void onItemSelected(TrainingSet item) {

    }
}
