package dobrowol.styloweplywanie.teammanagement;

import android.app.Activity;
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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import dobrowol.styloweplywanie.R;
import dobrowol.styloweplywanie.teammanagement.trainingdetails.AddStudentAchievementActivity;
import dobrowol.styloweplywanie.utils.CsvDataUtils;
import dobrowol.styloweplywanie.utils.StudentAchievement;
import dobrowol.styloweplywanie.utils.StudentData;
import dobrowol.styloweplywanie.utils.TeamData;
import dobrowol.styloweplywanie.utils.TeamDataUtils;
import dobrowol.styloweplywanie.welcome.CreateTeamActivity;
import dobrowol.styloweplywanie.welcome.JoinTeamActivity;

/**
 * Created by dobrowol on 08.01.18.
 */

public class TrainingManager extends AppCompatActivity implements View.OnClickListener {
    private static final int ADD_TEAM_REQUEST = 1;
    public static final String KEY = "TeamName";
    private static final int ADD_STUDENT_REQUEST = 2;
    private static final int JOIN_TEAM_REQUEST = 3;
    private String currentTime;
    private Button btnStart;
    private Button btnLap;
    private Button btnStop;
    private Button btnReset;
    private Menu the_menu;
    TextView mTimerView, mStudentName, mStrokeIndex, mStopBtn;
    int Seconds, Minutes, MilliSeconds ;
    long MillisecondTime, StartTime =0L, TimeBuff, UpdateTime = 0L ;
    Handler handler;
    String[] ListElements = new String[] {  };
    Long[] startedListElements = new Long[]{};
    LinkedList<Long> startedTimer;
    List<Long> stoppedTimer;
    ArrayList<CharSequence> ListElementsArrayList ;
    ArrayAdapter<CharSequence> adapter ;
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
    private int numberOfRunningStopwatch;


