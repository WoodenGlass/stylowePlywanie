package com.example.dobrowol.styloweplywanie.teammanagement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.dobrowol.styloweplywanie.R;
import com.example.dobrowol.styloweplywanie.utils.StudentAdapter;
import com.example.dobrowol.styloweplywanie.utils.StudentData;
import com.example.dobrowol.styloweplywanie.utils.TeamData;
import com.example.dobrowol.styloweplywanie.utils.TeamDataUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by dobrowol on 02.12.17.
 */

public class StudentsActivity extends AppCompatActivity implements StudentAdapter.StudentSelectedListener  {
    private static final int ADD_TEAM_REQUEST = 1;
    private StudentAdapter adapter;
    private RecyclerView itemsView;
    private List<TeamData> items;
    private TeamDataUtils teamUtils;
    private TeamData teamData;
    private static final String KEY = "TeamName";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jointeam);

        //TeamDataUtils teamDataUtils = new TeamDataUtils(getApplicationContext());
        //teamDataUtils.clearCache();

        itemsView = (RecyclerView) findViewById(R.id.items_view);
        itemsView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        adapter = new StudentAdapter(this, Picasso.with(this));
        itemsView.setAdapter(adapter);
        teamUtils = new TeamDataUtils(getApplicationContext());
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        Intent intent = getIntent();
        if (intent != null & intent.hasExtra(KEY)) {
            fetchStudents(intent.getExtras().getString(KEY));
        }

    }
    public static void startActivity(String teamName, Context context) {
        Intent intent = new Intent(context, StudentsActivity.class);
        intent.putExtra(KEY, teamName);

        context.startActivity(intent);

    }
    private void fetchStudents(String teamName)
    {
        teamData = teamUtils.getTeam(teamName);
        Log.d("DUPA students ", Integer.toString(teamData.students.size()));
        adapter.setItems(teamData.students);
    }

    @Override
    public void onItemSelected(StudentData item) {
        fetchStudents(item.name);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_TEAM_REQUEST) {
            if(resultCode == Activity.RESULT_OK){

                StudentData studentData = (StudentData) data.getExtras().getSerializable(AddStudentActivity.RETURNED_DATA_KEY);
                teamData.addStudent(studentData);
                Log.d("DUPA addStudent ", Integer.toString(teamData.students.size()));
                TeamDataUtils teamDataUtils = new TeamDataUtils(getApplicationContext());
                teamDataUtils.updateTeam(teamData);
                fetchStudents(teamData.teamName);
                String wasAddedString = getResources().getString(R.string.wasAdded);
                Toast.makeText(this, "Student "
                                +studentData.name+" "+wasAddedString,
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}