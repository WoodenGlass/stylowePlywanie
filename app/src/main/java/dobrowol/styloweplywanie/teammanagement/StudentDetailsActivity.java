package dobrowol.styloweplywanie.teammanagement;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import dobrowol.styloweplywanie.R;
import dobrowol.styloweplywanie.teammanagement.trainingdetails.StudentAchievementChartActivity;
import dobrowol.styloweplywanie.teammanagement.trainingdetails.StudentAchievementUtils;
import dobrowol.styloweplywanie.utils.AchievementAdapter;
import dobrowol.styloweplywanie.utils.CsvDataUtils;
import dobrowol.styloweplywanie.utils.StudentAchievement;
import dobrowol.styloweplywanie.utils.StudentAdapter;
import dobrowol.styloweplywanie.utils.StudentData;

public class StudentDetailsActivity extends AppCompatActivity implements AchievementAdapter.AchievementSelectedListener, View.OnClickListener {
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

    private RecyclerView itemsView;
    String[] ListElements = new String[] {  };
    ArrayList<CharSequence> ListElementsArrayList ;
    AchievementAdapter adapter ;
    List<StudentAchievement> bestResults;
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
        imageView.setOnClickListener(this);
        itemsView = (RecyclerView) findViewById(R.id.student_view);
        itemsView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));


        adapter = new AchievementAdapter(this);
        itemsView.setAdapter(adapter);
        bestResults = new ArrayList<>();
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
        if (studentData.image != "") {
            Picasso.get().load(studentData.image)
                    .resize(50, 50)
                    .centerCrop()
                    .into(imageView);
        }

    }
    public static void startActivity(StudentData studentData, Context context) {
        Intent intent = new Intent(context, StudentDetailsActivity.class);
        intent.putExtra(KEY, studentData);

        context.startActivity(intent);
    }

    void fetchAchievements()
    {
        StudentAchievementUtils studentAchievementUtils = new StudentAchievementUtils(new CsvDataUtils(getApplicationContext()));
        bestResults = studentAchievementUtils.getBestResults(studentData.dataFile);
        adapter.setItems(bestResults);
    }

    @Override
    public void onItemSelected(StudentAchievement key) {
        Intent intent = new Intent(StudentDetailsActivity.this, StudentAchievementChartActivity.class);
        intent.putExtra(StudentAchievementChartActivity.KEY, studentData.dataFile);
        Bundle bundle = new Bundle();
        bundle.putSerializable(StudentAchievementChartActivity.SECONDARY_KEY, key);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    private void showImageEditDialog()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(StudentDetailsActivity.this);
        alertDialog.setTitle("Edit Student Image");
        alertDialog.setMessage("Enter image http address");

        final EditText input = new EditText(StudentDetailsActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        alertDialog.setIcon(R.drawable.ic_insert_photo);

        alertDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String imageAddress = input.getText().toString();
                        if (imageAddress.compareTo("") == 0) {
                            if (true) {
                                Toast.makeText(getApplicationContext(),
                                        "Photo edited", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Photo address in wrong format!(should be http://)", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

        alertDialog.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.imageView:
                showImageEditDialog();
                break;

        }
    }
}
