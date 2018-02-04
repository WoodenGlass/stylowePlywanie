package com.example.dobrowol.styloweplywanie.teammanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dobrowol.styloweplywanie.R;
import com.example.dobrowol.styloweplywanie.utils.CsvDataUtils;
import com.example.dobrowol.styloweplywanie.utils.StudentAchievement;
import com.example.dobrowol.styloweplywanie.utils.StudentData;
import com.example.dobrowol.styloweplywanie.utils.TeamData;
import com.example.dobrowol.styloweplywanie.utils.TeamDataUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by dobrowol on 08.01.18.
 */

public class TrainingManager extends AppCompatActivity implements View.OnClickListener {
    private static final int ADD_TEAM_REQUEST = 1;
    private static final String KEY = "TeamName";
    private String currentTime;
    private Button btnStart;
    private Button btnLap;
    private Button btnStop;
    private Button btnReset;
    TextView mTimerView, mStudentName;
    int Seconds, Minutes, MilliSeconds ;
    long MillisecondTime, StartTime =0L, TimeBuff, UpdateTime = 0L ;
    Handler handler;
    String[] ListElements = new String[] {  };
    Long[] startedListElements = new Long[]{};
    LinkedList<Long> startedTimer;
    List<Long> stoppedTimer;
    List<String> ListElementsArrayList ;
    ArrayAdapter<String> adapter ;
    ListView listView;
    TeamData teamData;
    Map studentToStudentData;
    Integer timeToSave;
    private String selectedTime;
    private String fileToSave;
    private StudentAchievement currentStudentAchievement;
    Spinner spnr;
    Spinner spnrDistance;
    Spinner spnrStrokeCount;
    Button btnSave;


    String[] styles = {
            "kraul",
            "grzbiet",
            "delfin",
            "żabka",
            "NN kraul",
            "NN delfin",
            "NN żabka",
            "NN grzbiet",
            "NN delfin pod wodą",
            "NN delfin na plecach pod wodą"
    };

