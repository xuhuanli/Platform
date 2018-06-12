package com.xuhuanli.httplibrary.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GsonUtils {
    public static <T> T getObject(String jsonString, Class<T> cls) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonString, cls);
        } catch (Exception e) {
        }
        return t;
    }

    public static <T> List<T> getArray(String jsonString, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(
                    jsonString,
                    new TypeToken<List<T>>(){}.getType()
            );
        } catch (Exception e) {
        }
        return list;
    }


    public static List<Map<String, Object>> listKeyMaps(String jsonString) {
        List<Map<String, Object>> list = new ArrayList<>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(
                    jsonString,
                    new TypeToken<List<Map<String, Object>>>(){}.getType()
            );
        } catch (Exception e) {
        }
        return list;
    }

    public static Map<String, Object> objKeyMaps(String jsonString) {
        Map<String, Object> map = new HashMap<>();
        try {
            Gson gson = new Gson();
            map = gson.fromJson(
                    jsonString,
                    new TypeToken<Map<String, Object>>(){}.getType()
            );
        } catch (Exception e) {
        }
        return map;
    }
}
