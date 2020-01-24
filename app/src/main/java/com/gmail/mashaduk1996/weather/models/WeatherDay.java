package com.gmail.mashaduk1996.weather.models;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class WeatherDay {
    @SerializedName("wind")
    public Wind wind;
    @SerializedName("main")
    private WeatherTemp temp;
    @SerializedName("weather")
    private List<WeatherDescription> descriptions;
    @SerializedName("name")
    private String city;
    @SerializedName("dt")
    private long timestamp;
    @SerializedName("sys")
    private WeatherSys sys;


    public WeatherDay(WeatherTemp temp, List<WeatherDescription> descriptions, WeatherSys sys) {
        this.temp = temp;
        this.descriptions = descriptions;
        this.sys = sys;
    }

    public Calendar getDate() {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(timestamp * 1000);
        return date;
    }

    public String getTemp() {
        return String.valueOf(temp.temp.intValue());
    }

    public Double getPressure() {
        return temp.pressure;
    }

    public String getPressureMmHg(Double pressure){
        Double mmRtSt;
        mmRtSt=pressure*100/133.3224;
        int mm= mmRtSt.intValue();
        return String.valueOf(mm);
    }

    public String getTempMin() {
        return String.valueOf(temp.temp_min);
    }

    public String getTempMax() {
        return String.valueOf(temp.temp_max);
    }

    public String getTempInteger() {
        return String.valueOf(temp.temp.intValue());
    }

    public String getWind() {
        return String.valueOf(wind.speed);
    }

    public Double getDeg() {
        return wind.deg;
    }

    public String getDiscr() {
        return descriptions.get(0).description;
    }

    public String getTempWithDegree() {
        return String.valueOf(temp.temp.intValue()) + "\u00B0";
    }

    public String getCity() {
        return city;
    }



    public Calendar getSunset() {
        Calendar day = Calendar.getInstance();
        day.setTimeInMillis(sys.sunset * 1000);
        return day;
    }

    public Calendar getSunrise() {
        Calendar day = Calendar.getInstance();
        day.setTimeInMillis(sys.sunrise * 1000);
        return day;
    }

    public String getFormatSunset(){
        Calendar day=Calendar.getInstance();
        day.setTimeInMillis(sys.sunset*1000);
        SimpleDateFormat format = new SimpleDateFormat( "h:mm a");
        return format.format(day.getTime());
    }
    public String getFormatSunrise(){
        Calendar day=Calendar.getInstance();
        day.setTimeInMillis(sys.sunrise*1000);
        SimpleDateFormat format = new SimpleDateFormat( "h:mm a");
        return format.format(day.getTime());
    }
    public String get24FormatSunset(){
        Calendar day=Calendar.getInstance();
        day.setTimeInMillis(sys.sunset*1000);
        SimpleDateFormat format = new SimpleDateFormat( "hh:mm");
        return format.format(day.getTime());
    }

    public String get24FormatSunrise(){
        Calendar day=Calendar.getInstance();
        day.setTimeInMillis(sys.sunrise*1000);
        SimpleDateFormat format = new SimpleDateFormat( "hh:mm");
        return format.format(day.getTime());
    }
    public String getIcon() {
        return descriptions.get(0).icon;
    }

    public String getCountry() {
        return String.valueOf(sys.country);
    }

    public String getHumidity() {
        return String.valueOf(temp.humidity.intValue()) + "\u0025";
    }

    public String getIconUrl() {
        return "http://openweathermap.org/img/w/" + descriptions.get(0).icon + ".png";
    }

    public String deg(double d) {
        String deg = null;
        if (d >= 337.5 || d <= 22.5) {
            deg = "С";
        } else if (d > 22.5 && d <= 67.5) {
            deg = "СВ";
        } else if (d > 67.5 && d <= 112.5) {
            deg = "В";
        } else if (d > 112.5 && d <= 157.5) {
            deg = "ЮВ";
        } else if (d > 157.5 && d <= 202.5) {
            deg = "Ю";
        } else if (d > 202.5 && d <= 247.5) {
            deg = "ЮЗ";
        } else if (d > 247.5 && d <= 295.5) {
            deg = "З";
        } else if (d > 292.5 && d <= 337.5) {
            deg = "СЗ";
        }
        return deg;
    }

    public static class Wind {
        Double speed =0.0;
        Double deg=0.0;

    }

    public class WeatherTemp {
        Double temp;
        Double temp_min;
        Double temp_max;
        Double pressure;
        Double humidity;
    }

    public class WeatherSys {
        String country;
        long sunrise;
        long sunset;
    }

    public class WeatherDescription {
        String description;
        String icon;
    }
}