    String[] distances = {
      "25m",
            "50m",
            "5m",
            "15m",
            "100m",
            "200m",
            "400m",
            "1500m"
    };
    ArrayList<String> strokeCount;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainingmanager);
        Intent intent = getIntent();
        if (intent != null & intent.hasExtra(KEY)) {
            fetchTeam(intent.getExtras().getString(KEY));
        }
        btnStart = (Button) findViewById(R.id.btnStart);
        btnLap = (Button) findViewById(R.id.btnLap);
        btnStop = (Button) findViewById(R.id.btnStop);
        btnReset = (Button) findViewById(R.id.btnReset);
        btnStart.setOnClickListener(this);
        btnLap.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        mTimerView = (TextView) findViewById(R.id.tvTimer);
        mStudentName = (TextView) findViewById(R.id.tvStudentName);

        btnReset.setEnabled(false);
        btnStop.setEnabled(false);
        btnLap.setEnabled(false);

        handler = new Handler() ;
        startedTimer = new LinkedList<Long>(Arrays.asList(startedListElements));
        ListElementsArrayList = new ArrayList<String>(Arrays.asList(ListElements));
        adapter = new ArrayAdapter<String>(TrainingManager.this,
                android.R.layout.simple_list_item_1,
                ListElementsArrayList
        );
        listView = (ListView)findViewById(R.id.listview1);
        listView.setAdapter(adapter);
        listView.setOnCreateContextMenuListener(this);

        spnr = (Spinner)findViewById(R.id.tmSpinner);
        spnrDistance = (Spinner)findViewById(R.id.tmDistanceSpinner);
        spnrStrokeCount = (Spinner)findViewById(R.id.tmStrokeCountSpinner);
        setupSpinner();
        btnSave = (Button) findViewById(R.id.btnSaveAchievement);
        btnSave.setOnClickListener(this);
        currentStudentAchievement = new StudentAchievement();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

    }
    private void setupSpinner()
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, styles);

        spnr.setAdapter(adapter);
        spnr.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {

                        int position = spnr.getSelectedItemPosition();
                        currentStudentAchievement.style = styles[+position];
                        Toast.makeText(getApplicationContext(),"You have selected "+styles[+position],Toast.LENGTH_SHORT).show();
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }

                });
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, distances);

        spnrDistance.setAdapter(adapter1);
        spnrDistance.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {

                        int position = spnrDistance.getSelectedItemPosition();
                        currentStudentAchievement.distance = distances[+position];
                        Toast.makeText(getApplicationContext(),"You have selected "+distances[+position],Toast.LENGTH_SHORT).show();
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }

                });
        strokeCount = new ArrayList<>();
        for (int i =0; i < 100; i++)
        {
            strokeCount.add(String.valueOf(i));
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, strokeCount);

        spnrStrokeCount.setAdapter(adapter2);
        spnrStrokeCount.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {

                        int position = spnrStrokeCount.getSelectedItemPosition();
                        currentStudentAchievement.strokeCount = strokeCount.get(+position);
                        Toast.makeText(getApplicationContext(),"You have selected "+strokeCount.get(+position),Toast.LENGTH_SHORT).show();
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }

                });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_jointeam, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_addTeam:
                showAddStudent();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void showAddStudent() {
        Intent intent = new Intent(this, AddStudentActivity.class);

        //Intent pickContactIntent = new Intent(Intent.ACTION_PICK, ));
        // Show user only contacts w/ phone numbers
        startActivityForResult(intent, ADD_TEAM_REQUEST);
    }
    private void fetchTeam(String teamName) {
        TeamDataUtils teamDataUtils = new TeamDataUtils(getApplicationContext());
        teamData = teamDataUtils.getTeam(teamName);
        studentToStudentData = new HashMap(teamData.students.size());
        for (StudentData student: teamData.students) {
            String studentName = student.name + " " + student.surname;
            studentToStudentData.put(studentName, student);
        }
    }

    public static void startActivity(String teamName, Context context) {
        Intent intent = new Intent(context,TrainingManager.class);
        intent.putExtra(KEY, teamName);

        context.startActivity(intent);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        int position = info.position;
        selectedTime = (String) listView.getItemAtPosition(position);
        currentStudentAchievement.time = (String) listView.getItemAtPosition(position);
        for (StudentData student : teamData.students) {
            menu.add(0, v.getId(), 0, student.name+" "+student.surname);
        }

    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Log.d("DUPA", item.getTitle().toString());
        StudentData student = (StudentData) studentToStudentData.get(item.getTitle().toString());
        mStudentName.setText(item.getTitle().toString()+" "+(String)(selectedTime));
        fileToSave = student.dataFile;
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnStart:

                    StartTime = SystemClock.uptimeMillis();

                handler.postDelayed(runnable, 0);
                btnReset.setEnabled(true);
                btnStop.setEnabled(true);
                btnLap.setEnabled(true);
                startedTimer.add(StartTime);
                break;
            case R.id.btnLap:

                ListElementsArrayList.add(currentTime);

                adapter.notifyDataSetChanged();
                break;
            case R.id.btnReset:
                MillisecondTime = 0L ;
                StartTime = 0L ;
                TimeBuff = 0L ;
                UpdateTime = 0L ;
                Seconds = 0 ;
                Minutes = 0 ;
                MilliSeconds = 0 ;
                currentTime = "00:00.000";
                mTimerView.setText(currentTime);
                ListElementsArrayList.clear();
                adapter.notifyDataSetChanged();
                btnReset.setEnabled(false);
                btnStop.setEnabled(false);
                btnLap.setEnabled(false);
                break;
            case R.id.btnStop:
                long stopTime = SystemClock.uptimeMillis();
                long startTime = startedTimer.removeFirst();
                if (startedTimer.isEmpty())
                {
                    btnStop.setEnabled(false);
                    btnLap.setEnabled(false);
                    handler.removeCallbacks(runnable);
                }
                formatTime(startTime, stopTime);
                ListElementsArrayList.add(currentTime);

                adapter.notifyDataSetChanged();

                break;
            case R.id.btnSaveAchievement:
                CsvDataUtils csvDataUtils = new CsvDataUtils(getApplicationContext());

                currentStudentAchievement.date = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                csvDataUtils.saveStudentAchievement(currentStudentAchievement, fileToSave);
                Toast.makeText(getApplicationContext(),"Saved to "+fileToSave,Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void startStoper() {

    }


    public Runnable runnable = new Runnable() {

        public void run() {
            MillisecondTime = SystemClock.uptimeMillis() - StartTime;
            formatTime(StartTime, SystemClock.uptimeMillis());

            mTimerView.setText(currentTime);
            handler.postDelayed(this, 0);
        }

    };

    private void formatTime(long startTime, long endTime) {
         MillisecondTime = endTime - startTime;

        Seconds = (int) (MillisecondTime / 1000);

        Minutes = Seconds / 60;

        Seconds = Seconds % 60;

        MilliSeconds = (int) (MillisecondTime % 1000);
        currentTime = "" + String.format("%02d", Minutes) + ":"
                + String.format("%02d", Seconds) + ":"
                + String.format("%03d", MilliSeconds);
    }
}
