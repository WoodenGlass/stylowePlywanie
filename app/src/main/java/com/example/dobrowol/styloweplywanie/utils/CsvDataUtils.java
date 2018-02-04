package com.example.dobrowol.styloweplywanie.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by dobrowol on 01.02.18.
 */

public class CsvDataUtils {
    private Context context;

    public CsvDataUtils(Context context)
    {
        this.context = context;
    }

    public void saveStudentAchievement(StudentAchievement data, String dataFile)
    {
            data.calculateStrokeIndex();
            String outputString = data.date + "," + data.style + "," + data.distance + ","+data.time+","+data.strokeCount+","+data.strokeIndex;
            FileOutputStream outputStream;

            try {
                outputStream = context.openFileOutput(dataFile, Context.MODE_PRIVATE);
                outputStream.write(outputString.getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }




    }
    public ArrayList<StudentAchievement> getStudentAchievements(String dataFile)
    {
        ArrayList<StudentAchievement> achievements = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(dataFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {

            String line = br.readLine();

            while (line != null) {
                StudentAchievement sa = new StudentAchievement();
                String[] tokens = line.split(",");
                sa.date = tokens[0];
                sa.style = tokens[1];
                sa.distance = tokens[2];
                sa.time = tokens[3];
                sa.strokeCount = tokens[4];
                sa.strokeIndex = Float.valueOf(tokens[5]);
                achievements.add(sa);
                line = br.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return achievements;
    }


}
