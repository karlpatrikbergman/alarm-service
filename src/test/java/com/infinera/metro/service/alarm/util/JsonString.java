package com.infinera.metro.service.alarm.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;

public final class JsonString {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final Object object;

    public JsonString(Object object) {
        this.object = object;
    }

    @Override
    public String toString() {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeJsonMappingException("Failed to convert to pretty format json string");
        }
    }
}
