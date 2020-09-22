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
        for (int i = 0; i < weatherDayList.size()-1; i++) {
            if (weatherDayList.get(i).getTime().equals("00:00")) {
                newList.add(weatherDayList.get(i));
                newList.add(weatherDayList.get(i));
            } else {
                newList.add(weatherDayList.get(i));
            }
        }
        return newList;
    }

    public ArrayList<Integer> getIndexList(ArrayList<WeatherDay> list){
        possitions=new ArrayList<>();
        possitions.add(0);
        for(int i =0; i<list.size()-1;i++){
            if(list.get(i).getTime().equals(list.get(i+1).getTime())){
                possitions.add(i);
            }
        }
        return possitions;
    }
}
