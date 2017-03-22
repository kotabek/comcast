package com.comcast.utils;

/**
 * Created by kotabek on 3/22/17.
 */
public class DG {
    public static String getString(Object obj) {
        if (obj == null) {
            return "";

        } else if (obj instanceof String) {
            return (String) obj;

        }
        return obj.toString();
    }

    public static int getInt(Object obj) {
        return getInt(obj, 0);
    }

    public static int getInt(Object obj, int def) {
        if (obj == null) {
            return def;
        } else if (obj instanceof Number) {
            return ((Number) obj).intValue();
        }
        try {
            return getInt(Integer.valueOf(getString(obj)), def);
        } catch (Exception ex) {
            return def;
        }
    }
}
