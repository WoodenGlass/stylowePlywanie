package com.example.dobrowol.styloweplywanie.teammanagement.trainingdetails;

import android.content.Context;

import com.example.dobrowol.styloweplywanie.utils.CsvDataUtils;
import com.example.dobrowol.styloweplywanie.utils.LanguageUtils;
import com.example.dobrowol.styloweplywanie.utils.StudentAchievement;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class StudentAchievementUtils {
    private CsvDataUtils csvDataUtils;
    public static class Key{
        String style;
        String distance;
        Key(String style, String distance){this.style = LanguageUtils.removePolishSigns(style); this.distance = distance;}
        public int hashCode() {
            return (style+distance).hashCode();
        }
        public boolean equals(Object obj){
            Key k = (Key) obj;
            boolean res = style.equals(k.style)&&distance.equals(k.distance);
            return res;

        }
    };
    public static class Value {
        String date;
        String strokeIndex;
    }

    public StudentAchievementUtils(CsvDataUtils csvDataUtils) {
            this.csvDataUtils = csvDataUtils;
    }

    public Map<Key, List<Value>> fetchStudentAchievement(Context applicationContext, String dataFile) {
        ArrayList<StudentAchievement> studentAchievements = csvDataUtils.getStudentAchievements(dataFile);
        Map<Key, List<Value>> achievementsMap = new HashMap<>();
        for (StudentAchievement studentAchievement : studentAchievements) {
            Key k = new Key(studentAchievement.style, studentAchievement.distance);

            ArrayList<Value> values;
            if (achievementsMap.containsKey(k)) {
                values = (ArrayList<Value>) achievementsMap.get(k);

            } else {
                values = new ArrayList<Value>(1);
            }
            Value v = new Value();
            v.date = studentAchievement.date;
            v.strokeIndex = studentAchievement.strokeIndex.toString();
            values.add(v);
            achievementsMap.put(k, values);
        }
        return achievementsMap;
    }
    public List<LineDataSet> getLineDataSets(Map<Key, List<Value>> achievementsMap)
    {
        ArrayList<LineDataSet> lineDataSetArray = new ArrayList<>();
        Iterator it = achievementsMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            StudentAchievementUtils.Key k = (StudentAchievementUtils.Key) pair.getKey();
            ArrayList<StudentAchievementUtils.Value> values = (ArrayList<StudentAchievementUtils.Value>) pair.getValue();
            it.remove(); // avoids a ConcurrentModificationException
            List<Entry> entries = new ArrayList<Entry>();

            int i = 0;
            // turn your data into Entry objects
            for (StudentAchievementUtils.Value v : values) {
                entries.add(new Entry(i++, Float.valueOf(v.strokeIndex)));
            }

            lineDataSetArray.add( new LineDataSet(entries, "Stroke index of " + k.style + " " + k.distance));
        }
        return lineDataSetArray;
    }
}