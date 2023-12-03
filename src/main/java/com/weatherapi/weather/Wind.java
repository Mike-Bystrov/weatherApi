package com.weatherapi.weather;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Wind {
    private double speed;
    @JsonIgnore
    private int deg;
    @JsonIgnore
    private double gust;


    @JsonCreator
    public Wind(@JsonProperty("speed") double speed) {
        this.speed = speed;
    }


    public double getGust() {
        return gust;
    }

    public double getSpeed() {
        return speed;
    }

    public int getDeg() {
        return deg;
    }

    @Override
    public String toString() {
        return ""+getSpeed();
    }
}
