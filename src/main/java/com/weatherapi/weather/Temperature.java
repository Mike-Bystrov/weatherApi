package com.weatherapi.weather;

public class Temperature {
    private double temp;

    public Temperature(double temp) {
        this.temp = temp;
    }

    public double getTemp() {
        return temp;
    }

    @Override
    public String toString() {
        return ""+temp+"°С";
    }
}
