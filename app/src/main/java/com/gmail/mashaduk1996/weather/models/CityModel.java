package com.gmail.mashaduk1996.weather.models;

public class CityModel {
    private int id;
    private String name;
    private String name_rus;
    private String country;
    private String region;

    public CityModel(int id, String name, String name_rus, String country, String region) {
        this.id = id;
        this.name = name;
        this.name_rus = name_rus;
        this.country = country;
        this.region = region;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getName_rus() {
        return name_rus;
    }

    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }
}
