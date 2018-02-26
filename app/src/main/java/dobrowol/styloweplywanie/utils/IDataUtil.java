package dobrowol.styloweplywanie.utils;

import java.util.ArrayList;

/**
 * Created by dobrowol on 05.04.17.
 */
public interface IDataUtil {
    void saveTeamData(TeamData teamData);

    TeamData retrieveTeamData(String teamName);

    ArrayList<TeamData> getTeams();

    void clearCache();

    void removeTeam(String teamName);
}
