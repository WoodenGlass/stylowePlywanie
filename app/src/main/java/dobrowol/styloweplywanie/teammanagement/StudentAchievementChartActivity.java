package dobrowol.styloweplywanie.teammanagement;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import dobrowol.styloweplywanie.R;
import dobrowol.styloweplywanie.teammanagement.trainingdetails.StudentAchievementUtils;
import dobrowol.styloweplywanie.utils.CsvDataUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static dobrowol.styloweplywanie.R.id.chart;

/**
 * Created by dobrowol on 10.02.18.
 */

public class StudentAchievementChartActivity extends AppCompatActivity {

    private StudentAchievementUtils studentAchievementUtils;
    private LineChart lineChart;
    private String dataFile;
    private static final String KEY = "DataFile";
    private Spinner labelSpinner;
    private ArrayList<String> currentXaxisLabels;
    private Map<Integer, StudentAchievementUtils.Key> positionToKey;
    private Map<StudentAchievementUtils.Key, List<StudentAchievementUtils.Value>> achievementsMap;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_studentachievementchart);
        lineChart = (LineChart) findViewById(chart);
        labelSpinner = (Spinner) findViewById(R.id.labelsSpinner);
         // add entries to dataset
        //dataSet.setColor(0x2db82d);
        studentAchievementUtils = null;
        //dataSet.setValueTextColor(0xFFFA);

        Intent intent = getIntent();
        if (intent != null & intent.hasExtra(KEY)) {
            dataFile= intent.getExtras().getString(KEY);
            studentAchievementUtils = new StudentAchievementUtils(new CsvDataUtils(getApplicationContext()), dataFile);
        }

        currentXaxisLabels = new ArrayList<>();
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
        x.setLabelCount(dataSet.getEntryCount());
        x.setLabelRotationAngle(45);
        x.setValueFormatter(new MyXAxisValueFormatter(currentXaxisLabels));

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
    private LineDataSet getLineDataSet(Map<StudentAchievementUtils.Key, List<StudentAchievementUtils.Value>> achievementsMap, int position)
    {
        LineDataSet lineDataSet;
        StudentAchievementUtils.Key k = positionToKey.get(position);
        List<StudentAchievementUtils.Value> values = studentAchievementUtils.fetchStudentAchievementForKey(positionToKey.get(position));

        List<Entry> entries = new ArrayList<Entry>();

        int i = 0;
            // turn your data into Entry objects
        for (StudentAchievementUtils.Value v : values) {
            entries.add(new Entry(i++, Float.valueOf(v.strokeIndex)));
            currentXaxisLabels.add(v.date);
        }

        lineDataSet =  new LineDataSet(entries, "Stroke index of " + k.style + " " + k.distance);

        return lineDataSet;
    }
    private void fetchStudentAchievement()
    {
        if (studentAchievementUtils == null)
            return;
        Set<StudentAchievementUtils.Key> achievementsCategories = studentAchievementUtils.fetchUniqueKeys();
        //LineDataSet dataSet = lineDataSets.get(0);
        //for (LineDataSet dataSet : lineDataSets){
        //Toast.makeText(getApplicationContext(), dataSet.getLabel()+" "+dataSet.getEntryCount(), Toast.LENGTH_SHORT).show();

        ArrayList<String> labels = new ArrayList<>();
        positionToKey = new HashMap<>();
        int i = 0;
        for (StudentAchievementUtils.Key key : achievementsCategories){
            labels.add("Stroke index of " + key.style + " " + key.distance);
            positionToKey.put(i++, key);
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
                        drawLineChart(getLineDataSet(achievementsMap,position));

                        //Toast.makeText(getApplicationContext(),"You have selected "+distances[+position],Toast.LENGTH_SHORT).show();
                        // TODO Auto-generated method stub
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub

                    }

                });

        //}
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
                Date date;
                try {
                    date = new SimpleDateFormat("yyyyMMdd_HHmmss").parse(mValues.get((int) value));
                    fValue = new SimpleDateFormat("yyyy/MM/dd").format(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            return fValue;
        }


    }
}