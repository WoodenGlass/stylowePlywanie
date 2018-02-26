package dobrowol.styloweplywanie.teammanagement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import dobrowol.styloweplywanie.R;
import dobrowol.styloweplywanie.utils.TeamData;
import dobrowol.styloweplywanie.utils.TeamDataUtils;

/**
 * Created by dobrowol on 27.03.17.
 */

public class TeamActivity extends AppCompatActivity {

    private TeamData teamData;
    private TextView coachNameTextView;
    private TextView teamNameTextView;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        setTeamData();
        teamNameTextView = (TextView) findViewById(R.id.teamName);
        coachNameTextView = (TextView) findViewById(R.id.coachName);
        teamNameTextView.setText(teamData.getTeamName());
        coachNameTextView.setText(teamData.getCoachName());
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


    }

    private void setTeamData() {
        Bundle bundleTeamName = getIntent().getExtras();
        String teamName = bundleTeamName.getString("teamName");
        if (teamName != null) {
            TeamDataUtils teamDataUtils = new TeamDataUtils(getApplicationContext());
            teamData = teamDataUtils.getTeam(teamName);
        }
    }


}
