package dobrowol.styloweplywanie.utils;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dobrowol on 01.02.18.
 */

public class StudentAchievement implements Serializable,  Comparable<StudentAchievement> {
    public String style;
    public String distance;
    public String strokeCount;
    public String time;
    public String date;
    public Float strokeIndex;
    private String pattern;
    public String poolSize;

    public StudentAchievement()
    {
        pattern = "dd/MM/yyyy";
    }
    public void setDate()
    {
        date = new SimpleDateFormat(pattern).format(Calendar.getInstance().getTime());
    }
    public void setDate(Date aDate)
    {
        if (aDate != null) {
            date = new SimpleDateFormat(pattern).format(aDate);
        }
    }
    public Date getDate()
    {
        if (date != null && date != "")
        {
            try {
                return new SimpleDateFormat(pattern).parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public void setDate(String dateStr)
    {
        String formattedDate;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
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
            formattedDate = new SimpleDateFormat(pattern).format(d);
            return formattedDate;
        }
        return "";
    }
    private boolean isStrokeCountValid()
    {
        try
        {
            // checking valid float using parseInt() method
            Integer.parseInt(strokeCount);
            return true;
        }
        catch (NumberFormatException e)
        {
            return false;
        }
    }
    public void calculateStrokeIndex()
    {
        if (!isStrokeCountValid())
        {
            return;
        }
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
    public void setTime(String given_time)
    {
        time = extractTime(given_time);
    }
    private Integer matchFirstPattern(String time)
    {
        Integer extractedTime = 0;
        Integer[]miliBase = {100,10,1};
        String expectedPattern = "([0-9]+):([0-9]+).([0-9]+)";
        Pattern p = Pattern.compile(expectedPattern,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(time);
        if (m.find())
        {
            String mins = m.group(1);
            extractedTime += Integer.parseInt(mins)*60000;
            String secs = m.group(2);
            extractedTime += Integer.parseInt(secs)*1000;
            String millis = m.group(3);
            int j =0;
            for (char i : millis.toCharArray()) {
                if (i <= '9' && i >= '0') {
                    extractedTime += (i - '0') * miliBase[j++];
                }
            }
            return extractedTime;
        }
        return -1;
    }
    private Integer matchSecondPattern(String time)
    {
        Integer extractedTime = 0;
        String expectedPattern = "([0-9]+):([0-9]+)";
        Pattern p = Pattern.compile(expectedPattern,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(time);
        if (m.find())
        {
            String mins = m.group(1);
            extractedTime += Integer.parseInt(mins)*60000;
            String secs = m.group(2);
            extractedTime += Integer.parseInt(secs)*1000;
            return extractedTime;
        }
        return -1;
    }
    private Integer matchThirdPattern(String time)
    {
        Integer extractedTime = 0;
        Integer[]miliBase = {100,10,1};
        String expectedPattern = "([0-9]+).([0-9]+)";
        Pattern p = Pattern.compile(expectedPattern,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(time);
        if (m.find())
        {
            String secs = m.group(1);
            extractedTime += Integer.parseInt(secs)*1000;
            String millis = m.group(2);
            int j =0;
            for (char i : millis.toCharArray()) {
                if (i <= '9' && i >= '0') {
                    extractedTime += (i - '0') * miliBase[j++];
                }
            }
            return extractedTime;
        }
        return -1;
    }
    private String extractTime(String time) {

        Integer extractedTime = 0;
        Integer[]base = {1000*60, 1000};

        if (time.matches("([0-9]+):([0-9]+)\\.([0-9]+)")) {
            extractedTime = matchFirstPattern(time);
        }
        else if (time.matches("([0-9]+):([0-9]+)")) {
            extractedTime = matchSecondPattern(time);
        }
        else if (time.matches("([0-9]+)\\.([0-9]+)")) {
                extractedTime = matchThirdPattern(time);
        }

        return extractedTime.toString();
    }
    public String formatTime()
    {
        return ConvertUtils.formatTime(time);
    }
    int time2seconds(String time)
    {
        if (time == null)
            return 0;
        Integer seconds = Integer.parseInt(time);
        return seconds%1000;
    }

    @Override
    public int compareTo(@NonNull StudentAchievement studentAchievement) {
        if (getDate() == null || studentAchievement.getDate() == null)
            return 0;
        return getDate().compareTo(studentAchievement.getDate());
    }
}
