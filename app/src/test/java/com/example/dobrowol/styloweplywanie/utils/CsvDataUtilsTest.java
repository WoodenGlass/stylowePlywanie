package com.example.dobrowol.styloweplywanie.utils;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.mockito.Mockito.when;

/**
 * Created by dobrowol on 04.02.18.
 */


@RunWith(MockitoJUnitRunner.class)
public class CsvDataUtilsTest {
    private String testCoachName = "Treneiro";
    private String testTeamName = "Narwale";
    private String testFile = "agatkakmiecik.csv";
    @Mock
    Context contextMock;
    @InjectMocks
    CsvDataUtils sut;

    @Test
    public void saveAchievement() throws Exception {
        FileOutputStream outputStream = new FileOutputStream(new File("./",testFile));
        when(contextMock.openFileOutput(testFile, Context.MODE_PRIVATE)).thenReturn(outputStream);
        StudentAchievement sa = new StudentAchievement();
        sa.date = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        sa.strokeCount = "12";
        sa.time = "00:15.000";
        sa.style = "kraul";
        sa.distance = "25m";
        sut.saveStudentAchievement(sa, testFile);


    }
}