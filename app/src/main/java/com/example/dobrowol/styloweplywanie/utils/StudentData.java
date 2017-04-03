package com.example.dobrowol.styloweplywanie.utils;

import java.io.Serializable;

/**
 * Created by dobrowol on 01.04.17.
 */
public class StudentData implements Serializable{
    private String name;
    private String surname;
    private String age;

    public void setAge(String age) {
        this.age = age;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    public void setName(String nameText) {
        name = nameText;
    }
}
