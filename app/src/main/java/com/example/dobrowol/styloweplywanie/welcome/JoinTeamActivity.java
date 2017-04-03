package com.example.dobrowol.styloweplywanie.welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.dobrowol.styloweplywanie.R;
import com.example.dobrowol.styloweplywanie.teammanagement.TeamDetailsActivity;
import com.example.dobrowol.styloweplywanie.utils.ItemsAdapter;
import com.example.dobrowol.styloweplywanie.utils.TeamData;
import com.example.dobrowol.styloweplywanie.utils.TeamDataUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by dobrowol on 27.03.17.
 */

public class JoinTeamActivity extends AppCompatActivity implements ItemsAdapter.ItemsSelectedListener {
    private static final int ADD_TEAM_REQUEST = 1;
    private ItemsAdapter adapter;
    private RecyclerView itemsView;
    private List<TeamData> items;
    private TeamDataUtils teamUtils;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jointeam);
        itemsView = (RecyclerView) findViewById(R.id.items_view);
        itemsView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        adapter = new ItemsAdapter(this, Picasso.with(this));
        itemsView.setAdapter(adapter);
        teamUtils = new TeamDataUtils(getApplicationContext());
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        fetchTeams();
    }
    private void fetchTeams() {
        items = teamUtils.getTeams();
        adapter.setItems(items);

    }
    private void fetchTeam(String teamName) {
        TeamData teamData = teamUtils.getTeam(teamName);
        TeamDetailsActivity.startDetailsActivity(teamData, JoinTeamActivity.this);
    }
    @Override
    public void onItemSelected(TeamData item) {
        fetchTeam(item.getTeamName());
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

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAddTeam() {
        Intent intent = new Intent(this, CreateTeamActivity.class);

        //Intent pickContactIntent = new Intent(Intent.ACTION_PICK, ));
        // Show user only contacts w/ phone numbers
        startActivityForResult(intent, ADD_TEAM_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_TEAM_REQUEST) {
            if(resultCode == Activity.RESULT_OK){

                String teamName = data.getStringExtra(CreateTeamActivity.RETURNED_DATA_KEY);
                fetchTeams();
                String teamString = getResources().getString(R.string.team);
                String wasCreatedString = getResources().getString(R.string.wasCreated);
                Toast.makeText(this, teamString+" "
                        +teamName+" "+wasCreatedString,
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}