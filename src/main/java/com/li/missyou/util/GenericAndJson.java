package com.li.missyou.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GenericAndJson {

    private static ObjectMapper mapper;

    @Autowired
    public void setMapper(ObjectMapper mapper) {
        GenericAndJson.mapper = mapper;
    }

    public static <T> String objectToJson(T o) {
        try {
            return GenericAndJson.mapper.writeValueAsString(o);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("message");
        }
    }

    public static <T> T jsonToObject(String s, TypeReference<T> t) {
        if (s == null) {
            return null;
        }
        try {
            T o = GenericAndJson.mapper.readValue(s, t);
            return o;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("message");
        }
    }

    public static <T>List<T> jsonToList(String s) {
        if (s == null) {
            return null;
        }
        try {
            List<T> list = GenericAndJson.mapper.readValue(s, new TypeReference<List<T>>() {});
            return list;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("message");
        }
    }

    public static <T> T jsonToList(String s, TypeReference<T> t) {
        if (s == null) {
            return null;
        }
        try {
            T o = GenericAndJson.mapper.readValue(s, t);
            return o;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("message");
        }
    }
}
