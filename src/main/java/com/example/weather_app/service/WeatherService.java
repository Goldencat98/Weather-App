package com.example.weather_app.service;

import com.example.weather_app.model.WeatherResponse;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class WeatherService {
    @Value("${api.key}")
    private String apiKey;

    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather";

    public WeatherResponse getWeather(String city) throws IOException {
        String url = String.format("%s?q=%s&units=metric&appid=%s", API_URL, city, apiKey);
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            HttpResponse response = httpClient.execute(request);
            String json = EntityUtils.toString(response.getEntity());
            return new Gson().fromJson(json, WeatherResponse.class);
        }
    }
}
