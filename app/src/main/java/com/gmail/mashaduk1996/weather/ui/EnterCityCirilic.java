package com.gmail.mashaduk1996.weather.ui;

public class EnterCityCirilic {
    private String cityName;

    public String enterCirilicCty(String str) {
        switch (str) {
            default:
                cityName = str;
                break;
            case "Киев":
                cityName = "Kiev";
                break;
            case "Пекин":
                cityName = "Beijing";
                break;
        }
        return cityName;
    }

    public String showCity(String str) {
        switch (str) {
            case "Polatsk":
                str = "Полоцк";
                break;
            case "Beijing":
                str = "Пекин";
                break;
        }
        return str;
    }
}
