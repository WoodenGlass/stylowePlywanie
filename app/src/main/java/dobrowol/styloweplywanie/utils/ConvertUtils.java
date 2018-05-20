package dobrowol.styloweplywanie.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dobrowol on 25.03.18.
 */

public class ConvertUtils {
    public static Date stringToDate(String date) {
        Date d = null;
        if (date.matches("([0-9]{2})/([0-9]{2})/([0-9]{4})")) {
            try {
                d = new SimpleDateFormat("dd/MM/yyyy").parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else if (date.matches("([0-9]{2}).([0-9]{2}).([0-9]{4})"))
        {
            try {
                d = new SimpleDateFormat("dd.MM.yyyy").parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else if (date.matches("([0-9]{8}_[0-9]{6})"))
        {
            try {
                d = new SimpleDateFormat("yyyyMMdd_HHmmss").parse(date);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
        return d;
    }
    public static String formatTime(String time) {
        String formattedTime = "";
        Integer value = Integer.parseInt(time);
        Integer minutes = value / 60000;
        Integer seconds = (value % 60000) / 1000;
        Integer milli = value % 1000;
        formattedTime = String.format("%02d:%02d.%03d", minutes, seconds, milli);
        return formattedTime;
    }
}
