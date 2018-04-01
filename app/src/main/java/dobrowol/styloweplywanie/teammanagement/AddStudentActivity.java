package dobrowol.styloweplywanie.teammanagement;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import dobrowol.styloweplywanie.R;
import dobrowol.styloweplywanie.utils.DatePickerFragment;
import dobrowol.styloweplywanie.utils.StudentData;
import dobrowol.styloweplywanie.utils.TeamData;
import dobrowol.styloweplywanie.utils.TeamDataUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by dobrowol on 01.04.17.
 */
public class AddStudentActivity extends FragmentActivity implements View.OnClickListener, DatePickerFragment.OnDateSelectedListener, View.OnFocusChangeListener {
    private AutoCompleteTextView studentName;
    private AutoCompleteTextView studentSurname;
    private Button addStudentOk;
    private EditText studentAge;
    private Calendar dateOfBirth;
    public final static String RETURNED_DATA_KEY = "StudentData";
    private boolean dateOfBirthOk;
    private boolean nameOk;
    private boolean surnameOk;
    private static final String KEY = "TeamName";
    private TeamDataUtils teamDataUtils;
    private TeamData teamData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addstudent);

        studentName = (AutoCompleteTextView) findViewById(R.id.autocomplete_studentname);
        studentSurname = (AutoCompleteTextView) findViewById(R.id.autocomplete_studentsurname);
        studentAge = (EditText) findViewById(R.id.editText_studentage);
        addStudentOk = (Button) findViewById(R.id.button_addStudentOK);

        studentAge.setOnClickListener(this);
        studentSurname.setOnClickListener(this);
        studentName.setOnClickListener(this);
        addStudentOk.setOnClickListener(this);
        studentAge.setOnFocusChangeListener(this);
        studentName.setOnFocusChangeListener(this);
        studentSurname.setOnFocusChangeListener(this);

        addStudentOk.setEnabled(false);
        dateOfBirthOk = nameOk = surnameOk = false;
        Intent intent = getIntent();
        if (intent != null & intent.hasExtra(KEY)) {
            fetchStudents(intent.getExtras().getString(KEY));
        }
    }

    private void fetchStudents(String teamName) {
        teamDataUtils = new TeamDataUtils(getApplicationContext());
        teamData = teamDataUtils.getTeam(teamName);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.autocomplete_studentname:
                studentName.setText("");
                break;
            case R.id.autocomplete_studentsurname:
                studentSurname.setText("");
                break;
            case R.id.editText_studentage:
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
                break;
            case R.id.button_addStudentOK:
                addStudent();
                break;
            default:
                break;
        }
    }

    private void addStudent() {
        StudentData studentData = new StudentData();
        Log.d("DUPA", "addstudent activity 1");
        studentData.setName(String.valueOf(studentName.getText()));
        studentData.setSurname(String.valueOf(studentSurname.getText()));
        studentData.setDateOfBirth(dateOfBirth);
        teamData.addStudent(studentData);
        teamDataUtils.updateTeam(teamData);
        Intent returnIntent = new Intent();

        setResult(Activity.RESULT_OK,returnIntent);
        Log.d("DUPA", "addstudent activity 2");
        finish();
    }

    @Override
    public void onDateSelected(Calendar dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        String formattedDate;
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        if (dateOfBirth != null) {
            formattedDate = sdf.format(dateOfBirth.getTime());
            studentAge.setText(formattedDate);
            dateOfBirthOk=true;
            isStudentSavable();
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId())
        {
            case R.id.editText_studentage:
                if (hasFocus) {
                    DialogFragment newFragment = new DatePickerFragment();
                    newFragment.show(getFragmentManager(), "datePicker");
                }
                else
                {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                    String editTextDateOfBirth = studentAge.getText().toString();
                    if (editTextDateOfBirth != null && !editTextDateOfBirth.equals("")) {
                        String formattedDate = sdf.format(editTextDateOfBirth);
                        if (formattedDate != null)
                        {
                            dateOfBirthOk = true;
                            isStudentSavable();
                        }
                    }
                    else
                    {
                        dateOfBirthOk = false;
                    }
                }
                break;
            case R.id.autocomplete_studentname:
                String name = studentName.getText().toString();
                if (name != null && !name.equals(""))
                {
                    nameOk = true;
                    isStudentSavable();
                }
                break;
            case R.id.autocomplete_studentsurname:
                String surname = studentSurname.getText().toString();
                if (surname != null && !surname.equals(""))
                {
                    surnameOk = true;
                    isStudentSavable();
                }
                break;

        }
    }
    void isStudentSavable()
    {
        if (dateOfBirthOk && nameOk && surnameOk)
        {
            addStudentOk.setEnabled(true);
        }
    }
}

