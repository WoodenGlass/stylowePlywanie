package dobrowol.styloweplywanie.utils;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.util.function.Predicate;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by dobrowol on 03.12.17.
 */
@RunWith(MockitoJUnitRunner.class)
public class XMLDataUtilTest {
    private String testTeamName = "Narwale";
    @Mock
    Context contextMock;
    @InjectMocks
    XMLDataUtil sut;
    @Test
    public void saveTeamData() throws Exception {
        File file = new File("./");
        when(contextMock.getFilesDir()).thenReturn(file);
        TeamData teamData = new TeamData(testTeamName,"Coach");
        StudentData studentData = new StudentData();
        studentData.name = "Bartek";
        studentData.setAge("26.05.2007");
        studentData.surname = "Mąka";
        teamData.addStudent(studentData);
        teamData.styles.add("kraul");
        teamData.styles.add("żabka");
        teamData.distances.add("100m");
        teamData.distances.add("50m");
        sut.saveTeamData(teamData);

    }

    @Test
    public void retrieveTeamData() throws Exception {
        File file = new File("./");
        when(contextMock.getFilesDir()).thenReturn(file);
        TeamData teamData = sut.retrieveTeamData(testTeamName);
        assertNotNull(teamData);
        assertEquals(teamData.students.size(), 1);

        StudentData studentData = teamData.students.get(0);
        assertEquals(studentData.name, "Bartek");
        assertEquals(studentData.surname, "Mąka");
        assertEquals(studentData.age, "26.05.2007");
        assertEquals(teamData.styles.size(),2);
        assertTrue(teamData.students.contains("kraul"));

    }
    @Test
    public void retrieveTeamDataWithEmptyTeamName() throws Exception {
        File file = new File("./");
        when(contextMock.getFilesDir()).thenReturn(file);
        TeamData teamData = sut.retrieveTeamData("");
        assertEquals(teamData, null);
    }
    @Test
    public void getTeams() throws Exception {

    }

    @Test
    public void clearCache() throws Exception {

    }

}