package dobrowol.styloweplywanie.welcome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import dobrowol.styloweplywanie.R;
import dobrowol.styloweplywanie.utils.ITeamDataUtils;
import dobrowol.styloweplywanie.utils.LanguageUtils;
import dobrowol.styloweplywanie.utils.TeamDataUtils;

import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import javax.annotation.Resource;

/**
 * Created by dobrowol on 13.03.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class CreateTeamActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener, TextWatcher {
    @Resource
    private EditText editTextCoachName;
    private EditText editTextTeamName;
    private TextView wrongTeamName;
    private TextView wrongCoachName;
    private Button createTeamButton;
    protected ITeamDataUtils teamDataUtils;
    private boolean teamNameOk = false;
    private boolean coachNameOk = false;
    public final static String RETURNED_DATA_KEY = "TeamName";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createteam);
        editTextCoachName = (EditText) findViewById(R.id.editText_CreateTeamActivity_CoachName);
        editTextTeamName = (EditText) findViewById(R.id.editText_CreateTeamActivity_TeamName);
        createTeamButton = (Button) findViewById(R.id.button_CreateTeamActivity);
        wrongCoachName = (TextView) findViewById(R.id.textView_wrongCoachName);
        wrongTeamName = (TextView) findViewById(R.id.textView_wrongTeamName);
        createTeamButton.setOnClickListener(this);
        teamDataUtils = new TeamDataUtils(getApplicationContext());
        editTextCoachName.setOnClickListener(this);
        editTextTeamName.setOnClickListener(this);
        createTeamButton.setEnabled(false);
        editTextCoachName.setOnFocusChangeListener(this);
        editTextTeamName.setOnFocusChangeListener(this);
        editTextTeamName.addTextChangedListener(this);
        editTextCoachName.addTextChangedListener(this);
    }
    public static void startCreateTeamsActivity(Context context) {
        Intent intent = new Intent(context, CreateTeamActivity.class);

        context.startActivity(intent);
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
                String teamName = LanguageUtils.removePolishSigns(editTextTeamName.getText().toString().replaceAll("\\s",""));
                String coachName = String.valueOf(editTextCoachName.getText());
                teamDataUtils.addTeam(teamName, coachName);

                finishWithResult(teamName);
                break;
            default:
                break;


        }
    }

    private void finishWithResult(String teamName) {
        Intent returnIntent = new Intent();

        returnIntent.putExtra(RETURNED_DATA_KEY,teamName);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    private void wrongNamedWarning(TextView textView, String warning)
    {
            textView.setVisibility(View.VISIBLE);
            textView.setText(warning);
            textView.setTextColor(Color.RED);
            createTeamButton.setEnabled(false);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch(v.getId())
        {
            case R.id.editText_CreateTeamActivity_CoachName:
                if(hasFocus == false) {
                    checkCoachName();
                }

                break;
            case R.id.editText_CreateTeamActivity_TeamName:
                if (hasFocus == false) {
                    checkTeamName();
                }
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        checkCoachName();
        checkTeamName();
    }

    private void checkCoachName() {
        String coachName = editTextCoachName.getText().toString();
        if (coachName.equals("")) {
            wrongNamedWarning(wrongCoachName, "Wrong Coach Name!");

            coachNameOk = false;
        }
        else {
            wrongCoachName.setVisibility(View.INVISIBLE);
            coachNameOk = true;
            if (coachNameOk && teamNameOk)
            {
                createTeamButton.setEnabled(true);
            }
        }
    }

    private void checkTeamName() {
        String coachName = editTextTeamName.getText().toString();
        if (coachName.equals("")) {
            wrongNamedWarning(wrongTeamName,"Wrong Team Name!");
            teamNameOk = false;
        } else {
            wrongTeamName.setVisibility(View.INVISIBLE);
            teamNameOk = true;
            if (teamNameOk && coachNameOk) {
                createTeamButton.setEnabled(true);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
