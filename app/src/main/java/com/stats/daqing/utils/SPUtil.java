package com.stats.daqing.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.stats.daqing.common.DaQingApplication;

import java.util.Set;

/**
 * SharedPreferences 工具类
 * Created by Administrator on 2017/4/9.
 */

public class SPUtil {

    public final static int TYPE_USER = 0;
    public final static int TYPE_APP = 1;

    private final static String USER_CONFIG = "user_config";
    private final static String APP_CONFIG = "app_config";


    /**
     * 获取SharedPreferences
     * @param type
     * @return
     */
    private static SharedPreferences getPreferences(int type) {
        Context context = DaQingApplication.getContext();
        SharedPreferences preferences = null;
        if (type == TYPE_USER) {
            // 用户配置信息
            preferences = context.getSharedPreferences(USER_CONFIG, Context.MODE_PRIVATE);
            return preferences;

        } else if (type == TYPE_APP) {
            // 软件配置信息
            preferences = context.getSharedPreferences(APP_CONFIG,Context.MODE_PRIVATE);
            return preferences;
        }
        return preferences;
    }


    /**
     * 保存String类型值
     * @param key
     * @param values
     */
    public static void setPreferences(int type,String key, String values) {
        SharedPreferences.Editor editor = getPreferences(type).edit();
        editor.putString(key, values);
        editor.commit();
    }

    /**
     * 获取String类型值
     * @param key
     * @param defaultValues
     * @return
     */
    public static String getPreferences(int type,String key,String defaultValues) {
        return getPreferences(type).getString(key, defaultValues);
    }


    /**
     * 保存int类型值
     * @param key
     * @param values
     */
    public static void setPreferences(int type,String key, int values) {
        SharedPreferences.Editor editor = getPreferences(type).edit();
        editor.putInt(key, values);
        editor.commit();
    }

    /**
     * 获取int类型值
     * @param key
     * @param defaultValues
     * @return
     */
    public static int getPreferences(int type,String key, int defaultValues) {
        return getPreferences(type).getInt(key, defaultValues);
    }

    /**
     * 保存long类型值
     * @param key
     * @param values
     */
    public static void setPreferences(int type,String key, long values) {
        SharedPreferences.Editor editor = getPreferences(type).edit();
        editor.putLong(key, values);
        editor.commit();
    }

    /**
     * 获取long类型值
     * @param key
     * @param defaultValues
     * @return
     */
    public static long getPreferences(int type,String key, long defaultValues) {
        return getPreferences(type).getLong(key, defaultValues);
    }


    /**
     * 保存boolean类型值
     * @param key
     * @param values
     */
    public static void setPreferences(int type,String key, boolean values) {
        SharedPreferences.Editor editor = getPreferences(type).edit();
        editor.putBoolean(key, values);
        editor.commit();
    }

    /**
     * 获取boolean类型值
     */
    public static boolean getPreferences(int type,String key, boolean defaultValues) {
        return getPreferences(type).getBoolean(key, defaultValues);
    }

    /**
     * 是否包含此键
     * @param type
     * @param key
     * @return
     */
    public static boolean isContainKey(int type,String key) {
        if (getPreferences(type).contains(key)) {
            return true;
        }
        return false;
    }

    public static void setStringset(int type, String key, Set<String> set){
        SharedPreferences.Editor editor = getPreferences(type).edit();
        editor.putStringSet(key,set);
        editor.commit();
    }


    public static void remove(int type,String key){
        getPreferences(type).edit().remove(key).commit();
    }

    public static void clear(int type){
        getPreferences(type).edit().clear().commit();
    }



























}
