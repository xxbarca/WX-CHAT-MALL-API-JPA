package com.li.missyou.api.v2;

import java.text.SimpleDateFormat;

public class CommUtil {
    public static String formatTime(String format, Object v) {
        if (v == null)
            return null;
        if (v.equals(""))
            return "";
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(v);
    }

    public static boolean isNotNull(Object obj) {
        if ((obj != null) && (!obj.toString().equals(""))) {
            return true;
        }
        return false;
    }

    public static String formatShortDate(Object v) {
        if (v == null)
            return null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(v);
    }
}
