package com.readingroom.util.common;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.apache.commons.io.IOUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;

public class JsonConverter {

    private final static Gson gson = new Gson();

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static String toJson(Object obj, Type type) {
        return gson.toJson(obj, type);
    }

    public static JsonElement toJsonTree(Object obj) {
        return gson.toJsonTree(obj);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static <T> T fromJson(BufferedReader bufferedReader, Class<T> clazz) {
        return gson.fromJson(bufferedReader, clazz);
    }

    public static <T> T fromJson(BufferedReader bufferedReader, Type type) {
        return gson.fromJson(bufferedReader, type);
    }

    public static <T> T readJsonFile(File file, Type T) {
        if (file == null) return null;
        try {
            String json = IOUtils.toString(new FileReader(file));
            return gson.fromJson(json, T);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}