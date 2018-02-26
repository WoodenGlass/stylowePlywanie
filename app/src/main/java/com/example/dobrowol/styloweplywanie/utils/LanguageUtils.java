package com.example.dobrowol.styloweplywanie.utils;

/**
 * Created by dobrowol on 24.02.18.
 */

public class LanguageUtils {
    public static String removePolishSigns(String source)
    {
        return source.replace("ź","z").replaceAll("ż","z").replaceAll("Ź","Z").replaceAll("Ż", "Z").replaceAll("ó","o").replaceAll("Ó", "O").
                replaceAll("Ł","L").replaceAll("ł","l").replaceAll("ą","a").replaceAll("Ą", "A").replaceAll("Ć", "C").replaceAll("ć","c");
    }
}
