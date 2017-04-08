package com.example.dobrowol.styloweplywanie.utils;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by dobrowol on 16.03.17.
 */
public class TeamDataUtils implements ITeamDataUtils {
    private final String prefix = "stylowe_";
    private IDataUtil dataUtil;

    public TeamDataUtils(Context cntxt)
    {
        dataUtil = new XMLDataUtil(cntxt);
    }
    @Override
    public TeamData addTeam(String teamName, String coachName) {
        TeamData teamData = new TeamData(teamName, coachName);
        dataUtil.saveTeamData(teamData);
        return teamData;
    }

    @Override
    public ArrayList<TeamData> getTeams() {

        return dataUtil.getTeams();
    }

    @Override
    public void updateTeam(TeamData teamData) {
        saveTeamData(teamData);
    }

    private void saveTeamData(TeamData teamData) {
        dataUtil.saveTeamData(teamData);
    }

    public TeamData getTeam(String teamName)
    {
        return dataUtil.retrieveTeamData(teamName);
    }

    public void clearCache()
    {
        dataUtil.clearCache();

    }
}
