package com.example.dobrowol.styloweplywanie.utils;

import java.util.Arrays;

/**
 * Created by dobrowol on 12.04.17.
 */

public class Utils {
    public static String[] swimmingStylesNames() {
        return Arrays.toString(SwimmingStyles.values()).replaceAll("^.|.$", "").split(", ");
    }
}
