package com.ge.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by falcon on 16-12-27.
 *
 */
public class DateTimeHelper {

    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final Map<String, DateTimeFormatter> map = new ConcurrentHashMap<>();

    private static DateTimeFormatter getFormatter(String pattern) {
        return map.computeIfAbsent(pattern, k -> DateTimeFormatter.ofPattern(pattern));
    }

    public static String toSimpleString(LocalDateTime time) {
        return toSimpleString(time, DEFAULT_PATTERN);
    }

    public static String toSimpleString(LocalDateTime time, String pattern) {
        if (time == null) {
            return "";
        }
        DateTimeFormatter formatter = getFormatter(pattern);
        return formatter.format(time);
    }

    public static LocalDateTime parse(String time, String pattern) {
        DateTimeFormatter formatter = getFormatter(pattern);
        return LocalDateTime.parse(time, formatter);
    }

    public static LocalDateTime parse(String time) {
        try {
            return parse(time, DEFAULT_PATTERN);
        } catch (Exception e) {
            return null;
        }
    }
}
