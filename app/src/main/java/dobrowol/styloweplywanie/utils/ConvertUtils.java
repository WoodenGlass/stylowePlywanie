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
        }else if (date.matches("([0-9]{8}_[0-9]{6})"))
        {
            try {
                d = new SimpleDateFormat("yyyyMMdd_HHmmss").parse(date);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
        return d;
    }
}
