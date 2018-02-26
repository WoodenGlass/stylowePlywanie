package dobrowol.styloweplywanie.utils;

import java.io.Serializable;

/**
 * Created by dobrowol on 01.02.18.
 */

public class StudentAchievement implements Serializable {
    public String style;
    public String distance;
    public String strokeCount;
    public String time;
    public String date;
    public Float strokeIndex;
    public void calculateStrokeIndex()
    {
        int s = time2seconds(time);

        if (distance == null) {
            strokeIndex = Float.valueOf(0);
            return;
        }
        distance = distance.replace("m", "");
        Float d = Float.valueOf(distance);
        Float stc = Float.valueOf(strokeCount);
        if (s > 0 && stc > 0) {
            strokeIndex = d / s * ((d / stc));
        }
        else
            strokeIndex = Float.valueOf(0);
    }
    int time2seconds(String time)
    {
        if (time == null)
            return 0;
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
