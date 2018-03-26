package dobrowol.styloweplywanie.teammanagement.trainingdetails;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dobrowol.styloweplywanie.R;
import dobrowol.styloweplywanie.utils.AchievementsItemsAdapter;
import dobrowol.styloweplywanie.utils.CsvDataUtils;
import dobrowol.styloweplywanie.utils.ItemsAdapter;
import dobrowol.styloweplywanie.utils.StudentAchievement;
import dobrowol.styloweplywanie.utils.TeamData;

/**
 * Created by dobrowol on 25.01.18.
 */

public class StudentAchievementActivity extends AppCompatActivity implements View.OnClickListener, AchievementsItemsAdapter.ItemsSelectedListener {
    public static final String KEY = "StudentName";

    private AchievementsItemsAdapter adapter;
    private RecyclerView itemsView;
    private String dataFile;
    private FloatingActionButton fab;
    private int currentAchievementPosition;
    private StudentAchievement currentAchievement;
    private HashMap<Integer, StudentAchievement> editedAchievements;
    private List<StudentAchievement> listOfAchievements;
    private List<Integer> listOfRemoved;
    private int toRemove;
    private CsvDataUtils csvDataUtils;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jointeam);

        fab = (FloatingActionButton) findViewById(R.id.fab_check);
        fab.setOnClickListener(this);
        itemsView = (RecyclerView) findViewById(R.id.items_view);
        itemsView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        //registerForContextMenu(itemsView);
        adapter = new AchievementsItemsAdapter(this, Picasso.with(this));
        itemsView.setAdapter(adapter);


        currentAchievement = null;
        currentAchievementPosition = -1;
        //TeamDataUtils teamDataUtils = new TeamDataUtils(getApplicationContext());
        //teamDataUtils.clearCache();
        editedAchievements = new HashMap<>();
        listOfAchievements = new ArrayList<>();
        listOfRemoved = new ArrayList<>();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        Intent intent = getIntent();
        if (intent != null & intent.hasExtra(KEY)) {
            dataFile = intent.getExtras().getString(KEY);
            fetchStudentAchievement(dataFile);
        }

    }

    private void fetchStudentAchievement(String dataFile) {
        csvDataUtils = new CsvDataUtils(getApplicationContext());
        listOfAchievements = csvDataUtils.getStudentAchievements(dataFile);
        adapter.setItems(listOfAchievements);
    }

    public static void startActivity(String studentdataFile, Context context) {
        Intent intent = new Intent(context, StudentAchievementActivity.class);
        intent.putExtra(KEY, studentdataFile);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.fab_check:
                if (csvDataUtils != null) {
                    csvDataUtils.overwrite(listOfAchievements, dataFile);
                }
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
                break;
        }
    }

    @Override
    public void onItemSelected(StudentAchievement item, int adapterPosition) {
        currentAchievementPosition = adapterPosition;
        currentAchievement = item;

        listOfAchievements.set(adapterPosition,item);
    }

    @Override
    public void onItemRemoved(int position) {
        if (csvDataUtils != null) {
            listOfRemoved.add(position);
            listOfAchievements.remove(position);
        }

    }

    @Override
    public void OnItem(int adapterPosition) {
        toRemove = adapterPosition;

    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        adapter.removeItem(toRemove);
        return true;
    }
}
