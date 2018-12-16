package dobrowol.styloweplywanie.teammanagement.trainingdetails;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import dobrowol.styloweplywanie.R;
import dobrowol.styloweplywanie.utils.ConvertUtils;
import dobrowol.styloweplywanie.utils.CsvDataUtils;
import dobrowol.styloweplywanie.utils.StudentAchievement;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static dobrowol.styloweplywanie.R.id.chart;

/**
 * Created by dobrowol on 10.02.18.
 */

public class StudentAchievementChartActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int EDIT_ACHIEVEMENTS = 1;
    private StudentAchievementUtils studentAchievementUtils;
    private FloatingActionButton fab;
    private FloatingActionButton fab_save;
    private LineChart lineChart;
    private LineChart strokeIndexLineChart;
    private String dataFile;
    public static final String KEY = "DataFile";
    public static final String SECONDARY_KEY = "Achievement";
    private String file_name;
    private Spinner labelSpinner;
    private StudentAchievement baseStudentAchievement;
    private ArrayList<String> currentXaxisLabels;
    private ArrayList<String> currentYaxisLabels;
    private Map<Integer, StudentAchievementUtils.Key> positionToKey;
    private Map<StudentAchievementUtils.Key, List<StudentAchievement>> achievementsMap;
    private LineDataSet lineDataSet;
    private LineDataSet strokeIndexDataSet;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_studentachievementchart);
        lineChart = (LineChart) findViewById(chart);
        strokeIndexLineChart = (LineChart) findViewById(R.id.strokeIndexchart);
        //labelSpinner = (Spinner) findViewById(R.id.labelsSpinner);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        fab_save = (FloatingActionButton) findViewById(R.id.fab_save);
        fab_save.setOnClickListener(this);

        // add entries to dataset
        //dataSet.setColor(0x2db82d);

        //dataSet.setValueTextColor(0xFFFA);
        studentAchievementUtils = new StudentAchievementUtils(new CsvDataUtils(getApplicationContext()));
        Intent intent = getIntent();
        if (intent != null & intent.hasExtra(KEY)) {
            dataFile= intent.getExtras().getString(KEY);
            String []splited = dataFile.split("\\.");
            file_name=splited[0];
        }
        if (intent != null & intent.hasExtra(SECONDARY_KEY))
        {
            baseStudentAchievement = (StudentAchievement) intent.getExtras().getSerializable(SECONDARY_KEY);

        }
        currentXaxisLabels = new ArrayList<>();
        currentYaxisLabels = new ArrayList<>();
        fetchStudentAchievement();
        verifyStoragePermissions(this);
    }
    private void drawBarChart()
    {
        LineChart chart = (LineChart) findViewById(R.id.chart);
        List<Entry> entries = new ArrayList<Entry>();
        StudentAchievementUtils.Key k = new StudentAchievementUtils.Key(baseStudentAchievement.style, baseStudentAchievement.distance);
        List<StudentAchievement> values = achievementsMap.get(k);
        int i = 0;
        // turn your data into Entry objects
        for (StudentAchievement v : values) {
            entries.add(new Entry(i, Float.valueOf(v.time)));
            currentXaxisLabels.add(v.date);
            i++;
        }

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");


        LineDataSet dataset = new LineDataSet(entries, "# of Calls");

        LineData data = new LineData(dataset);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        XAxis x = chart.getXAxis();
        x.setEnabled(true);
        x.setDrawGridLines(true);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setGranularity(1f);
        x.setLabelCount(currentXaxisLabels.size());
        x.setLabelRotationAngle(45);
        x.setValueFormatter(new MyXAxisValueFormatter(currentXaxisLabels));

        YAxis y = chart.getAxisLeft();
        y.setEnabled(true);

        //y.setValueFormatter(new MyYAxisValueFormatter(currentYaxisLabels));
        y.setDrawLabels(false);
        y.setLabelCount(currentXaxisLabels.size());

        // y.setGranularity(1000f);

        YAxis y2 = chart.getAxisRight();
        y2.setEnabled(false);
        y.setDrawLabels(false);


        chart.getDescription().setPosition(200f,20f);
        chart.getDescription().setTextSize(16);
        chart.getDescription().setText(baseStudentAchievement.distance + "m " + baseStudentAchievement.style);

        chart.setExtraOffsets(20,20,20,20);
        chart.setData(data);
        chart.fitScreen();

    }
    private void drawLineChart()
    {
        setLineDataSet();
        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);
        dataSets.add(strokeIndexDataSet);
        //dataSet.setValueTextColor(0xFF00FF);
        lineDataSet.setColor(Color.RED);
        lineDataSet.setLineWidth(Float.valueOf(1));
        lineDataSet.setDrawCircles(true);
        lineDataSet.setFillColor(Color.rgb(0, 0, 0));
        lineDataSet.setLineWidth(0.2f);

        strokeIndexDataSet.setColor(Color.BLUE);
        strokeIndexDataSet.setLineWidth(Float.valueOf(1));
        strokeIndexDataSet.setDrawCircles(true);
        strokeIndexDataSet.setFillColor(Color.rgb(0, 0, 0));
        strokeIndexDataSet.setLineWidth(0.2f);

        LineData lineData = new LineData( lineDataSet);
        LineData strokeIndexLineData = new LineData(strokeIndexDataSet);
        lineData.setValueFormatter(new MyValueFormatter());

        XAxis x = lineChart.getXAxis();
        x.setEnabled(true);
        x.setDrawGridLines(true);
        x.setPosition(XAxis.XAxisPosition.BOTTOM);
        x.setLabelCount(lineDataSet.getEntryCount());
        x.setLabelRotationAngle(45);
        x.setValueFormatter(new MyXAxisValueFormatter(currentXaxisLabels));
        x.setLabelCount(currentXaxisLabels.size());
        x.setGranularity(1f);

        XAxis x2 = strokeIndexLineChart.getXAxis();
        x2.setEnabled(true);
        x2.setDrawGridLines(false);
        x2.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        x2.setLabelCount(strokeIndexDataSet.getEntryCount());
        x2.setLabelRotationAngle(45);
        x2.setValueFormatter(new MyXAxisValueFormatter(currentXaxisLabels));

        YAxis strokeIndexYAxis = strokeIndexLineChart.getAxisLeft();
        strokeIndexYAxis.setEnabled(true);
        strokeIndexYAxis.setDrawGridLines(false);
        //strokeIndexYAxis.setGranularity(1000f);

        YAxis y = lineChart.getAxisLeft();
        y.setEnabled(true);
        y.setDrawGridLines(false);
        //y.setValueFormatter(new MyYAxisValueFormatter(currentYaxisLabels));
        y.setDrawLabels(false);
        y.setLabelCount(currentXaxisLabels.size());

       // y.setGranularity(1000f);

        YAxis y2 = lineChart.getAxisRight();
        y2.setEnabled(false);

        lineChart.setData(lineData);
        lineChart.getDescription().setPosition(200f,20f);
        lineChart.getDescription().setTextSize(12);
        lineChart.getDescription().setText(baseStudentAchievement.distance + "m " + baseStudentAchievement.style);
        lineChart.setExtraOffsets(40,20,40,20);
        lineChart.invalidate();

        strokeIndexLineChart.setData(strokeIndexLineData);
        strokeIndexLineChart.invalidate();
    }
    public static void startActivity(String dataFile, Context context) {
        Intent intent = new Intent(context, StudentAchievementChartActivity.class);
        intent.putExtra(KEY, dataFile);

        context.startActivity(intent);
    }
    private void setLineDataSet(int position)
    {
        StudentAchievementUtils.Key k = positionToKey.get(position);
        setLineDataSet(positionToKey.get(position));
    }
    private void setLineDataSet() {
        if (baseStudentAchievement != null) {
            //labelSpinner.getSelectedItemPosition();
            StudentAchievementUtils.Key key = new StudentAchievementUtils.Key(baseStudentAchievement.style, baseStudentAchievement.distance);
            setLineDataSet(key);
        }
    }
    @NonNull
    private void setLineDataSet(StudentAchievementUtils.Key k) {
        List<StudentAchievement> values = achievementsMap.get(k);

        List<Entry> entries = new ArrayList<Entry>();
        List<Entry> strokeIndexEntries = new ArrayList<Entry>();

        int i = 0;
        // turn your data into Entry objects
        for (StudentAchievement v : values) {
            entries.add(new Entry(i, Float.valueOf(v.time)));
            strokeIndexEntries.add(new Entry(i, Float.valueOf(v.strokeIndex)));
            i++;
            currentXaxisLabels.add(v.date);
            currentYaxisLabels.add(v.time);
        }

        lineDataSet =  new LineDataSet(entries, "Czas na " + k.distance + "m " + k.style);
        //lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        strokeIndexDataSet = new LineDataSet(strokeIndexEntries, "Stroke index for " + k.style + " " + k.distance);

    }

    private void fetchStudentAchievement()
    {
        studentAchievementUtils = new StudentAchievementUtils(new CsvDataUtils(getApplicationContext()));
        achievementsMap = studentAchievementUtils.fetchStudentAchievement( dataFile);
        //LineDataSet dataSet = lineDataSets.get(0);
        //for (LineDataSet dataSet : lineDataSets){
        //Toast.makeText(getApplicationContext(), dataSet.getLabel()+" "+dataSet.getEntryCount(), Toast.LENGTH_SHORT).show();

        ArrayList<String> labels = new ArrayList<>();
        positionToKey = new HashMap<>();
        int i = 0;
        for (StudentAchievementUtils.Key key : achievementsMap.keySet()){
            labels.add("Stroke index of " + key.style + " " + key.distance);
            positionToKey.put(i++, key);
        }
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, labels);

       // labelSpinner.setAdapter(adapter1);
      /*  labelSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {

                        int position = labelSpinner.getSelectedItemPosition();
                        drawLineChart(getLineDataSet(achievementsMap,position));

                        //Toast.makeText(getApplicationContext(),"You have selected "+distances[+position],Toast.LENGTH_SHORT).show();
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }

                });*/
        if (baseStudentAchievement != null)
        {
            drawLineChart();
        }
        //}
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.fab:
            Intent i = new Intent(this, StudentAchievementActivity.class);
            i.putExtra(StudentAchievementActivity.KEY, dataFile);
            startActivityForResult(i, EDIT_ACHIEVEMENTS);
            break;

            case R.id.fab_save:
                Long tsLong = System.currentTimeMillis()/1000;
                String ts = tsLong.toString();
            boolean saved = lineChart.saveToGallery(file_name+ts,100);
                Toast.makeText(getApplicationContext(),"File saved "+saved, Toast.LENGTH_LONG ).show();
            break;
            //StudentAchievementActivity.startActivity(dataFile, StudentAchievementChartActivity.this);
        }
    }
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
    public class MyYAxisValueFormatter implements IAxisValueFormatter{
    private List<String> mValues;

    public MyYAxisValueFormatter(List<String> values) {
        this.mValues = values;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // "value" represents the position of the label on the axis (x or y)
        int index = (int)value;
        String fValue=String.valueOf(index);

        fValue = ConvertUtils.formatTime(fValue);

        return fValue;
    }
}
    public class MyValueFormatter implements IValueFormatter {

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            // write your logic here
            int index = (int)value;
            String fValue=String.valueOf(index);

            fValue = ConvertUtils.formatTime(fValue);

            return fValue;
        }
    }
    public class MyXAxisValueFormatter implements IAxisValueFormatter {

        private List<String> mValues;

        public MyXAxisValueFormatter(List<String> values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            // "value" represents the position of the label on the axis (x or y)
            int index = (int)value;
            String fValue="";
            if (index >=0 && index < mValues.size()) {
                Date date = ConvertUtils.stringToDate(mValues.get((int) value));
                if (date != null) {
                    fValue = new SimpleDateFormat("dd/MM/yyyy").format(date);
                }
            }
            return fValue;
        }


    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDIT_ACHIEVEMENTS) {
            if (resultCode == Activity.RESULT_OK) {
                fetchStudentAchievement();
            }
        }
    }
}
