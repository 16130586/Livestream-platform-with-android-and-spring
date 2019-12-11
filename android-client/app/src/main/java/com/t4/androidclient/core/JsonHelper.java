package com.t4.androidclient.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.ConfigFeature;

import java.io.IOException;
import java.util.Map;

public class JsonHelper {
    public JsonHelper() {
    }

    public static String serialize(Object obj) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException var3) {
            return "";
        }
    }

    public static <E> E deserialize(String json, Class<E> targetClass) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(json, targetClass);
        } catch (IOException var4) {
            return null;
        }
    }
    public static <E> E deserialize(Map objMap, Class<E> targetClass) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(objMap, targetClass);
    }
    public static <E> E deserialize(DeserializationFeature configRoot , String json, Class<E> targetClass) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(configRoot , true);
        try {
            return mapper.readValue(json, targetClass);
        } catch (IOException var4) {
            return null;
        }
    }

    public static <E> void printArray(E[] inputArray) {
        Object[] var1 = inputArray;
        int var2 = inputArray.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            Object element = var1[var3];
            System.out.printf("%s ", element.toString());
        }

        System.out.println();
    }
}