package com.example.dobrowol.styloweplywanie.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dobrowol.styloweplywanie.R;

/**
 * Created by dobrowol on 25.01.18.
 */

public class StudentAchievementActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String KEY = "StudentName";
    private Button okBtn;
    private EditText distanceET;
    private EditText styleET;
    private EditText timeET;
    private EditText strokeCountEt;
    private EditText dateEt;
    private  String dataFile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentachievement);

        styleET = (EditText) findViewById(R.id.eT_achievement_style);
        distanceET = (EditText) findViewById(R.id.eT_achievement_distance);
        strokeCountEt = (EditText) findViewById(R.id.eT_achievement_move);
        timeET = (EditText) findViewById(R.id.eT_achievement_time);
        okBtn = (Button) findViewById(R.id.btn_ok_acievement);
        okBtn.setOnClickListener(this);
        dateEt = (EditText) findViewById(R.id.eT_achievement_date);

        //TeamDataUtils teamDataUtils = new TeamDataUtils(getApplicationContext());
        //teamDataUtils.clearCache();


        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        Intent intent = getIntent();
        if (intent != null & intent.hasExtra(KEY)) {
            dataFile = intent.getExtras().getString(KEY);
            //fetchStudentAchievement(intent.getExtras().getString(KEY));
        }

    }

    private void fetchStudentAchievement(String studentName) {

    }

    public static void startActivity(String studentdataFile, Context context) {
        Intent intent = new Intent(context, StudentAchievementActivity.class);
        intent.putExtra(KEY, studentdataFile);

        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        String style = styleET.getText().toString();
        Integer distance = Integer.valueOf(distanceET.getText().toString());
        Integer strokeCount = Integer.valueOf(strokeCountEt.getText().toString());

        CsvDataUtils csvDataUtils = new CsvDataUtils(getApplicationContext());
        StudentAchievement sa = new StudentAchievement();
        sa.time = timeET.getText().toString();
        sa.strokeCount = strokeCountEt.getText().toString();
        sa.distance = distanceET.getText().toString();
        sa.date = dateEt.getText().toString();
        csvDataUtils.saveStudentAchievement(sa, dataFile);

    }
}