    String[] styles = {
            "kraul",
            "grzbiet",
            "delfin",
            "żabka",
            "zmienny",
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
            "10m",
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
        else
        {
            fetchTeam("");
        }
        numberOfRunningStopwatch = 0;
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
        mStrokeIndex = (TextView) findViewById(R.id.tvSrokeIndex);
        mStopBtn = (TextView) findViewById(R.id.tvStopBtn);

        btnReset.setEnabled(false);
        btnStop.setEnabled(false);
        btnLap.setEnabled(false);

        handler = new Handler() ;
        startedTimer = new LinkedList<Long>(Arrays.asList(startedListElements));
        ListElementsArrayList = new ArrayList<CharSequence>(Arrays.asList(ListElements));
        adapter = new ArrayAdapter<CharSequence>(TrainingManager.this,
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

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.screenBrightness = 1;
        getWindow().setAttributes(params);

    }
    private void updateStrokeIndex()
    {
        currentStudentAchievement.calculateStrokeIndex();
        mStrokeIndex.setText("StrokeIndex: " + currentStudentAchievement.strokeIndex.toString());
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
                        //Toast.makeText(getApplicationContext(),"You have selected "+styles[+position],Toast.LENGTH_SHORT).show();
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
                        updateStrokeIndex();
                        //Toast.makeText(getApplicationContext(),"You have selected "+distances[+position],Toast.LENGTH_SHORT).show();
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
                        updateStrokeIndex();
                        //Toast.makeText(getApplicationContext(),"You have selected "+strokeCount.get(+position),Toast.LENGTH_SHORT).show();
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
                showAddTeam();
                return true;
            case R.id.action_addTeamMember:
                showAddStudent();
                return true;
            case R.id.action_add:
                showAddAchievement();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAddAchievement() {
        AddStudentAchievementActivity.startActivity(teamData.teamName, TrainingManager.this);
    }

    private void showAddTeam() {
        TeamDataUtils teamDataUtils = new TeamDataUtils(getApplicationContext());
        Intent intent;
        if (teamDataUtils.getTeams().size() > 0)
        {
            intent = new Intent(this, JoinTeamActivity.class);
            startActivityForResult(intent, JOIN_TEAM_REQUEST);
        }
        else {
            intent = new Intent(this, CreateTeamActivity.class);
            startActivityForResult(intent, ADD_TEAM_REQUEST);
        }
    }

    private void showAddStudent() {
        if (teamData == null)
            return;
        Intent intent = new Intent(this, StudentsActivity.class);
        intent.putExtra(KEY, teamData.teamName);
        //Intent pickContactIntent = new Intent(Intent.ACTION_PICK, ));
        // Show user only contacts w/ phone numbers
        startActivityForResult(intent, ADD_STUDENT_REQUEST);
        //StudentsActivity.startActivity(teamData.teamName, TrainingManager.this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_STUDENT_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                fetchTeam(teamData.teamName);
            }
        }
            else if (requestCode == ADD_TEAM_REQUEST) {
                if(resultCode == Activity.RESULT_OK) {
                    fetchTeam( data.getStringExtra(CreateTeamActivity.RETURNED_DATA_KEY));
                }
        }
        else if (requestCode == JOIN_TEAM_REQUEST) {
            if(resultCode == Activity.RESULT_OK) {
                fetchTeam( data.getStringExtra(JoinTeamActivity.RETURNED_DATA_KEY));
            }
        }
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
        if (teamData == null)
        {
            setTitle("Stylowe Pływanie(No team)");
            return;
        }
        setTitle("Stylowe Pływanie("+teamData.teamName+")");

        if (the_menu != null) {
            the_menu.findItem(R.id.action_addTeamMember).setVisible(true).setEnabled(true);
        }
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
        currentStudentAchievement.time = ListElementsArrayList.get(position).toString();
        if (teamData != null) {
            for (StudentData student : teamData.students) {
                menu.add(0, v.getId(), 0, student.name + " " + student.surname);
            }
        }

    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Log.d("DUPA", item.getTitle().toString());
        StudentData student = (StudentData) studentToStudentData.get(item.getTitle().toString());
        mStudentName.setText(item.getTitle().toString()+" "+(String)(selectedTime));
        updateStrokeIndex();
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
                numberOfRunningStopwatch++;
                mStopBtn.setText("STOP ("+numberOfRunningStopwatch+")");
                break;
            case R.id.btnLap: {
                long stopTime = SystemClock.uptimeMillis();
                long startTime = startedTimer.peekFirst();
                formatTime(startTime, stopTime);
                ListElementsArrayList.add(String.valueOf(MillisecondTime));

                adapter.notifyDataSetChanged();
                break;
            }
            case R.id.btnReset:
                startedTimer.clear();
                handler.removeCallbacks(runnable);
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
                mStrokeIndex.setText("StrokeIndex: 0.0");
                mStudentName.setText("");
                numberOfRunningStopwatch=0;
                mStopBtn.setText("STOP ("+numberOfRunningStopwatch+")");
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
                ListElementsArrayList.add(String.valueOf(MillisecondTime));

                adapter.notifyDataSetChanged();
                numberOfRunningStopwatch--;
                mStopBtn.setText("STOP ("+numberOfRunningStopwatch+")");

                break;
            case R.id.btnSaveAchievement:
                CsvDataUtils csvDataUtils = new CsvDataUtils(getApplicationContext());
                currentStudentAchievement.setDate();
                if (fileToSave == null || fileToSave == "")
                {
                    Toast.makeText(getApplicationContext(),"You have to choose student to save achievement!(Long click on result)",Toast.LENGTH_SHORT).show();
                    break;
                }
                csvDataUtils.saveStudentAchievement(currentStudentAchievement, fileToSave);
                //Toast.makeText(getApplicationContext(),"Saved to "+fileToSave,Toast.LENGTH_SHORT).show();
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("DUPA"," saveInstance");
        outState.putInt("strokeCount", spnrStrokeCount.getSelectedItemPosition());
        outState.putInt("distance", spnrDistance.getSelectedItemPosition());
        outState.putInt("style", spnr.getSelectedItemPosition());
        outState.putCharSequenceArrayList("listOfElementsArray", ListElementsArrayList);
        outState.putString("currentTime", mTimerView.getText().toString());
        outState.putSerializable("currentStudentAchievement", currentStudentAchievement);
        outState.putString("studentName", mStudentName.getText().toString());


    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        spnrStrokeCount.setSelection(savedInstanceState.getInt("strokeCount"));
        spnrDistance.setSelection(savedInstanceState.getInt("distance"));
        spnr.setSelection(savedInstanceState.getInt("style"));
        ListElementsArrayList = savedInstanceState.getCharSequenceArrayList("listOfElementsArray");
        Toast.makeText(getApplicationContext(),"number of elements in list " + ListElementsArrayList.size(),Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();
        currentStudentAchievement = (StudentAchievement) savedInstanceState.getSerializable("currentStudentAchievement");
        updateStrokeIndex();
        mStudentName.setText(savedInstanceState.getString("studentName"));
        mTimerView.setText(savedInstanceState.getString("currentTime"));
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        the_menu = menu;
        if (teamData == null) {
            menu.findItem(R.id.action_addTeamMember).setVisible(false).setEnabled(false);
        }
        else {
            the_menu.findItem(R.id.action_addTeamMember).setVisible(true).setEnabled(true);
        }
        return true;
    }
}
