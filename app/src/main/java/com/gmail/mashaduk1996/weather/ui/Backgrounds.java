package com.gmail.mashaduk1996.weather.ui;

import android.view.ViewGroup;

import com.gmail.mashaduk1996.weather.R;

public class Backgrounds {

    public void set(String url, ViewGroup layout) {
        switch (url) {
            case "http://openweathermap.org/img/w/01d.png":
                layout.setBackgroundResource(R.drawable.clear_day_bg);
                break;
            case "http://openweathermap.org/img/w/01n.png":
                layout.setBackgroundResource(R.drawable.clear_night_bg);
                break;
            case "http://openweathermap.org/img/w/02d.png":
            case "http://openweathermap.org/img/w/03d.png":
                layout.setBackgroundResource(R.drawable.cloudy);
                break;
            case "http://openweathermap.org/img/w/02n.png":
            case "http://openweathermap.org/img/w/03n.png":
            case "http://openweathermap.org/img/w/04n.png":
                layout.setBackgroundResource(R.drawable.partly_cloudy_night_bg);
                break;
            case "http://openweathermap.org/img/w/04d.png":
                layout.setBackgroundResource(R.drawable.partly_cloudy);
                break;
            case "http://openweathermap.org/img/w/09d.png":
            case "http://openweathermap.org/img/w/09n.png":
            case "http://openweathermap.org/img/w/10d.png":
            case "http://openweathermap.org/img/w/10n.png":
                layout.setBackgroundResource(R.drawable.rain_bg);
                break;
            case "http://openweathermap.org/img/w/11d.png":
            case "http://openweathermap.org/img/w/11n.png":
                layout.setBackgroundResource(R.drawable.thunderstorm);
                break;
            case "http://openweathermap.org/img/w/13d.png":
            case "http://openweathermap.org/img/w/13n.png":
                layout.setBackgroundResource(R.drawable.snow);
                break;
            case "http://openweathermap.org/img/w/50d.png":
            case "http://openweathermap.org/img/w/50n.png":
                layout.setBackgroundResource(R.drawable.fog);
                break;
            default:
                layout.setBackgroundResource(R.drawable.bg_df_day);
        }
    }
}
