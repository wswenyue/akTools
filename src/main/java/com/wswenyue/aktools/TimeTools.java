package com.wswenyue.aktools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeTools {

    public static final String PATTERN_DEFAULT = "yyyy-MM-dd HH:mm:ss SSS";
    private static final SimpleDateFormat FORMAT_DEFAULT = new SimpleDateFormat(PATTERN_DEFAULT);

    public static String formatTime(long time, TimeUnit unit) {
        return formatTime(time, unit, FORMAT_DEFAULT);
    }

    public static String formatTime(long time, TimeUnit unit, SimpleDateFormat format) {
        long millis = unit.toMillis(time);
        return format.format(new Date(millis));
    }

    public static String convertTime(String pattern, String timeStr) {
        String ret = "";
        long time = Long.valueOf(timeStr);
        if (time < 999999999L) {
            ret = String.format("time: %s not seconds or milliSeconds!!!", timeStr);
        } else if (time <= 9999999999L) {
            ret = formatTime(time, TimeUnit.SECONDS, CommonTools.isEmpty(pattern) ? FORMAT_DEFAULT : new SimpleDateFormat(pattern));
        } else if (time <= 999999999999L) {
            ret = "Guess what you want to enter is milliseconds? The millisecond is 13 bits. -->" + timeStr;
        } else if (time <= 9999999999999L) {
            ret = formatTime(time, TimeUnit.MILLISECONDS, CommonTools.isEmpty(pattern) ? FORMAT_DEFAULT : new SimpleDateFormat(pattern));
        } else {
            ret = String.format("time: %s not seconds or milliSeconds!!!", timeStr);
        }
        return ret;

    }

    public static String formatTime(String timeStr) {
        return convertTime(null, timeStr);
    }

}
