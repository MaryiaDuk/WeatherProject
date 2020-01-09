package com.gmail.mashaduk1996.weather.ui;

public class CountryConverter {
    public String showFullName(String country) {
        switch (country) {
            case "BY":
                country = "Беларусь";
                break;
            case "RU":
                country = "Росиия";
                break;
            case "US":
                country = "США";
                break;
            case "CN":
                country = "Китай";
                break;
            case "UA":
                country = "Украина";
                break;
            case "AU":
                country = "Австралия";
                break;
            case "GB":
                country = "Великобритния";
                break;
            case "AT":
                country = "Австрия";
                break;
            case "AZ":
                country = "Азербайджан";
                break;
        }
        return country;
    }
}
