package com.example.dobrowol.styloweplywanie.utils;

/**
 * Created by dobrowol on 24.02.18.
 */

public class LanguageUtils {
    public static String removePolishSigns(String source)
    {
        return source.replace("ź","z").replace("ż","z").replace("Ź","Z").replace("Ż", "Z").replace("ó","o").replace("Ó", "O").
                replace("Ł","L").replace("ł","l").replace("ą","a").replace("Ą", "A").replace("Ć", "C").replace("ć","c");
    }
}
