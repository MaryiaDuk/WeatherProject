package com.gmail.mashaduk1996.weather.MVP;

import android.app.Activity;
import android.content.Context;

import com.gmail.mashaduk1996.weather.models.WeatherDay;

public interface MainContract {
    interface View {
        void loadData(WeatherDay data, String language);

        String getCity();

        void showErrorToast(String error);

        void showErrorMessage();

        void showLoadingDialoge();

        void hideLoadingDialoge();

    }

    interface Presenter {
        void findButtonWasClicked();
        void onActivityCreate();
        void onGeoButtonWasClicked();
    }

    interface Repository {
        WeatherDay getWeather(String city);

    }
}
