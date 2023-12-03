package com.weatherapi.repository;

import com.weatherapi.WeatherResponse;
import org.springframework.data.repository.query.Param;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface WeatherRepository {
    void saveWeatherInfo(WeatherResponse w);

    String getLatestWeatherInfo();

    List<Map<String, Double>> getAverageTemperatureByDateRange(
            @Param("fromDate") Date fromDate,
            @Param("toDate") Date toDate);

}
