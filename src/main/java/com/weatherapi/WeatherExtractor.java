package com.weatherapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherapi.weather.*;

public class WeatherExtractor {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public WeatherResponse extractWeatherFromJSON(String jsonData) throws JsonProcessingException {
        JsonNode rootNode = objectMapper.readTree(jsonData);

        JsonNode weatherNode = rootNode.get("weather");
        Weather weather = objectMapper.readValue(weatherNode.get(0).toString(), Weather.class);

        JsonNode windNode = rootNode.get("wind");
        Wind wind = objectMapper.readValue(windNode.toString(), Wind.class);

        JsonNode temperatureNode = rootNode.get("main");
        Temperature temperature = objectMapper.readValue(temperatureNode.get("temp").toString(), Temperature.class);

        JsonNode pressureNode = rootNode.get("main");
        Pressure pressure = objectMapper.readValue(pressureNode.get("pressure").toString(), Pressure.class);

        JsonNode locationNode = rootNode.get("name");
        Location location = objectMapper.readValue(locationNode.toString(), Location.class);

        JsonNode humidityNode = rootNode.get("main");
        Humidity humidity = objectMapper.readValue(humidityNode.get("humidity").toString(), Humidity.class);


        return new WeatherResponse(weather, wind, temperature, pressure, location, humidity);
    }

}