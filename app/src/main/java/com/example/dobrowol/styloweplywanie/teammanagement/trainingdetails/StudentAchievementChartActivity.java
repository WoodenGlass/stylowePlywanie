package com.example.dobrowol.styloweplywanie.teammanagement.trainingdetails;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.dobrowol.styloweplywanie.R;
import com.example.dobrowol.styloweplywanie.utils.CsvDataUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.dobrowol.styloweplywanie.R.id.chart;

/**
 * Created by dobrowol on 10.02.18.
 */

public class StudentAchievementChartActivity extends AppCompatActivity {

    private StudentAchievementUtils studentAchievementUtils;
    private LineChart lineChart;
    private String dataFile;
    private static final String KEY = "DataFile";
    private Spinner labelSpinner;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_studentachievementchart);
        lineChart = (LineChart) findViewById(chart);
        labelSpinner = (Spinner) findViewById(R.id.labelsSpinner);
         // add entries to dataset
        //dataSet.setColor(0x2db82d);

        //dataSet.setValueTextColor(0xFFFA);
        studentAchievementUtils = new StudentAchievementUtils(new CsvDataUtils(getApplicationContext()));
        Intent intent = getIntent();
        if (intent != null & intent.hasExtra(KEY)) {
            dataFile= intent.getExtras().getString(KEY);
        }
        fetchStudentAchievement();

    }
    private void drawLineChart(LineDataSet dataSet)
    {
        //dataSet.setValueTextColor(0xFF00FF);
        dataSet.setColor(Color.RED);
        dataSet.setLineWidth(Float.valueOf(1));
        dataSet.setDrawCircles(true);
        dataSet.setFillColor(Color.rgb(0, 0, 0));
        dataSet.setLineWidth(0.2f);
        LineData lineData = new LineData(dataSet);

        XAxis x = lineChart.getXAxis();
        x.setEnabled(true);
        x.setDrawGridLines(false);
        x.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        x.setLabelCount(5);
        YAxis y = lineChart.getAxisLeft();
        y.setEnabled(true);
        y.setDrawGridLines(false);
        lineChart.setData(lineData);
        lineChart.invalidate();

    }
    public static void startActivity(String dataFile, Context context) {
        Intent intent = new Intent(context, StudentAchievementChartActivity.class);
        intent.putExtra(KEY, dataFile);

        context.startActivity(intent);
    }
    private void fetchStudentAchievement()
    {
        Map<StudentAchievementUtils.Key, List<StudentAchievementUtils.Value>> achievementsMap;
        achievementsMap = studentAchievementUtils.fetchStudentAchievement( getApplicationContext(), dataFile);
        final List<LineDataSet> lineDataSets = studentAchievementUtils.getLineDataSets(achievementsMap);
        //LineDataSet dataSet = lineDataSets.get(0);
        //for (LineDataSet dataSet : lineDataSets){
        //Toast.makeText(getApplicationContext(), dataSet.getLabel()+" "+dataSet.getEntryCount(), Toast.LENGTH_SHORT).show();

        ArrayList<String> labels = new ArrayList<>(lineDataSets.size());
        for (LineDataSet dataSet : lineDataSets){
            labels.add(dataSet.getLabel());
        }
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, labels);

        labelSpinner.setAdapter(adapter1);
        labelSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {

                        int position = labelSpinner.getSelectedItemPosition();
                        drawLineChart(lineDataSets.get(+position));

                        //Toast.makeText(getApplicationContext(),"You have selected "+distances[+position],Toast.LENGTH_SHORT).show();
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }

                });
            drawLineChart(lineDataSets.get(0));
        //}
    }
}
