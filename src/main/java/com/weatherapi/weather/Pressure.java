package com.weatherapi.weather;

public class Pressure {
    private int pressureValue;

    public Pressure(int pressure) {
        this.pressureValue = pressure;
    }

    public int getPressure() {
        return pressureValue;
    }

    @Override
    public String toString() {
        return ""+pressureValue;
    }
}
