package com.violetboralee.android.bakingappnew.util;

/**
 * Created by bora on 23/11/2017.
 */

public class TextUtil {
    public static String removeTrailingZero(String stringNumber) {
        return !stringNumber.contains(".") ? stringNumber :
                stringNumber.replaceAll("0*$", "").replaceAll("\\.$", "");
    }

    public static String capitalizeWords(String text) {
        String[] stringArray = text.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : stringArray) {
            String capString = s.substring(0, 1).toUpperCase() + s.substring(1);
            stringBuilder.append(capString).append(" ");
        }
        return stringBuilder.toString();
    }


}
