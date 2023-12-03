package com.weatherapi.weather;

public class Humidity {
    private int humidityValue;

    public Humidity(int humidityValue) {
        this.humidityValue = humidityValue;
    }

    public int getHumidityValue() {
        return humidityValue;
    }

    @Override
    public String toString() {
        return ""+humidityValue;
    }
}
