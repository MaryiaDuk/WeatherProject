package com.gmail.mashaduk1996.weather.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class WeatherForecast {
    @SerializedName("list")
    private ArrayList<WeatherDay> items;



    public WeatherForecast(ArrayList<WeatherDay> items) {
        this.items = items;
    }

    public ArrayList<WeatherDay> getItems() {
        return items;
    }
}