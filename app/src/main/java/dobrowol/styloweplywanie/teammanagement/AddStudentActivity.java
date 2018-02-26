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

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by dobrowol on 01.04.17.
 */
public class AddStudentActivity extends FragmentActivity implements View.OnClickListener, DatePickerFragment.OnDateSelectedListener {
    private AutoCompleteTextView studentName;
    private AutoCompleteTextView studentSurname;
    private Button addStudentOk;
    private EditText studentAge;
    private Calendar dateOfBirth;
    public final static String RETURNED_DATA_KEY = "StudentData";

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
                studentAge.setText("");
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
        studentData.setAge(dateOfBirth);

        Intent returnIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(RETURNED_DATA_KEY, studentData);
        returnIntent.putExtras(bundle);

        setResult(Activity.RESULT_OK,returnIntent);
        Log.d("DUPA", "addstudent activity 2");
        finish();
    }

    @Override
    public void onDateSelected(Calendar dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        String formattedDate=null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        if (dateOfBirth != null) {
            formattedDate = sdf.format(dateOfBirth.getTime());
        }
        studentAge.setText(formattedDate);
    }
}

