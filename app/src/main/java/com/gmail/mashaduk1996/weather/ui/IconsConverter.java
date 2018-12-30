package com.gmail.mashaduk1996.weather.ui;

import android.widget.ImageView;

import com.gmail.mashaduk1996.weather.R;

public class IconsConverter {

    public void setIcon(String url, ImageView image) {
        switch (url) {
            case "http://openweathermap.org/img/w/01d.png":
                image.setImageResource(R.drawable.ic_sun);
                break;
            case "http://openweathermap.org/img/w/01n.png":
                image.setImageResource(R.drawable.ic_night);
                break;
            case "http://openweathermap.org/img/w/02d.png":
                image.setImageResource(R.drawable.ic_partly_cloudy_day);
                break;
            case "http://openweathermap.org/img/w/02n.png":
                image.setImageResource(R.drawable.ic_partly_cloudy_night);
                break;
            case "http://openweathermap.org/img/w/03d.png":
                image.setImageResource(R.drawable.ic_cloudy);
                break;
            case "http://openweathermap.org/img/w/03n.png":
                image.setImageResource(R.drawable.ic_cloudy);
                break;
            case "http://openweathermap.org/img/w/04d.png":
                image.setImageResource(R.drawable.ic_cloudy);
                break;
            case "http://openweathermap.org/img/w/04n.png":
                image.setImageResource(R.drawable.ic_cloudy);
                break;
            case "http://openweathermap.org/img/w/09d.png":
                image.setImageResource(R.drawable.ic_rain);
                break;
            case "http://openweathermap.org/img/w/09n.png":
                image.setImageResource(R.drawable.ic_rain);
                break;
            case "http://openweathermap.org/img/w/10d.png":
                image.setImageResource(R.drawable.ic_rain);
                break;
            case "http://openweathermap.org/img/w/10n.png":
                image.setImageResource(R.drawable.ic_rain);
                break;
            case "http://openweathermap.org/img/w/11d.png":
                image.setImageResource(R.drawable.ic_thnderstorm);
                break;
            case "http://openweathermap.org/img/w/11n.png":
                image.setImageResource(R.drawable.ic_thnderstorm);
                break;
            case "http://openweathermap.org/img/w/13d.png":
                image.setImageResource(R.drawable.ic_snow);
                break;
            case "http://openweathermap.org/img/w/13n.png":
                image.setImageResource(R.drawable.ic_snow);
                break;
            case "http://openweathermap.org/img/w/50d.png":
                image.setImageResource(R.drawable.ic_fog);
                break;
            case "http://openweathermap.org/img/w/50n.png":
                image.setImageResource(R.drawable.ic_fog);
                break;
        }
    }
}
