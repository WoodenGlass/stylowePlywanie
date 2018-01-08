package com.example.dobrowol.styloweplywanie.utils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by dobrowol on 01.04.17.
 */
public class StudentData implements Serializable{
    public String name;
    public String surname;
    public String age;
    public String dataFile;

    public void setAge(Calendar dateOfBirth) {
        String formatted="";
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        if (dateOfBirth != null) {
            formatted = sdf.format(dateOfBirth.getTime());
        }
        this.age = formatted;
    }
    public void setAge(String dateOfBirth)
    {

        age=dateOfBirth;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    public void setName(String nameText) {
        name = nameText;
    }
}
