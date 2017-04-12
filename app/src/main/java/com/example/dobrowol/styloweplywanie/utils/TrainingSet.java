package com.example.dobrowol.styloweplywanie.utils;

public class TrainingSet {
    public int distance;
    public SwimmingStyles style;
    public String excercise;
    public String description;
    public TrainingSet(int dst, SwimmingStyles st, String desc)
    {
        distance = dst;
        style = st;
        description = desc;
    }
}
