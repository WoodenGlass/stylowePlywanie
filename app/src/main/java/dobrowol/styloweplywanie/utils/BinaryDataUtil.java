package dobrowol.styloweplywanie.utils;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by dobrowol on 05.04.17.
 */
public class BinaryDataUtil implements IDataUtil {
    private Context context;
    private String prefix="stylowe_";

    public BinaryDataUtil(Context context)
    {
        this.context = context;
    }
    @Override
    public void saveTeamData(TeamData teamData) {
        String filename = prefix + teamData.getTeamName()+".bin";
        File file = new File(context.getFilesDir(), filename);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(teamData);
            oos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public TeamData retrieveTeamData(String teamName) {
        String filename = prefix + teamName+".bin";
        File file = new File(context.getFilesDir(), filename);
        return getTeamData(file);
    }

    @Override
    public ArrayList<TeamData> getTeams() {
        ArrayList<TeamData> returnedList = new ArrayList<TeamData>();
        File[] foundFiles = getFiles();

        for (File file : foundFiles) {
            returnedList.add(getTeamData(file));
        }
        return returnedList;
    }

    @Override
    public void clearCache() {
        File[] foundFiles = getFiles();
        for (File file : foundFiles)
        {
            file.delete();
        }
    }

    @Override
    public void removeTeam(String teamName) {
        String filename = prefix + teamName+".xml";
        File file = new File(context.getFilesDir(), filename);
        file.delete();
    }

    private File[] getFiles() {
        File dir = new File(context.getFilesDir(), ".");
        return dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir1, String name) {
                return name.startsWith(prefix);
            }
        });
    }

    private TeamData getTeamData(File file) {
        TeamData teamData = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            teamData = (TeamData) ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return teamData;
    }
}
