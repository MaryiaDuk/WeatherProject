package com.gmail.mashaduk1996.weather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.gmail.mashaduk1996.weather.adapters.RecyclerViewAdapter;
import com.gmail.mashaduk1996.weather.api.RetrofitClient;
import com.gmail.mashaduk1996.weather.api.WeatherAPI;
import com.gmail.mashaduk1996.weather.models.WeatherForecast;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class WeatherForecastActivity extends AppCompatActivity {
    private RecyclerView forecastRecycler;

private RecyclerViewAdapter adapter;
    WeatherAPI.ApiInterface api;
    private String units, lang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getIntent().getExtras();
        String city = arguments.get("city").toString();

        setContentView(R.layout.activity_weather_forecast);
        final String key = WeatherAPI.KEY;
        Retrofit retrofit = RetrofitClient.getInstance();
        api = retrofit.create(WeatherAPI.ApiInterface.class);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(App.context);
        lang = sp.getString("lang", "1");
        if (lang.equals("1")) lang = "eng";
        if (lang.equals("2")) lang = "ru";
        units = sp.getString("temperature", "1");
        if (units.equals("1")) units = "metric";
        else units = "imperial";
        forecastRecycler = findViewById(R.id.recyclerView);
        forecastRecycler.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        forecastRecycler.setLayoutManager(layoutManager);
        Observable<WeatherForecast> weatherForecastObservable = api.getForecast(city, units, key, lang);
        weatherForecastObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DisposableObserver<WeatherForecast>() {
            @Override
            public void onNext(WeatherForecast weatherForecast) {
                //  Log.d("WeatherAAA", weatherForecast.getItems().get(0).getCity());
                Log.d("WeatherAAA", "onNext");
                Log.d("WeatherAAA", weatherForecast.getItems().get(0).getPressure().toString());
                adapter=new RecyclerViewAdapter(weatherForecast.getItems());
                forecastRecycler.setAdapter(adapter);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(WeatherForecastActivity.this, MainActivity.class));
    }
}
