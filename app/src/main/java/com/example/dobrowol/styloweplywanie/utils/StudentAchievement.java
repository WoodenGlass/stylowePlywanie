package com.example.dobrowol.styloweplywanie.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dobrowol on 01.02.18.
 */

public class StudentAchievement {
    public String style;
    public String distance;
    public String strokeCount;
    public String time;
    public String date;
    public Float strokeIndex;
    void calculateStrokeIndex()
    {
        DateFormat formatter = new SimpleDateFormat("mm:ss.SSS");
        try {
            Date date = formatter.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Integer ms = Integer.valueOf(time);
        Float d = Float.valueOf(distance);
        Float stc = Float.valueOf(strokeCount);
        strokeIndex = d/ms *((d/stc));
    }

}
