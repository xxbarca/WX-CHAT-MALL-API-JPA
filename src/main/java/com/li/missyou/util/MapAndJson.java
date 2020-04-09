package com.li.missyou.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ServerErrorException;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import java.util.Map;

@Convert
public class MapAndJson implements AttributeConverter<Map<String, Object>, String> {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public String convertToDatabaseColumn(Map<String, Object> map) {
        try {
            return mapper.writeValueAsString(map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("message");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }
        try {
            Map<String, Object> t = mapper.readValue(s, HashedMap.class);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("message");
        }
    }
}
