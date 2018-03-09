package dobrowol.styloweplywanie.teammanagement.trainingdetails;

import android.content.Context;

import dobrowol.styloweplywanie.utils.CsvDataUtils;
import dobrowol.styloweplywanie.utils.LanguageUtils;
import dobrowol.styloweplywanie.utils.StudentAchievement;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StudentAchievementUtils {
    private ArrayList<StudentAchievement> studentAchievements;
    private Set<Key> keys;
    private CsvDataUtils csvDataUtils;
    private String dataFile;
    public static class Key{
        public String style;
        public String distance;
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
        public Value(String date, String strokeIndex){
            this.date = date;
            this.strokeIndex = strokeIndex;
        }
        public String date;
        public String strokeIndex;
    }

    public StudentAchievementUtils(CsvDataUtils csvDataUtils, String dataFile) {
            this.csvDataUtils = csvDataUtils;
            studentAchievements = null;
            this.dataFile = dataFile;
            keys = null;
    }

    public Set<Key> fetchUniqueKeys()
    {
        if (keys != null)
            return keys;

        keys = new HashSet<>();
        if (studentAchievements == null) {
            studentAchievements = csvDataUtils.getStudentAchievements(dataFile);
        }
        for (StudentAchievement studentAchievement : studentAchievements) {
            Key k = new Key(studentAchievement.style, studentAchievement.distance);
            keys.add(new Key(studentAchievement.style, studentAchievement.distance));
        }
        return keys;
    }
    public List<Value> fetchStudentAchievementForKey(Key k)
    {
        List<Value> values = new ArrayList<>();
        for (StudentAchievement studentAchievement : studentAchievements) {
            if (studentAchievement.distance.equals(k.distance) && studentAchievement.style.equals( k.style)){
                values.add(new Value(studentAchievement.date, studentAchievement.strokeIndex.toString()));
            }
        }
        return values;
    }
    public Map<Key, List<Value>> fetchStudentAchievement() {
        if (studentAchievements == null) {
            studentAchievements = csvDataUtils.getStudentAchievements(dataFile);
        }
        Map<Key, List<Value>> achievementsMap = new HashMap<>();
        for (StudentAchievement studentAchievement : studentAchievements) {
            Key k = new Key(studentAchievement.style, studentAchievement.distance);

            ArrayList<Value> values;
            if (achievementsMap.containsKey(k)) {
                values = (ArrayList<Value>) achievementsMap.get(k);

            } else {
                values = new ArrayList<Value>(1);
            }
            Value v = new Value(studentAchievement.date, studentAchievement.strokeIndex.toString());
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