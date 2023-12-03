package com.weatherapi.weather;

//"weather:"

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Weather {
    @JsonIgnore
    private int id;

    @JsonIgnore
    private String main;

    private String description;

    @JsonIgnore
    private String icon;

    @JsonCreator
    public Weather(@JsonProperty("description") String weatherCondition) {
        this.description = weatherCondition;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getMain() {
        return main;
    }

    public String getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return "main: " + getMain() + "\n" + "descr: " + getDescription();
    }
}
