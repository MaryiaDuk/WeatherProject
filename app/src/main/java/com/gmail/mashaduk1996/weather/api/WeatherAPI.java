package com.gmail.mashaduk1996.weather.api;

import com.gmail.mashaduk1996.weather.models.WeatherDay;
import com.gmail.mashaduk1996.weather.models.WeatherForecast;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class WeatherAPI {
    public static String KEY = "b083cab9f857f94bcba8b63de3f71b37";
    public static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    private static Retrofit retrofit = null;

    public interface ApiInterface {
        @GET("weather")
        Call<WeatherDay> getToday(
                @Query("lat") Double lat,
                @Query("lon") Double lon,
                @Query("units") String units,
                @Query("appid") String appid,
                @Query("lang") String lang
        );

        @GET("weather")
        Call<WeatherDay> getByName(
                @Query("q") String name,
                @Query("units") String units,
                @Query("appid") String appid,
                @Query("lang") String lang
        );

        @GET("forecast")
        Call<WeatherForecast> getForecast(
                @Query("lat") Double lat,
                @Query("lon") Double lon,
                @Query("units") String units,
                @Query("appid") String appid
        );

        @GET("weather")
        Observable<WeatherDay> getWeather(@Query("lat") Double lat,
                                          @Query("lon") Double lon,
                                          @Query("units") String units,
                                          @Query("appid") String appid,
                                          @Query("lang") String lang
        );

        @GET("weather")
        Observable<WeatherDay> getWeatherByName(
                @Query("q") String name,
                @Query("units") String units,
                @Query("appid") String appid,
                @Query("lang") String lang
        );

        @GET("weather")
        Observable<WeatherDay> getWeatherById(
                @Query("id") String id,
                @Query("units") String units,
                @Query("appid") String appid,
                @Query("lang") String lang
        );

        @GET("weather")
        Observable<WeatherDay> getWeatherByName(
                @Query("q") String name,

                @Query("appid") String appid

        );

        @GET("forecast")
        Observable<WeatherForecast> getForecast(
                @Query("q") String name,
                @Query("units") String units,
                @Query("appid") String appid,
                @Query("lang") String lang
        );
    }

//    public static Retrofit getClient() {
//        if (retrofit == null) {
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();
//                   // .addConverterFactory(GsonConverterFactory.create()).build();
//        }
//        return retrofit;
//    }
}
