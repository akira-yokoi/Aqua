package org.amd.aqua.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Akira on 2018/04/21.
 */

public class PreferenceUtil {
    public static boolean hasPref(Context context, String key) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.contains(key);
    }

    public static String getStringPref(Context context, String key, String defValue) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(key, defValue);
    }

    public static boolean getBooleanPref(Context context, String key, boolean defValue) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getBoolean(key, defValue);
    }

    public static float getFloatPref(Context context, String key, float defValue) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getFloat(key, defValue);
    }

    public static int getIntPref(Context context, String key, int defValue) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getInt(key, defValue);
    }

    public static long getLongPref(Context context, String key, long defValue) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getLong(key, defValue);
    }

    public static boolean saveStringPref(Context context, String key, String value) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static boolean saveBooleanPref(Context context, String key, boolean value) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public static boolean saveFloatPref(Context context, String key, float value) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    public static boolean saveIntPref(Context context, String key, int value) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    public static boolean saveLongPref(Context context, String key, long value) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    public static boolean removePref(Context context, String key) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key);
        return editor.commit();
    }
}
