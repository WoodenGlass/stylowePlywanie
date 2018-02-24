package com.example.dobrowol.styloweplywanie.teammanagement.trainingdetails;

import android.content.Context;

import com.example.dobrowol.styloweplywanie.utils.CsvDataUtils;
import com.example.dobrowol.styloweplywanie.utils.StudentAchievement;
import com.github.mikephil.charting.data.LineDataSet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by dobrowol on 23.02.18.
 */
public class StudentAchievementUtilsTest {
    private String dataFile="AlfredBanka.csv";
    Context contextMock;
    @Mock
    CsvDataUtils csvDataUtils;
    @InjectMocks
    StudentAchievementUtils sut;
    @Before
    public void init_tested_and_mocks() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void fetchStudentAchievement() throws Exception {
        ArrayList<StudentAchievement> studentAchievements = new ArrayList<>(4);
        StudentAchievement studentAchievement = new StudentAchievement();
        studentAchievement.distance = "100";
        studentAchievement.style = "kraul";
        studentAchievement.strokeIndex=1.2f;
        studentAchievement.date = "2018_13.06_16:45.00";

        StudentAchievement studentAchievement2 = new StudentAchievement();
        studentAchievement2.distance = "100";
        studentAchievement2.style = "kraul";
        studentAchievement2.strokeIndex=2.2f;
        studentAchievement2.date = "2018_12.06_16:45.00";

        StudentAchievement studentAchievement3 = new StudentAchievement();
        studentAchievement3.distance = "25";
        studentAchievement3.style = "żabka";
        studentAchievement3.strokeIndex=2.2f;
        studentAchievement3.date = "2018_12.06_16:45.00";

        StudentAchievement studentAchievement4 = new StudentAchievement();
        studentAchievement4.distance = "25";
        studentAchievement4.style = "żabka";
        studentAchievement4.strokeIndex=2.2f;
        studentAchievement4.date = "2018_12.06_16:45.00";

        StudentAchievement studentAchievement5 = new StudentAchievement();
        studentAchievement5.distance = "25";
        studentAchievement5.style = "żabka";
        studentAchievement5.strokeIndex=2.2f;
        studentAchievement5.date = "2018_12.06_16:45.00";

        studentAchievements.add(studentAchievement);
        studentAchievements.add(studentAchievement2);

        when(csvDataUtils.getStudentAchievements(dataFile)).thenReturn(studentAchievements);
        Map<StudentAchievementUtils.Key, List<StudentAchievementUtils.Value>> achievementsMap = new HashMap<StudentAchievementUtils.Key, List<StudentAchievementUtils.Value>>();
        sut = new StudentAchievementUtils(csvDataUtils);
        achievementsMap = sut.fetchStudentAchievement(contextMock, dataFile);

        StudentAchievementUtils.Key k  = new StudentAchievementUtils.Key("kraul", "100");

        assertTrue(achievementsMap.containsKey(k));
        assertEquals(achievementsMap.get(k).size(),2);

        List<LineDataSet> lineDataSetArray = sut.getLineDataSets(achievementsMap);
        assertEquals(lineDataSetArray.size(), 1);
    }
    @Test
    public void fetchDifferentStudentAchievement() throws Exception {
        ArrayList<StudentAchievement> studentAchievements = new ArrayList<>(4);
        StudentAchievement studentAchievement = new StudentAchievement();
        studentAchievement.distance = "100";
        studentAchievement.style = "żabka";
        studentAchievement.strokeIndex=1.2f;
        studentAchievement.date = "2018_13.06_16:45.00";

        StudentAchievement studentAchievement2 = new StudentAchievement();
        studentAchievement2.distance = "100";
        studentAchievement2.style = "kraul";
        studentAchievement2.strokeIndex=2.2f;
        studentAchievement2.date = "2018_12.06_16:45.00";

        studentAchievements.add(studentAchievement);
        studentAchievements.add(studentAchievement2);

        when(csvDataUtils.getStudentAchievements(dataFile)).thenReturn(studentAchievements);
        Map<StudentAchievementUtils.Key, List<StudentAchievementUtils.Value>> achievementsMap = new HashMap<StudentAchievementUtils.Key, List<StudentAchievementUtils.Value>>();
        sut = new StudentAchievementUtils(csvDataUtils);
        achievementsMap = sut.fetchStudentAchievement(contextMock, dataFile);

        StudentAchievementUtils.Key k  = new StudentAchievementUtils.Key("kraul", "100");

        assertTrue(achievementsMap.containsKey(k));
        assertEquals(achievementsMap.get(k).size(),1);
    }
}