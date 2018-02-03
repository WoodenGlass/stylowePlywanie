package com.example.dobrowol.styloweplywanie.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by dobrowol on 01.02.18.
 */

public class CsvDataUtil {
    private Context context;

    public CsvDataUtil(Context context)
    {
        this.context = context;
    }

    public void saveStudentAchievement(StudentAchievement data, String dataFile)
    {
        try
        {
            FileWriter fw = new FileWriter(dataFile, true);

            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            String outputString = data.date + "," + data.style + "," + data.distance + ","+data.time+","+data.strokeCount+","+data.strokeIndex;

            out.println(outputString);

        } catch (IOException e) {
            //exception handling left as an exercise for the reader
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
