package com.example.dobrowol.styloweplywanie.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dobrowol.styloweplywanie.R;
import com.example.dobrowol.styloweplywanie.teammanagement.TeamActivity;
import com.example.dobrowol.styloweplywanie.utils.ITeamDataUtils;
import com.example.dobrowol.styloweplywanie.utils.TeamDataUtils;

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import javax.annotation.Resource;

/**
 * Created by dobrowol on 13.03.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class CreateTeamActivity extends AppCompatActivity implements View.OnClickListener {
    @Resource
    private EditText editTextCoachName;
    private EditText editTextTeamName;
    private Button createTeamButton;
    protected ITeamDataUtils teamDataUtils;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createteam);
        editTextCoachName = (EditText) findViewById(R.id.editText_CreateTeamActivity_CoachName);
        editTextTeamName = (EditText) findViewById(R.id.editText_CreateTeamActivity_TeamName);
        createTeamButton = (Button) findViewById(R.id.button_CreateTeamActivity);
        createTeamButton.setOnClickListener(this);
        teamDataUtils = new TeamDataUtils(getApplicationContext());
        editTextCoachName.setOnClickListener(this);
        editTextTeamName.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editText_CreateTeamActivity_CoachName:
                editTextCoachName.setText("");
                break;
            case R.id.editText_CreateTeamActivity_TeamName:
                editTextTeamName.setText("");
                break;
            case R.id.button_CreateTeamActivity:
                Log.d("DUPA", "CreateTeamActivity create team button");
                String teamName = editTextTeamName.getText().toString();
                teamDataUtils.addTeam(teamName, String.valueOf(editTextCoachName.getText()));

                Intent intent = new Intent(this, TeamActivity.class);
                intent.putExtra("teamName", teamName);
                break;
            default:
                break;


        }
    }
}
