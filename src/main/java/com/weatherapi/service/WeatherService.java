package com.weatherapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherapi.WeatherExtractor;
import com.weatherapi.WeatherResponse;
import com.weatherapi.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


@Service
public class WeatherService {
    private final WeatherRepository weatherRepository;
    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);
    private String city = "Minsk";

    public void setCity(String city) {
        this.city = city;
    }

    @Autowired
    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter city: ");
        this.city = scanner.nextLine();
    }

    // every 30 minutes
    @Scheduled(fixedRateString = "${executionInterval:20000}")
    public void saveWeatherInfoPeriodically() throws JsonProcessingException {
        try {
            saveInfo();
        } catch (JsonProcessingException e) {
            logger.error("Error saving weather info: {}", e.getMessage());
        }
    }

    public void saveInfo() throws JsonProcessingException {
        try {
            WeatherResponse w = getWeather();
            logger.info("Saving weather info periodically: {}", w);
            weatherRepository.saveWeatherInfo(w);
        } catch (Exception e) {
            logger.error("Error saving weather info: {}", e.getMessage());
        }
    }

    public WeatherResponse getWeather() throws JsonProcessingException {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city +
                "&appid=5ece387521f39284d0c083321dc2fe2a&units=metric";
        RestTemplate restTemplate = new RestTemplate();

        try {
            WeatherExtractor weatherExtractor = new WeatherExtractor();
            return weatherExtractor.extractWeatherFromJSON(restTemplate.getForObject(url, String.class));
        } catch (Exception e) {
            logger.error("Error getting weather info: {}", e.getMessage());
            throw e;
        }
    }

    public String getLatestWeatherInfo() {
        try {
            return weatherRepository.getLatestWeatherInfo();
        } catch (Exception e) {
            logger.error("Error getting latest weather info: {}", e.getMessage());
            return "{}";
        }
    }

    public String getAverageTemperature(Date fromDate, Date toDate) {
        logger.info("Getting average temperature for dates: {} to {}", fromDate, toDate);

        List<Map<String, Double>> result = weatherRepository.getAverageTemperatureByDateRange(
                fromDate,
                toDate
        );

        logger.info("Result from database: {}", result);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            logger.error("Error converting result to JSON", e);
            return "{}";
        }
    }
}
