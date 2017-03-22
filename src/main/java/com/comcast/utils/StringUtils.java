package com.comcast.utils;

/**
 * Created by kotabek on 3/22/17.
 */
public class StringUtils {
    public static boolean isEmpty(String val) {
        return val == null
               || val.isEmpty()
               || val.trim().isEmpty();
    }
}
