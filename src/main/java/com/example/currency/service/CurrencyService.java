package com.example.currency.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

import com.example.currency.model.ExchangeRate;
import com.google.gson.Gson;

public class CurrencyService {
    private HttpClient client;
    
    private final String linkAPI = "https://v6.exchangerate-api.com/v6/efb77f77110d48ca7591e6b8/latest/";

    public CurrencyService() {
        this.client = HttpClient.newHttpClient();
    }

    public ExchangeRate request(String baseCode) throws IOException, InterruptedException{
        var req = HttpRequest.newBuilder()
            .uri(URI.create(linkAPI + baseCode))
            .GET()
            .build(); 
                    
        var responde = client.send(req, BodyHandlers.ofString());
        int statusCode = responde.statusCode();

        if (statusCode == 200) {
            Gson gson = new Gson();
            return gson.fromJson(responde.body(), ExchangeRate.class);
        }
        else throw new IOException("Errore nella chiamata: " + statusCode);  
    }

}