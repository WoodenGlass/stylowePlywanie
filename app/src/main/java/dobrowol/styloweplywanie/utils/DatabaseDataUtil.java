package dobrowol.styloweplywanie.utils;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.ArrayList;


public class DatabaseDataUtil implements IDataUtil{

    public DatabaseDataUtil(Context context)
    {
    }
    @Override
    public void saveTeamData(TeamData teamData) {

    }

    @Override
    public TeamData retrieveTeamData(String teamName) {
        return null;
    }

    @Override
    public ArrayList<TeamData> getTeams() {
        return null;
    }

    @Override
    public void clearCache() {

    }

    @Override
    public void removeTeam(String teamName) {

    }
}
