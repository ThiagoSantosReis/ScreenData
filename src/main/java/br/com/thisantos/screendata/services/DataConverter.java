package br.com.thisantos.screendata.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataConverter implements MediaConverterData{
    private ObjectMapper mapper = new ObjectMapper();


    @Override
    public <T> T getData(String json, Class<T> mClass) {
        try {
            return mapper.readValue(json, mClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
