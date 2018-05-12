package dobrowol.styloweplywanie.teammanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import dobrowol.styloweplywanie.R;
import dobrowol.styloweplywanie.teammanagement.trainingdetails.StudentAchievementUtils;
import dobrowol.styloweplywanie.utils.CsvDataUtils;
import dobrowol.styloweplywanie.utils.StudentAdapter;
import dobrowol.styloweplywanie.utils.StudentData;

public class StudentDetailsActivity extends AppCompatActivity implements StudentAdapter.StudentSelectedListener {
    private static final String KEY = "StudentData";
    private static final int GET_USER_REQUEST = 1;
    private TextView firstLine;
    private TextView secondLine;
    private TextView thirdLine;
    private TextView fourthLine;
    private ImageView imageView;
    private StudentData studentData;
    private Menu menu;
    private Toolbar myToolbar;
    private ArrayList<StudentData> items;
    private StudentAdapter adapter;
    private RecyclerView itemsView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teamdetails);
        firstLine = (TextView) findViewById(R.id.firstLine);
        secondLine = (TextView) findViewById(R.id.secondLine);
        secondLine = (TextView) findViewById(R.id.secondLine);
        thirdLine = (TextView) findViewById(R.id.thirdLine);
        fourthLine = (TextView) findViewById(R.id.fourthLine);
        imageView = (ImageView) findViewById(R.id.imageView);
        itemsView = (RecyclerView) findViewById(R.id.student_view);
        itemsView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        adapter = new StudentAdapter(this, Picasso.with(this));
        itemsView.setAdapter(adapter);
        Intent intent = getIntent();
        if (intent != null & intent.hasExtra(KEY)) {
            studentData = (StudentData) intent.getExtras().getSerializable(KEY);
            fillUserDetails();
        }
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        fetchAchievements();
    }
    private void fillUserDetails() {
        firstLine.setText(studentData.name + " " + studentData.surname);
        secondLine.setText(studentData.dateOfBirth);
        thirdLine.setText("Ilość uczniow " + studentData);
    }
    public static void startActivity(StudentData studentData, Context context) {
        Intent intent = new Intent(context, StudentDetailsActivity.class);
        intent.putExtra(KEY, studentData);

        context.startActivity(intent);

    }
    void fetchAchievements()
    {
        StudentAchievementUtils studentAchievementUtils = new StudentAchievementUtils(new CsvDataUtils(getApplicationContext()));
        achievementsMap = studentAchievementUtils.fetchStudentAchievement( getApplicationContext(), studentData.dataFile);

    }

    @Override
    public void onItemSelected(StudentData item) {

    }
}
