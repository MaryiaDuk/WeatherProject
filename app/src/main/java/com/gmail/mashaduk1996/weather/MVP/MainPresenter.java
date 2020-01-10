package com.gmail.mashaduk1996.weather.MVP;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.gmail.mashaduk1996.weather.App;
import com.gmail.mashaduk1996.weather.api.RetrofitClient;
import com.gmail.mashaduk1996.weather.api.WeatherAPI;
import com.gmail.mashaduk1996.weather.geolocation.Geolocation;
import com.gmail.mashaduk1996.weather.models.WeatherDay;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainPresenter implements MainContract.Presenter {
    private MainContract.View mainView;
    private MainContract.Repository mainRepository;
    private String city;

    private WeatherAPI.ApiInterface api;
    private SharedPreferences sp;
    private String lang, units;

    private void init() {
        Retrofit retrofit = RetrofitClient.getInstance();
        api = retrofit.create(WeatherAPI.ApiInterface.class);
        sp = PreferenceManager.getDefaultSharedPreferences(App.context);
        lang = sp.getString("lang", "1");
        if (lang.equals("1")) lang = "eng";
        if (lang.equals("2")) lang = "ru";
        units = sp.getString("temperature", "1");
        if (units.equals("1")) units = "metric";
        else units = "imperial";
        city = sp.getString("defaultCity", "");
    }

    public MainPresenter(MainContract.View mainView) {
        this.mainView = mainView;
        this.mainRepository = new MainRepository();
        init();
    }

    @Override
    public void findButtonWasClicked() {
        city = mainView.getCity();
        if (city.length() != 0) {
            mainView.showLoadingDialoge();
            getWeatherByName();
        }
        if (city.isEmpty()) mainView.showErrorToast("Введите город");
    }

    @Override
    public void onActivityCreate() {
        mainView.showLoadingDialoge();
        getWeatherByName();
    }

    @Override
    public void onGeoButtonWasClicked() {
        mainView.showLoadingDialoge();
        getWeatherByCord();
    }


    private void getWeatherByName() {
        final String key = WeatherAPI.KEY;
        Observable<WeatherDay> weatherDayObservable = api.getWeatherByName(city, units, key, lang);
        weatherDayObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DisposableObserver<WeatherDay>() {
            @Override
            public void onNext(WeatherDay weatherDay) {
                mainView.loadData(weatherDay);
            }

            @Override
            public void onError(Throwable e) {
                mainView.hideLoadingDialoge();
                mainView.showErrorMessage();
            }

            @Override
            public void onComplete() {
                mainView.hideLoadingDialoge();
            }
        });
    }

    private void getWeatherByCord() {
        Double lat;
        Double lng;
        try {
            lat = Geolocation.imHere.getLatitude();
            lng = Geolocation.imHere.getLongitude();

            final String key = WeatherAPI.KEY;
            Observable<WeatherDay> weatherCord = api.getWeather(lat, lng, units, key, lang);

            weatherCord.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DisposableObserver<WeatherDay>() {
                @Override
                public void onNext(WeatherDay weatherDay) {
                    mainView.loadData(weatherDay);
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {
                    mainView.hideLoadingDialoge();
                }
            });
        } catch (NullPointerException e) {
            mainView.showErrorToast("Ошибка геолокации");
        }
    }
}
