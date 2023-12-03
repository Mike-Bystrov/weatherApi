package com.weatherapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherapi.WeatherResponse;
import com.weatherapi.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
public class Controller {

    private final WeatherService weatherService;
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    @Autowired
    public Controller(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/latest-weather/{city}")
    public String getLatestWeather(@PathVariable(name = "city") String city) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            weatherService.setCity(city);
            String latestWeather = weatherService.getLatestWeatherInfo();
            return objectMapper.writeValueAsString(latestWeather);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/average-temperature/{fromDate}/{toDate}")
    public String getAverageTemperature(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate
    ) {
        logger.info("Received request for average temperature. From date: {}, To date: {}", fromDate, toDate);

        String result = weatherService.getAverageTemperature(fromDate, toDate);

        logger.info("Result sent as response: {}", result);

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            logger.error("Result can't be processed", result);
            e.printStackTrace();
            return "[]";
        }
    }
}
