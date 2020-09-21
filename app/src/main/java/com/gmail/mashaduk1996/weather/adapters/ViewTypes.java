package com.gmail.mashaduk1996.weather.adapters;

import android.widget.LinearLayout;

import com.gmail.mashaduk1996.weather.models.WeatherDay;

import java.util.ArrayList;
import java.util.List;

public class ViewTypes {
    private ArrayList<WeatherDay> weatherDayList;
    ArrayList<Integer> possitions;
    private ArrayList<Integer> diffrentPos;

    public ViewTypes(ArrayList<WeatherDay> weatherDayList) {
        this.weatherDayList = weatherDayList;
    }


    public ArrayList<WeatherDay> getNewList() {
        ArrayList<WeatherDay> newList = new ArrayList<>();
        newList.add(weatherDayList.get(0));
        newList.addAll(weatherDayList);
        return newList;
    }
}
