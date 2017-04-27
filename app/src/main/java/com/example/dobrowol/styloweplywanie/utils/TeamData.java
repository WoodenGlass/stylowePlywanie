package com.example.dobrowol.styloweplywanie.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dobrowol on 16.03.17.
 */
public class TeamData implements Serializable{
    public String teamName;
    public String coachName;
    public ArrayList<StudentData> students;
    public Map<Date, ArrayList<TrainingData>> trainings;

    TeamData(String team, String coach)
    {
        teamName = team;
        coachName = coach;
        students = new ArrayList<StudentData>();
        trainings = new HashMap<>();
    }
    public void addCoach(String text) {

    }
    public ArrayList<StudentData> getStudents()
    {
        return students;
    }
    public String getTeamName()
    {
        return teamName;
    }
    public String getCoachName() {return  coachName;}

    public void addStudent(StudentData studentData) {
        students.add(studentData);
    }

    public int getSize() {
        return students.size();
    }
}
