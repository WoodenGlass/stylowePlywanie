package com.example.dobrowol.styloweplywanie.utils;

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
        int s = time2seconds(time);

        distance = distance.replace("m", "");
        Float d = Float.valueOf(distance);
        Float stc = Float.valueOf(strokeCount);
        strokeIndex = d/s *((d/stc));
    }
    int time2seconds(String time)
    {
        int [] factor=
                {
                    600,
                    60,
                    10,
                    1
                };
        int seconds =  0;
        int index = 0;
        for (char i : time.toCharArray())
        {
            if (index >= factor.length)
                break;
            if (i <= '9' && i >= '0') {
                seconds += (i - '0') * factor[index++];
            }
        }
        return seconds;
    }
}
