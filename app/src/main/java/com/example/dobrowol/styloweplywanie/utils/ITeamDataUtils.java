package com.example.dobrowol.styloweplywanie.utils;

import java.util.List;

/**
 * Created by dobrowol on 20.03.17.
 */
public interface ITeamDataUtils {
    TeamData addTeam(String teamName, String coachName);

    List<TeamData> getTeams();

    void updateTeam(TeamData teamData);

    void removeTeam(String teamName);
}
