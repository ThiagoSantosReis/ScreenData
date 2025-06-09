package br.com.thisantos.screendata.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MediaAPI {
    public String getData(String strAdress){

        URI adress = URI.create(strAdress);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(adress)
                .build();
        HttpResponse<String> response = null;

        try{
            response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        }catch (IOException | InterruptedException e){
            throw new RuntimeException(e);
        }

        return response.body();

    }
}
