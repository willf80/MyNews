package com.appinlab.mynews.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    /**
     * Parse strind date with this format  : yyyy-MM-dd'T'HH:mm:ssX
     *
     * @param dateString date in string
     * @return Date object
     */
    public static Date parseStringToDate(String dateString) {
        String pattern = "yyyy-MM-dd'T'HH:mm:ssX";
        return parseStringToDate(dateString, pattern);
    }

    /**
     * Parse string date with this format pattern
     *
     * @param dateString date in string
     * @return Date object
     */
    public static Date parseStringToDate(String dateString, String dateStringPattern) {
        SimpleDateFormat format = new SimpleDateFormat(dateStringPattern, Locale.getDefault());
        try {
            return format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String parseDateToString(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        return format.format(date);
    }

    public static String stringDateFormatted(String dateString, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        return format.format(parseStringToDate(dateString));
    }

    public static String stringDateFormattedWithPattern(String dateString, String dateStringPattern, String outputDatePattern) {
        SimpleDateFormat format = new SimpleDateFormat(outputDatePattern, Locale.getDefault());
        return format.format(parseStringToDate(dateString, dateStringPattern));
    }

}
