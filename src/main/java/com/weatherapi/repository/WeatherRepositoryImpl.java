package com.weatherapi.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherapi.WeatherResponse;
import com.weatherapi.connection.DBConnection;
import com.weatherapi.weather.Humidity;
import com.weatherapi.weather.Location;
import com.weatherapi.weather.Pressure;
import com.weatherapi.weather.Temperature;
import com.weatherapi.weather.Weather;
import com.weatherapi.weather.Wind;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.*;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class WeatherRepositoryImpl implements WeatherRepository {

    private static final Logger logger = LoggerFactory.getLogger(WeatherRepositoryImpl.class);

    @Override
    public void saveWeatherInfo(WeatherResponse weatherResponse) {
        logger.info("Saving weather info: {}", weatherResponse);

        // Ваша логика сохранения данных в базе данных
        try (Connection connection = DBConnection.getInstance()) {
            String insertQuery = "INSERT INTO weather_info (temperature, wind_speed, pressure, humidity, weather_condition, location, timestamp) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setDouble(1, weatherResponse.getTemperature().getTemp());
                preparedStatement.setDouble(2, weatherResponse.getWind().getSpeed());
                preparedStatement.setInt(3, weatherResponse.getPressure().getPressure());
                preparedStatement.setInt(4, weatherResponse.getHumidity().getHumidityValue());
                preparedStatement.setString(5, weatherResponse.getCondition().getDescription());
                preparedStatement.setString(6, weatherResponse.getLocation().getCity());
                preparedStatement.setTimestamp(7, new java.sql.Timestamp(new Date().getTime()));

                preparedStatement.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("Error saving weather info", e);
        }
    }

    public String getLatestWeatherInfo() {
        WeatherResponse latestWeatherInfo = null;

        // Ваш запрос для получения последней записи из базы данных
        String selectQuery = "SELECT * FROM weather_info ORDER BY timestamp DESC LIMIT 1";

        try (Connection connection = DBConnection.getInstance();
             PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                latestWeatherInfo = createWeatherResponseFromResultSet(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (latestWeatherInfo != null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.writeValueAsString(latestWeatherInfo);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return ""; // Или другой вариант обработки
    }

    @Override
    public List<Map<String, Double>> getAverageTemperatureByDateRange(Date fromDate, Date toDate) {
        List<Map<String, Double>> resultList = new ArrayList<>();

        try (Connection connection = DBConnection.getInstance()) {
            String query = "SELECT AVG(wind_speed), CAST(day AS DATE)\n" +
                    "FROM (\n" +
                    "         SELECT *, date_trunc('day', timestamp) AS day\n" +
                    "         FROM weather_info\n" +
                    "     ) WHERE date_trunc('day', timestamp) BETWEEN ? AND ?\n" +
                    "GROUP BY CAST(day AS DATE);";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                // Преобразование java.util.Date в java.sql.Date
                java.sql.Date sqlFromDate = new java.sql.Date(fromDate.getTime());
                java.sql.Date sqlToDate = new java.sql.Date(toDate.getTime());

                preparedStatement.setDate(1, sqlFromDate);
                preparedStatement.setDate(2, sqlToDate);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Map<String, Double> resultMap = new HashMap<>();
                        resultMap.put(resultSet.getString("day"), resultSet.getDouble("avg"));
                        resultList.add(resultMap);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    private WeatherResponse createWeatherResponseFromResultSet(ResultSet resultSet) throws SQLException {
        double temperature = resultSet.getDouble("temperature");
        double windSpeed = resultSet.getDouble("wind_speed");
        int pressure = resultSet.getInt("pressure");
        int humidity = resultSet.getInt("humidity");
        String weatherCondition = resultSet.getString("weather_condition");
        String location = resultSet.getString("location");

        Temperature tempObj = new Temperature(temperature);
        Wind windObj = new Wind(windSpeed);
        Pressure pressureObj = new Pressure(pressure);
        Humidity humidityObj = new Humidity(humidity);
        Weather weatherObj = new Weather(weatherCondition);
        Location locationObj = new Location(location);

        return new WeatherResponse(weatherObj, windObj, tempObj, pressureObj, locationObj, humidityObj);
    }
}
