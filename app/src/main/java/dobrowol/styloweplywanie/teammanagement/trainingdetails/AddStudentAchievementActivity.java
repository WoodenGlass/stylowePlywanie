package dobrowol.styloweplywanie.teammanagement.trainingdetails;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import dobrowol.styloweplywanie.R;
import dobrowol.styloweplywanie.teammanagement.AddStudentActivity;
import dobrowol.styloweplywanie.teammanagement.TrainingManager;
import dobrowol.styloweplywanie.utils.ConvertUtils;
import dobrowol.styloweplywanie.utils.CsvDataUtils;
import dobrowol.styloweplywanie.utils.DatePickerFragment;
import dobrowol.styloweplywanie.utils.StudentAchievement;
import dobrowol.styloweplywanie.utils.StudentData;
import dobrowol.styloweplywanie.utils.TeamData;
import dobrowol.styloweplywanie.utils.TeamDataUtils;

public class AddStudentAchievementActivity extends AppCompatActivity implements View.OnClickListener, DatePickerFragment.OnDateSelectedListener, View.OnFocusChangeListener {

    public static final String KEY="StudentAchievement";
    private String dataFile;
    private Spinner etStyle;
    private Spinner etDistance;
    private EditText etDate;
    private EditText etTime;
    private EditText etPoolSize;
    private EditText etStrokeCount;
    private Spinner spinnerStudents;
    private FloatingActionButton fbAccept;
    private TeamData teamData;
    private Map studentToStudentData;
    private ArrayList<String> students;
    private String currentStudent;
    private ArrayList<String> styles;
    private String currentStyle;
    private Calendar dateOfAchievement;
    private ArrayList<String> distances;
    private String currentDistance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addachievement);

        etStyle = (Spinner) findViewById(R.id.et_newStyle);
        etDistance = (Spinner) findViewById(R.id.et_newDistance);
        etDate = (EditText) findViewById(R.id.et_newDate);
        etTime = (EditText) findViewById(R.id.et_newTime);
        etPoolSize = (EditText) findViewById(R.id.et_poolSize);
        etStrokeCount = (EditText) findViewById(R.id.et_newStrokeCount);
        spinnerStudents = (Spinner) findViewById(R.id.spinner_students);
        fbAccept = (FloatingActionButton) findViewById(R.id.fbAccept);
        fbAccept.setOnClickListener(this);
        etDate.setOnClickListener(this);
        etDate.setOnFocusChangeListener(this);
        currentStudent = "";
        currentStyle = "";
        currentDistance = "";
        styles = new ArrayList<>();
        Collections.addAll(styles,getResources().getStringArray(R.array.styles));
        distances = new ArrayList<>();
        Collections.addAll(distances,getResources().getStringArray(R.array.distances));
        Intent intent = getIntent();
        if (intent != null & intent.hasExtra(TrainingManager.KEY)) {
            fetchTeam(intent.getStringExtra(TrainingManager.KEY));
        }
        setupSpinner();
    }
    private void setupSpinner()
    {
        if (teamData == null)
        {
            spinnerStudents.setVisibility(View.INVISIBLE);
            return;
        }
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, students);

        spinnerStudents.setAdapter(adapter1);
        spinnerStudents.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {

                        int position = spinnerStudents.getSelectedItemPosition();
                        currentStudent = students.get(+position);
                        //Toast.makeText(getApplicationContext(),"You have selected "+distances[+position],Toast.LENGTH_SHORT).show();
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }

                });

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, styles);

        etStyle.setAdapter(adapter2);
        etStyle.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {

                        int position = etStyle.getSelectedItemPosition();
                        if (position>=0)
                            currentStyle = styles.get(+position);
                        //Toast.makeText(getApplicationContext(),"You have selected "+distances[+position],Toast.LENGTH_SHORT).show();
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }

                });
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, distances);

        etDistance.setAdapter(adapter3);
        etDistance.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {

                        int position = etDistance.getSelectedItemPosition();
                        if(position>=0)
                            currentDistance = distances.get(+position);
                        //Toast.makeText(getApplicationContext(),"You have selected "+distances[+position],Toast.LENGTH_SHORT).show();
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }

                });
    }
    private void fetchTeam(String teamName) {

        TeamDataUtils teamDataUtils = new TeamDataUtils(getApplicationContext());
        if (teamName == null || teamName == "")
        {
            ArrayList<TeamData> items = teamDataUtils.getTeams();
            if (items.size()>0) {
                teamData = items.get(0);
            }
        }
        else {
            teamData = teamDataUtils.getTeam(teamName);
        }


        if (teamData != null && teamData.students != null) {
            studentToStudentData = new HashMap(teamData.students.size());
            students = new ArrayList<>();
            for (StudentData student : teamData.students) {
                String studentName = student.name + " " + student.surname;
                students.add(studentName);
                studentToStudentData.put(studentName, student);
            }
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.fbAccept:
                StudentAchievement studentAchievement = new StudentAchievement();
                Date date = ConvertUtils.stringToDate(etDate.getText().toString());
                if (date != null) {
                    studentAchievement.setDate(date);
                }
                if (currentDistance != null)
                {
                    studentAchievement.distance = currentDistance;
                }

                studentAchievement.strokeCount = etStrokeCount.getText().toString();
                if (currentStyle != null)
                {
                    studentAchievement.style = currentStyle;
                }
                studentAchievement.poolSize = etPoolSize.getText().toString();
                studentAchievement.setTime(etTime.getText().toString());

                if (currentStudent != null)
                {
                    StudentData studentData = (StudentData) studentToStudentData.get(currentStudent);
                    if (studentData != null) {
                        CsvDataUtils csvDataUtils = new CsvDataUtils(AddStudentAchievementActivity.this);
                        csvDataUtils.saveStudentAchievement(studentAchievement, studentData.dataFile);
                        clearFields();
                    }
                }
                else {
                    Intent returnIntent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(KEY, studentAchievement);
                    returnIntent.putExtras(bundle);
                    setResult(Activity.RESULT_OK, returnIntent);


                }
                finish();
                break;
            case R.id.et_newDate:
                Dialog newFragment = new DatePickerFragment(AddStudentAchievementActivity.this, AddStudentAchievementActivity.this);
                newFragment.show();
                break;

        }
    }


    public static void startActivity(String teamName, Context context) {
        Intent intent = new Intent(context, AddStudentAchievementActivity.class);
        intent.putExtra(TrainingManager.KEY, teamName);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);
    }

    private void clearFields() {
        etDate.setText("");
        etStrokeCount.setText("");
        etTime.setText("");
    }

    @Override
    public void onDateSelected(Calendar dateOfBirth) {
        this.dateOfAchievement = dateOfBirth;
        String formattedDate;
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        if (dateOfBirth != null) {
            formattedDate = sdf.format(dateOfBirth.getTime());
            etDate.setText(formattedDate);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.et_newDate:
                if (hasFocus) {
                    Dialog newFragment = new DatePickerFragment(AddStudentAchievementActivity.this, AddStudentAchievementActivity.this);
                    newFragment.show();
                }
                break;
        }
    }
}

