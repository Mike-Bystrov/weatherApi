package com.weatherapi.weather;

public class Location {
    private String city;

    public Location(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return ""+city;
    }


}
