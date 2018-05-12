package dobrowol.styloweplywanie.utils;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    public void setDate()
    {
        date = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
    }
    public void setDate(Date aDate)
    {
        if (aDate != null) {
            date = new SimpleDateFormat("dd/MM/yyyy").format(aDate);
        }
    }
    public void setDate(String dateStr)
    {
        String formattedDate;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if (dateStr != null) {
            if ((formattedDate = sdf.format(dateStr)) != null) {
                date = formattedDate;
            }
            else if ((formattedDate = new SimpleDateFormat("yyyyMMdd_HHmmss").format(dateStr))!= null)
            {
                date = formattedDate;
            }
            else {
                setDate();
            }
        }
    }

    public String displayDate()
    {
        String formattedDate;

        Date d = ConvertUtils.stringToDate(date);
        if (d != null) {
            formattedDate = new SimpleDateFormat("dd/MM/yyyy").format(d);
            return formattedDate;
        }
        return "";
    }
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
