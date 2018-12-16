package dobrowol.styloweplywanie.teammanagement.trainingdetails;

import android.content.Context;
import android.content.res.Resources;

import dobrowol.styloweplywanie.utils.CsvDataUtils;
import dobrowol.styloweplywanie.utils.LanguageUtils;
import dobrowol.styloweplywanie.utils.StudentAchievement;
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
        public String style;
        public String distance;
        Key(String style, String distance){
            this.style = LanguageUtils.removePolishSigns(style);
            this.distance = distance;}
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
        public String date;
        public String strokeIndex;
        public String time;
    }

    public StudentAchievementUtils(CsvDataUtils csvDataUtils) {
            this.csvDataUtils = csvDataUtils;
    }

    public Map<Key, List<StudentAchievement>> fetchStudentAchievement(String dataFile) {
        ArrayList<StudentAchievement> studentAchievements = csvDataUtils.getStudentAchievements(dataFile);
        Map<Key, List<StudentAchievement>> achievementsMap = new HashMap<>();
        for (StudentAchievement studentAchievement : studentAchievements) {
            Key k = new Key(studentAchievement.style, studentAchievement.distance);

            ArrayList<StudentAchievement> values;
            if (achievementsMap.containsKey(k)) {
                values = (ArrayList<StudentAchievement>) achievementsMap.get(k);

            } else {
                values = new ArrayList<StudentAchievement>(1);
            }
            values.add(studentAchievement);
            achievementsMap.put(k, values);
        }
        return achievementsMap;
    }
    public List<StudentAchievement> getBestResults(String dataFile)
    {
        List<StudentAchievement> bestResults = new ArrayList<>();
        Map<Key, List<StudentAchievement>> all = fetchStudentAchievement(dataFile);

        Iterator it = all.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            StudentAchievementUtils.Key k = (Key) pair.getKey();
            List<StudentAchievement> values = (List<StudentAchievement>) pair.getValue();

            List<Entry> entries = new ArrayList<Entry>();

            int i = 0;
            StudentAchievement best = new StudentAchievement();
            int min = 1000000000;
            // turn your data into Entry objects
            for (StudentAchievement v : values) {
                int time = Integer.parseInt(v.time);
                if (time < min) {
                    min = time;
                    best = v;
                }

            }
            bestResults.add(best);
        }

        return bestResults;
    }
    public List<LineDataSet> getLineDataSets(Map<Key, List<StudentAchievement>> achievementsMap)
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