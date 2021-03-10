package com.foodforme.vendore.prefmanager;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class AppPrefConnector {

    private static final String PREF_NAME = "GLOBAL_RESTAURRENT";
    private static final int MODE = Context.MODE_PRIVATE;

    public static final String KEY_SHORTCUT = "KEY_SHORTCUT";

    public static final String DEVICE_TOKEN = "DEVICE_TOKEN";

    /*about us, term conditon, contact us, policy*/
    public static final String APP_ABOUT_US = "APP_ABOUT_US";
    public static final String APP_CONTACT_US = "APP_CONTACT_US";
    public static final String APP_TERM_CONDITION = "APP_TERM_CONDITION";
    public static final String APP_PRIVACYPOLICY = "APP_PRIVACYPOLICY";


    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, MODE);
    }

    public static Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }


    public static void writeBoolean(Context context, String key, boolean value) {
        getEditor(context).putBoolean(key, value).commit();
    }

    public static boolean readBoolean(Context context, String key,
                                      boolean defValue) {
        return getPreferences(context).getBoolean(key, defValue);
    }

    public static void writeInteger(Context context, String key, int value) {
        getEditor(context).putInt(key, value).commit();

    }

    public static int readInteger(Context context, String key, int defValue) {
        return getPreferences(context).getInt(key, defValue);
    }

    public static void writeString(Context context, String key, String value) {
        getEditor(context).putString(key, value).commit();

    }

    public static String readString(Context context, String key, String defValue) {
        return getPreferences(context).getString(key, defValue);
    }

    public static void writeFloat(Context context, String key, float value) {
        getEditor(context).putFloat(key, value).commit();
    }

    public static float readFloat(Context context, String key, float defValue) {
        return getPreferences(context).getFloat(key, defValue);
    }

    public static void writeLong(Context context, String key, long value) {
        getEditor(context).putLong(key, value).commit();

    }

    public static long readLong(Context context, String key, long defValue) {
        return getPreferences(context).getLong(key, defValue);

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void writeArraylist(Context context, String key,
                                      List<String> arryid) {
        Set<String> set = new HashSet<String>(arryid);
        getEditor(context).putStringSet(key, set).commit();

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static List<String> readArraylist(Context context, String key) {
        Set<String> stock_Set = getPreferences(context).getStringSet(key,
                new HashSet<String>());
        List<String> demo = new ArrayList<String>(stock_Set);

        return demo;
    }


    public static void remove(Context context, String key) {
        getEditor(context).remove(key).commit();
    }

    public static void clear(Context context) {
        getEditor(context).clear().commit();
    }

}
