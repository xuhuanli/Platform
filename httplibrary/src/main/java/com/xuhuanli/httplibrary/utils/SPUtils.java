package com.xuhuanli.httplibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;


import com.xuhuanli.httplibrary.MyRetrofit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public class SPUtils {
    public SPUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static final String FILE_NAME = "share_data";

    public static String get(String key, String defaultValue) {
        SharedPreferences sp = obtainPref();
        return sp.getString(key, defaultValue);
    }

    public static int get(String key, int defaultValue) {
        SharedPreferences sp = obtainPref();
        return sp.getInt(key, defaultValue);
    }

    public static boolean get(String key, boolean defaultValue) {
        SharedPreferences sp = obtainPref();
        return sp.getBoolean(key, defaultValue);
    }

    public static float get(String key, float defaultValue) {
        SharedPreferences sp = obtainPref();
        return sp.getFloat(key, defaultValue);
    }

    public static long get(String key, long defaultValue) {
        SharedPreferences sp = obtainPref();
        return sp.getLong(key, defaultValue);
    }

    public static Set<String> get(String key, Set<String> defaultValue) {
        SharedPreferences sp = obtainPref();
        return sp.getStringSet(key, defaultValue);
    }

    public static void put(String key, String value) {
        SharedPreferences.Editor editor = obtainPrefEditor();
        editor.putString(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    public static void put(String key, int value) {
        SharedPreferences.Editor editor = obtainPrefEditor();
        editor.putInt(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    public static void put(String key, boolean value) {
        SharedPreferences.Editor editor = obtainPrefEditor();
        editor.putBoolean(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    public static void put(String key, float value) {
        SharedPreferences.Editor editor = obtainPrefEditor();
        editor.putFloat(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    public static void put(String key, long value) {
        SharedPreferences.Editor editor = obtainPrefEditor();
        editor.putLong(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    public static void put(String key, Set value) {
        SharedPreferences.Editor editor = obtainPrefEditor();
        editor.putStringSet(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    public static void remove(String key) {
        SharedPreferences.Editor editor = obtainPrefEditor();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    public static void clearAll() {
        SharedPreferences.Editor editor = obtainPrefEditor();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    public static boolean contains(String key) {
        SharedPreferences sp = obtainPref();
        return sp.contains(key);
    }

    public static Map<String, ?> getAll() {
        SharedPreferences sp = obtainPref();
        return sp.getAll();
    }

    private static SharedPreferences obtainPref() {
        Context context = MyRetrofit.getContext();
        SharedPreferences pref = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return pref;
    }

    private static SharedPreferences.Editor obtainPrefEditor() {
        return obtainPref().edit();
    }

/**************************************** 以下为需要传入Context参数的API ******************************************/

    public static void put(Context context, String key, Object object) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }

    public static Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }


    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }


    public static void clearAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }


    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }
}
