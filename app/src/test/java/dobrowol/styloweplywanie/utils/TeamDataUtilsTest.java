package dobrowol.styloweplywanie.utils;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.when;

/**
 * Created by dobrowol on 23.03.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class TeamDataUtilsTest {
    private String testCoachName = "Treneiro";
    private String testTeamName = "Narwale";
    @Mock
    Context contextMock;
    @InjectMocks
    TeamDataUtils sut;
    @Test
    public void addTeam() throws Exception {
        File file = new File("./");
        when(contextMock.getFilesDir()).thenReturn(file);
        sut.addTeam(testTeamName, testCoachName);
        TeamData teamData = sut.getTeam(testTeamName);
        assertNotNull(teamData);

    }
    @Test
    public void addPolishTeam() throws Exception {
        File file = new File("./");
        when(contextMock.getFilesDir()).thenReturn(file);
        String testPolishTeamName = "Kuźnia";
        sut.addTeam(testPolishTeamName, testCoachName);
        sut.removeTeam(testPolishTeamName);
        TeamData teamData = sut.getTeam(testPolishTeamName);
        assertNull(teamData);
    }
    @Test
    public void getTeam() throws Exception{
        File file =  new File("./");
        when(contextMock.getFilesDir()).thenReturn(file);
        //sut.addTeam(testTeamName, testCoachName);
        TeamData teamData = sut.getTeam(testTeamName);
        assertNotNull(teamData);
        assertEquals(teamData.getCoachName(), testCoachName);

    }

    @Test
    public void getTeams() throws Exception {
        File file = new File("./");
        when(contextMock.getFilesDir()).thenReturn(file);

        sut.addTeam(testTeamName + "1", testCoachName + "1");
        sut.addTeam(testTeamName + "2", testCoachName + "2");
        sut.addTeam(testTeamName + "3", testCoachName + "3");

        ArrayList<TeamData> teams = sut.getTeams();

        assertNotNull(teams);
        assertEquals(teams.size(), 4);

    }

    @Test
    public void updateTeams() throws Exception {
        File file =  new File("./");
        when(contextMock.getFilesDir()).thenReturn(file);
        //sut.addTeam(testTeamName, testCoachName);
        TeamData teamData = sut.getTeam(testTeamName);
        assertNotNull(teamData);
        StudentData studentData = new StudentData();
        studentData.name = "agatka";
        studentData.surname = "kmmiecik";
        studentData.setAge("03.05.2004");

        teamData.addStudent(studentData);
        sut.updateTeam(teamData);

        teamData = sut.getTeam(testTeamName);
        assertEquals(teamData.students.size(), 1);
    }

}