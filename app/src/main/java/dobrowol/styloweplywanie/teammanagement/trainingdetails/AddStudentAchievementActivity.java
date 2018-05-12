package dobrowol.styloweplywanie.teammanagement.trainingdetails;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

import dobrowol.styloweplywanie.R;
import dobrowol.styloweplywanie.utils.ConvertUtils;
import dobrowol.styloweplywanie.utils.StudentAchievement;

public class AddStudentAchievementActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String KEY="StudentAchievement";
    private String dataFile;
    private EditText etStyle;
    private EditText etDistance;
    private EditText etDate;
    private EditText etTime;
    private EditText etStrokeCount;
    private FloatingActionButton fbAccept;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addachievement);

        etStyle = (EditText) findViewById(R.id.et_newStyle);
        etDistance = (EditText) findViewById(R.id.et_newDistance);
        etDate = (EditText) findViewById(R.id.et_newDate);
        etTime = (EditText) findViewById(R.id.et_newTime);
        etStrokeCount = (EditText) findViewById(R.id.et_newStrokeCount);
        fbAccept = (FloatingActionButton) findViewById(R.id.fbAccept);
        fbAccept.setOnClickListener(this);
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
                studentAchievement.distance = etDistance.getText().toString();
                studentAchievement.strokeCount = etStrokeCount.getText().toString();
                studentAchievement.style = etStyle.getText().toString();
                studentAchievement.time = etTime.getText().toString();
                Intent returnIntent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable(KEY, studentAchievement);
                returnIntent.putExtras(bundle);
                setResult(Activity.RESULT_OK,returnIntent);

                finish();
                break;

        }
    }
}

