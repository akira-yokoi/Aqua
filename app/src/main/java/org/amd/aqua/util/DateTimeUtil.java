package org.amd.aqua.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Akira on 2018/04/15.
 */

public class DateTimeUtil {

    public static final SimpleDateFormat YYYY_MM_DD_HHmm_FORMATER = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault());

    public static final SimpleDateFormat YYYY_M_D_FORMATER = new SimpleDateFormat("yyyy/M/d", Locale.getDefault());

    public static final SimpleDateFormat M_D_Hm_FORMATER = new SimpleDateFormat("M/d H:m", Locale.getDefault());

    public static final SimpleDateFormat Hmm_FORMATER = new SimpleDateFormat("H:mm", Locale.getDefault());

    public static String getYYYYMD(Date date) {
        if (date == null) {
            return null;
        }

        try {
            synchronized (YYYY_M_D_FORMATER) {
                return YYYY_M_D_FORMATER.format(date);
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public static String getYYYYMMDDHHMM(Date date) {
        if (date == null) {
            return null;
        }

        try {
            synchronized (YYYY_MM_DD_HHmm_FORMATER) {
                return YYYY_MM_DD_HHmm_FORMATER.format(date);
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public static String getMDHm(Date date) {
        if (date == null) {
            return null;
        }

        try {
            synchronized (M_D_Hm_FORMATER) {
                return M_D_Hm_FORMATER.format(date);
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public static String getHmm(Date date) {
        if (date == null) {
            return null;
        }

        try {
            synchronized (Hmm_FORMATER) {
                return Hmm_FORMATER.format(date);
            }
        } catch (Exception ex) {
            return null;
        }
    }


    public static Date getDate(int year, int month, int day, int hour, int minutes) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(year, month - 1, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minutes);
        return cal.getTime();
    }

    public static Date adjustHour(Date date, int hour) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, hour);
        Date dt = cal.getTime();
        return dt;
    }
}
