package br.com.thisantos.screendata.services;

public interface MediaConverterData {
    <T> T getData(String json, Class<T> mClass);
}
