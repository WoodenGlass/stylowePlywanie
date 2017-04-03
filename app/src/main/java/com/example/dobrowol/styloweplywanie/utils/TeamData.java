package com.example.dobrowol.styloweplywanie.utils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dobrowol on 16.03.17.
 */
public class TeamData implements Serializable{
    public String teamName;
    public String coachName;
    public ArrayList<StudentData> students;

    TeamData(String team, String coach)
    {
        teamName = team;
        coachName = coach;
        students = new ArrayList<StudentData>();
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
