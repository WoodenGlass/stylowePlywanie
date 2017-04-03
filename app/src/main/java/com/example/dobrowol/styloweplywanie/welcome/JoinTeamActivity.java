package com.example.dobrowol.styloweplywanie.welcome;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

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
}
