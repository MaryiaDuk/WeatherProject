package com.gmail.mashaduk1996.weather.MVP;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.gmail.mashaduk1996.weather.App;
import com.gmail.mashaduk1996.weather.api.RetrofitClient;
import com.gmail.mashaduk1996.weather.api.WeatherAPI;
import com.gmail.mashaduk1996.weather.models.WeatherDay;


import retrofit2.Retrofit;

public class MainRepository implements MainContract.Repository {
    WeatherDay weather;
    private WeatherAPI.ApiInterface api;
    private SharedPreferences sp;
    private String lang, units;

    void init() {
        Retrofit retrofit = RetrofitClient.getInstance();
        api = retrofit.create(WeatherAPI.ApiInterface.class);
        sp = PreferenceManager.getDefaultSharedPreferences(App.context);
        lang = sp.getString("lang", "1");
        if (lang.equals("1")) lang = "eng";
        if (lang.equals("2")) lang = "ru";
        units = sp.getString("temperature", "1");
        if (units.equals("1")) units = "metric";
        else units = "imperial";
    }

    public WeatherDay getWeather(String city) {
        return null;
    }
}
