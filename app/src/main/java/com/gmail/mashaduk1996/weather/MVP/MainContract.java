package com.gmail.mashaduk1996.weather.MVP;

import com.gmail.mashaduk1996.weather.models.WeatherDay;

public interface MainContract {
    interface View {
        void loadData(WeatherDay data, String language, String timeFormat);

        void showErrorToast(String error);

        void showErrorMessage();

        void showLoadingDialog();

        void hideLoadingDialog();

    }

    interface Presenter {

        void findButtonWasClicked(String name);
        void onActivityCreate();
        void onGeoButtonWasClicked();
    }

    interface Repository {
        WeatherDay getWeather(String city);

    }
}
