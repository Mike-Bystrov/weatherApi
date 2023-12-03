package com.weatherapi;

import com.weatherapi.weather.*;
import org.springframework.stereotype.Component;

public class WeatherResponse {
    private Weather condition;
    private Wind wind;
    private Temperature temperature;
    private Pressure pressure;
    private Location location;
    private Humidity humidity;

    public WeatherResponse() {

    }
    public WeatherResponse(Weather condition, Wind wind, Temperature temperature, Pressure pressure, Location location, Humidity humidity) {
        this.condition = condition;
        this.wind = wind;
        this.temperature = temperature;
        this.pressure = pressure;
        this.location = location;
        this.humidity = humidity;
    }

    public Wind getWind() {
        return wind;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public Pressure getPressure() {
        return pressure;
    }

    public Location getLocation() {
        return location;
    }

    public Humidity getHumidity() {
        return humidity;
    }

    public Weather getCondition() {
        return condition;
    }

    @Override
    public String toString() {
        return "WeatherResponse{" +
                "\nweather=" + condition +
                "\nwind=" + wind +
                "\ntemperature=" + temperature +
                "\npressure=" + pressure +
                "\nlocation=" + location +
                "\nhumidity=" + humidity +
                '}';
    }
}