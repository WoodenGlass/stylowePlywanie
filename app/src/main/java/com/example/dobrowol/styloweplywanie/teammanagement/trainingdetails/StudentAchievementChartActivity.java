package com.example.dobrowol.styloweplywanie.teammanagement.trainingdetails;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.dobrowol.styloweplywanie.R;
import com.example.dobrowol.styloweplywanie.utils.CsvDataUtils;
import com.example.dobrowol.styloweplywanie.utils.StudentAchievement;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.dobrowol.styloweplywanie.R.id.chart;

/**
 * Created by dobrowol on 10.02.18.
 */

public class StudentAchievementChartActivity extends AppCompatActivity {

    private LineChart lineChart;
    private String dataFile;
    private static final String KEY = "DataFile";

    public class Key{
        String style;
        String distance;
    };
    public class Value {
        String date;
        String strokeIndex;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_studentachievementchart);
        lineChart = (LineChart) findViewById(chart);
        List<Entry> entries = new ArrayList<Entry>();

            // turn your data into Entry objects
        entries.add(new Entry(1, 2));
        entries.add(new Entry(2, 3));
        entries.add(new Entry(3, 4));

        LineDataSet dataSet = new LineDataSet(entries, "Stroke index"); // add entries to dataset
        //dataSet.setColor(0x2db82d);

        //dataSet.setValueTextColor(0xFFFA);
        dataSet.setLineWidth(Float.valueOf(1));
        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);
        lineChart.invalidate();
        Intent intent = getIntent();
        if (intent != null & intent.hasExtra(KEY)) {
            dataFile= intent.getExtras().getString(KEY);
        }
        fetchStudentAchievement();

    }
    public static void startActivity(String dataFile, Context context) {
        Intent intent = new Intent(context, StudentAchievementChartActivity.class);
        intent.putExtra(KEY, dataFile);

        context.startActivity(intent);
    }
    private void fetchStudentAchievement()
    {
        CsvDataUtils csvDataUtils = new CsvDataUtils(getApplicationContext());
        ArrayList<StudentAchievement> studentAchievements= csvDataUtils.getStudentAchievements(dataFile);

        Map<Key, List<Value>> achievementsMap = new HashMap<>();
    }
}
