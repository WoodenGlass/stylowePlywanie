package com.example.dobrowol.styloweplywanie.teammanagement;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dobrowol.styloweplywanie.R;
import com.example.dobrowol.styloweplywanie.utils.TeamData;
import com.example.dobrowol.styloweplywanie.utils.TrainingData;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dobrowol on 08.04.17.
 */

public class AddTrainingActivity extends AppCompatActivity implements OnDateSelectedListener, View.OnClickListener {
    private static final int GET_TRAINING_SETS_REQUEST = 1;
    public static final String TRAINING_SET_KEY = "TRAINING_DATE";
    public static String TRAINING_DATA="TRAINING_DATA";
    private MaterialCalendarView simpleCalendarView;
    private Button buttonAddTraining;
    private TrainingAdapter trainingAdapter;
    private RecyclerView list;
    private TeamData teamData;
    private TrainingData trainingData;
    private Date selectedDate;

    public static final String KEY = "TeamData";
    public static final String[] EVENT_PROJECTION = new String[]{
            CalendarContract.Calendars._ID,                           // 0
            CalendarContract.Calendars.ACCOUNT_NAME,                  // 1
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,         // 2
            CalendarContract.Calendars.OWNER_ACCOUNT                  // 3
    };

    // The indices for the projection array above.
    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_ACCOUNT_NAME_INDEX = 1;
    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;
    private static final int PROJECTION_OWNER_ACCOUNT_INDEX = 3;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtraining);

        setupRecyclerView();


        buttonAddTraining = (Button) findViewById(R.id.button_createTraining);
        buttonAddTraining.setOnClickListener(this);
        simpleCalendarView = (MaterialCalendarView) findViewById(R.id.simpleCalendarView); // get the reference of CalendarView
        simpleCalendarView.setOnDateChangedListener(this);
        listTrainingsFromContext();
    }

    private void setupRecyclerView() {
        trainingAdapter = new TrainingAdapter(this);
        list = (RecyclerView) findViewById(R.id.day_events);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(trainingAdapter);
        DividerItemDecoration horizontalDecoration = new DividerItemDecoration(list.getContext(),
                DividerItemDecoration.VERTICAL);
        Drawable horizontalDivider = ContextCompat.getDrawable(this, R.drawable.horizontal_separator);
        horizontalDecoration.setDrawable(horizontalDivider);
        list.addItemDecoration(horizontalDecoration);
    }

    private void listTrainingsFromContext() {
        Intent intent = getIntent();
        if (intent != null & intent.hasExtra(KEY)) {
            teamData = (TeamData) intent.getExtras().getSerializable(KEY);
            trainingAdapter.setSamples(teamData.trainings.get(selectedDate));
        }
    }

    public static void startAddTrainingActivity(TeamData teamData, Context context) {
        Intent intent = new Intent(context, AddTrainingActivity.class);
        intent.putExtra(KEY, teamData);

        context.startActivity(intent);

    }
    private void startAddTrainingDetailsActivity(CalendarDay date, TrainingData trainingData) {

        Intent intent = new Intent(this, AddTrainingDetails.class);
        /*String trainingDate = date.getDay() + "/" + date.getMonth() + "/" + date.getYear();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TRAINING_DATA, trainingData);
        bundle.putSerializable(TRAINING_SET_KEY, date.getDate());
        intent.putExtras(bundle);*/
        //startActivityForResult(intent, GET_TRAINING_SETS_REQUEST);
        startActivity(intent);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == GET_TRAINING_SETS_REQUEST) {
            if(resultCode == Activity.RESULT_OK){
                TrainingData trainingData = (TrainingData) data.getExtras().getSerializable(TRAINING_DATA);
                ArrayList<TrainingData> trainings = setTrainingDatas(trainingData);
                trainingAdapter.setSamples(trainings);
            }
        }
    }

    @NonNull
    private ArrayList<TrainingData> setTrainingDatas(TrainingData trainingData) {
        ArrayList<TrainingData> trainings = teamData.trainings.get(selectedDate);
        if (trainings == null)
        {
            trainings = new ArrayList<>();
        }
        trainings.add(trainingData);
        teamData.trainings.put(selectedDate, trainings);
        return trainings;
    }

    private void getCalendar() {
        Cursor cur = null;
        ContentResolver cr = getContentResolver();
        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        String selection = "((" + CalendarContract.Calendars.ACCOUNT_NAME + " = ?) AND ("
                + CalendarContract.Calendars.ACCOUNT_TYPE + " = ?) AND ("
                + CalendarContract.Calendars.OWNER_ACCOUNT + " = ?))";
        String[] selectionArgs = new String[]{"dobrowol@example.com", "com.example",
                "dobrowol@example.com"};
// Submit the query and get a Cursor object back.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);
        while (cur.moveToNext()) {
            long calID = 0;
            String displayName = null;
            String accountName = null;
            String ownerName = null;

            // Get the field values
            calID = cur.getLong(PROJECTION_ID_INDEX);
            displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
            accountName = cur.getString(PROJECTION_ACCOUNT_NAME_INDEX);
            ownerName = cur.getString(PROJECTION_OWNER_ACCOUNT_INDEX);

            // Do something with the values...


        }


    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        Toast.makeText(getApplicationContext(), date.getDay() + "/" + date.getMonth() + "/" + date.getYear(), Toast.LENGTH_SHORT).show();
        trainingData = new TrainingData();
        selectedDate = date.getDate();
        startAddTrainingDetailsActivity(date, trainingData);

        //listAdapter.swapData(loadMyEvents(date.getDate()));
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.button_createTraining:
                break;
            default:

                break;

        }

    }
    private void onRouteClicked(TrainingData route) {
        //ActivityInfo activity = route.activityInfo;
        //ComponentName name = new ComponentName(activity.applicationInfo.packageName, activity.name);
        //startActivity(new Intent(Intent.ACTION_VIEW).setComponent(name));
    }
    class TrainingAdapter extends RecyclerView.Adapter<TrainingAdapter.TrainingViewHolder> {

        private final PackageManager pm;
        private final LayoutInflater inflater;
        private List<TrainingData> samples;

        private TrainingAdapter(Context context) {
            this.inflater = LayoutInflater.from(context);
            this.pm = context.getPackageManager();
        }

        public void setSamples(List<TrainingData> samples) {
            this.samples = samples;
        }

        @Override
        public TrainingViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = inflater.inflate(R.layout.calendar_event, viewGroup, false);
            return new TrainingViewHolder(view);
        }

        @Override
        public void onBindViewHolder(TrainingViewHolder viewHolder, int i) {
            TrainingData item = samples.get(i);
            viewHolder.fillView(item);
        }

        @Override
        public int getItemCount() {
            if (samples == null)
                return 0;
            return samples.size();
        }

        class TrainingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public final TextView trainingView;
            public final TextView trainingTime;

            public TrainingViewHolder(View view) {
                super(view);
                this.trainingView = (TextView) view.findViewById(R.id.trainingname_textview);
                this.trainingTime = (TextView) view.findViewById(R.id.trainingtime_textview);
                view.setOnClickListener(this);
            }
            public void fillView(TrainingData trainingData)
            {
                trainingView.setText(trainingData.name);

                trainingTime.setText("czas");
            }

            @Override
            public void onClick(@NonNull View v) {
                onRouteClicked(samples.get(getAdapterPosition()));
            }
        }
    }
}
