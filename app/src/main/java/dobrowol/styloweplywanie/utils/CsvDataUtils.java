package dobrowol.styloweplywanie.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dobrowol on 01.02.18.
 */

public class CsvDataUtils {
    private Context context;

    public CsvDataUtils(Context context)
    {
        this.context = context;
    }

    private String prepareLine(StudentAchievement data)
    {
        return data.date + "," + data.style + "," + data.distance + ","+data.time+","+data.strokeCount+","+data.strokeIndex;
    }
    public void overwrite(List<StudentAchievement> list, String dataFile)
    {
        List<String> allLines = new ArrayList<>();
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(dataFile,Context.MODE_PRIVATE);


            PrintWriter pw = new PrintWriter(outputStream);
            for (StudentAchievement s : list)
            {
                pw.append( prepareLine(s));
                pw.println();
            }


            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveStudentAchievement(StudentAchievement data, String dataFile)
    {
            data.calculateStrokeIndex();
            String outputString = prepareLine(data);
            FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(dataFile, Context.MODE_APPEND);

            PrintWriter pw = new PrintWriter(outputStream);
            pw.append(outputString);
            pw.println();
            pw.flush();
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<StudentAchievement> getStudentAchievements(String dataFile)
    {
        ArrayList<StudentAchievement> achievements = new ArrayList<>();
        BufferedReader br = null;
        try {
            File file = new File(context.getFilesDir(), dataFile);
            br = new BufferedReader(new FileReader(file));
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
                sa.setTime(tokens[3]);
                sa.strokeCount = tokens[4];
                try {
                    sa.strokeIndex = Float.valueOf(tokens[5]);
                }
                catch (NumberFormatException nfe)
                {
                    System.out.print("NumberFormatException: "+nfe.getMessage());
                    sa.strokeIndex = 0.0f;
                }
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
