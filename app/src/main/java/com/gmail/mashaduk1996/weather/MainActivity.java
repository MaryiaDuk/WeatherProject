package com.gmail.mashaduk1996.weather;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.mashaduk1996.weather.api.RetrofitClient;
import com.gmail.mashaduk1996.weather.api.WeatherAPI;
import com.gmail.mashaduk1996.weather.fragments.MainFragment;
import com.gmail.mashaduk1996.weather.geolocation.Geolocation;
import com.gmail.mashaduk1996.weather.models.WeatherDay;
import com.gmail.mashaduk1996.weather.ui.Backgrounds;
import com.gmail.mashaduk1996.weather.ui.CountryConverter;
import com.gmail.mashaduk1996.weather.ui.EnterCityCirilic;
import com.gmail.mashaduk1996.weather.ui.IconsConverter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private String lang, place;
    int lang1;
    private TextView temp, city, pressure, descr, date, humidity, wind;
    private ImageView imageView;
    private WeatherAPI.ApiInterface api;
    private Backgrounds backgrounds;
    private EditText enterCity;
    private String name = null;
    private ImageButton addCity, locbutton, settingsButton;
    private IconsConverter icons;
    private SharedPreferences sPref;
    private WeatherDay data;
    private Toolbar toolbar;
    private LinearLayout linearLayout;
    private String units;
    private EnterCityCirilic enter;
    private SimpleDateFormat dateFormat;
    private ConstraintLayout constraintLayout, progressLayout, errorLayout;
    private SharedPreferences sp;
    private static final String pattern = "dd MMMM yyyy";
    private FragmentManager fragmentManager;

    //Инициализация
    @SuppressLint("SimpleDateFormat")
    private void init() {
        imageView = findViewById(R.id.imageView);
        Retrofit retrofit = RetrofitClient.getInstance();
        api = retrofit.create(WeatherAPI.ApiInterface.class);
        temp = findViewById(R.id.textView);
        city = findViewById(R.id.cityTextView);
        city.setSelected(true);
        pressure = findViewById(R.id.pressure);
        descr = findViewById(R.id.descriptioTV);
        date = findViewById(R.id.dateTextView);
        humidity = findViewById(R.id.humidity);
        wind = findViewById(R.id.wind);
        backgrounds = new Backgrounds();
        icons = new IconsConverter();
        enterCity = findViewById(R.id.enterCity);
        locbutton = findViewById(R.id.locationButton);
        addCity = findViewById(R.id.addCity);
        enterCity.setCursorVisible(false);
        toolbar = findViewById(R.id.toolbar);
        linearLayout = findViewById(R.id.root_layout);
        enter = new EnterCityCirilic();
        dateFormat = new SimpleDateFormat(pattern);
        constraintLayout = findViewById(R.id.constraint_layout);
        progressLayout = findViewById(R.id.progressLayout);
        settingsButton = findViewById(R.id.settingsButton);
        errorLayout = findViewById(R.id.layout_error);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = PreferenceManager.getDefaultSharedPreferences(App.context);
        //чтение файла настроек
        //Layout в зависимости от положения Toolbar
        boolean toolbarPlace = sp.getBoolean("toolbarPlace", false);

        lang = sp.getString("lang", "1");
        if (lang.equals("1")) lang = "eng";
        if (lang.equals("2")) lang = "ru";
        units = sp.getString("temperature", "1");
        if (units.equals("1")) units = "metric";
        else units = "imperial";
        if (toolbarPlace) setContentView(R.layout.activity_main);
        else setContentView(R.layout.activity_main2);
        //Разрешение на доступ к геолокации
        int permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            Geolocation.SetUpLocationListener(this);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }
        //инициализация
        init();
        name = sp.getString("defaultCity", "");
        //   name = loadCity();
        date.setText(dateFormat.format(Calendar.getInstance().getTime()));
        setSupportActionBar(toolbar);
        getWeatherByName();
        addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWeatherByName();
            }
        });
        locbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWeatherByCoord(city);
            }
        });

        enterCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterCity.setCursorVisible(true);
            }
        });
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSettings();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Geolocation.SetUpLocationListener(this);
                }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        name = enterCity.getText().toString().trim();
    }

    public void getWeatherByCoord(View v) {
        Double lat;
        Double lng;
        try {
            //чтение координат
            lat = Geolocation.imHere.getLatitude();
            lng = Geolocation.imHere.getLongitude();

            final String key = WeatherAPI.KEY;
            //      Observable<WeatherDay> weatherCord = api.getWeather(lat, lng, units, key, lang);
            Observable<WeatherDay> weatherCord = api.getWeather(lat, lng, units, key, lang);
            weatherCord.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<WeatherDay>() {
                @Override
                public void onSubscribe(Disposable d) {
                }

                @Override
                public void onNext(WeatherDay weatherDay) {
                    if (progressLayout.getVisibility() == View.VISIBLE)
                        progressLayout.setVisibility(View.GONE);
                    loadData(weatherDay);
                    if (constraintLayout.getVisibility() == View.GONE) {
                        constraintLayout.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onError(Throwable e) {
                    //     temp.setText("-");
                    if (progressLayout.getVisibility() == View.VISIBLE) {
                        progressLayout.setVisibility(View.GONE);
                        errorLayout.setVisibility(View.VISIBLE);
                    }
                    Toast errorToast = Toast.makeText(getApplicationContext(), "Ошибка получения данных", Toast.LENGTH_SHORT);
                    errorToast.show();
                }

                @Override
                public void onComplete() {
                }
            });
        } catch (NullPointerException e) {
            Toast error = Toast.makeText(getApplicationContext(), "Отсутствует доступ к геолокации", Toast.LENGTH_SHORT);
            error.show();
        }
    }

    public void getWeatherByName() {
        final String key = WeatherAPI.KEY;
        if (enterCity.getText().length() != 0) {
            name = enterCity.getText().toString();
        }
        name = enter.enterCirilicCty(name);
        if (name.isEmpty()) {
            Toast enterCity = Toast.makeText(getApplicationContext(), "Введите город", Toast.LENGTH_SHORT);
            enterCity.show();
        } else {

            Observable<WeatherDay> weatherDayObservable = api.getWeatherByName(name, units, key, lang);
            //    Observable<WeatherDay> weatherDayObservable = api.getWeatherByName(name, key);

            weatherDayObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<WeatherDay>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(WeatherDay weatherDay) {
                    if (progressLayout.getVisibility() == View.VISIBLE)
                        progressLayout.setVisibility(View.GONE);
                    loadData(weatherDay);
                    if (constraintLayout.getVisibility() == View.GONE) {
                        constraintLayout.setVisibility(View.VISIBLE);
                    }

//                        MainFragment mainFragment = (MainFragment) fragmentManager.findFragmentByTag("mainFragment");
//                        FragmentTransaction transaction = fragmentManager.beginTransaction();
//                        transaction.replace(R.id.constraint_layout, Objects.requireNonNull(mainFragment));
//                        transaction.commit();

                }

                @Override
                public void onError(Throwable e) {
                    if (progressLayout.getVisibility() == View.VISIBLE) {
                        progressLayout.setVisibility(View.GONE);
                        errorLayout.setVisibility(View.VISIBLE);
                    }
                    Toast errorToast = Toast.makeText(getApplicationContext(), "Ошибка получения данных", Toast.LENGTH_SHORT);
                    errorToast.show();
                    constraintLayout.setVisibility(ConstraintLayout.GONE);
                    linearLayout.setBackgroundResource(R.drawable.bg_df_day);

                }

                @Override
                public void onComplete() {
                }
            });
        }
    }

    @SuppressLint("SetTextI18n")
    private void loadData(WeatherDay data) {
        String name = data.getCity().trim();
        CountryConverter countryConverter = new CountryConverter();
        //   city.setText(enter.showCity(name) + ", " + countryConverter.showFullName(data.getCountry()));
        // city.setText(enter.showCity(name) + ", " + data.getCountry());
        //Locale locale = new Locale(data.getCountry().toLowerCase());

        Locale locale = new Locale("", data.getCountry());
        city.setText(enter.showCity(name) + ", " + locale.getDisplayCountry(new Locale("ru")));
        String url = data.getIconUrl();
        icons.setIcon(url, imageView);
        temp.setText(data.getTempWithDegree());
        pressure.setText(data.getPressureMmHg(data.getPressure()) + " мм рт ст");
        descr.setText(data.getDiscr().toUpperCase());
        humidity.setText(data.getHumidity());
        dateFormat.setCalendar(data.getDate());
        date.setText(dateFormat.format(data.getDate().getTime()));
        wind.setText(data.getWind() + " m/s" + " " + data.deg(data.getDeg()));
        backgrounds.set(url, linearLayout);
    }

    public void startSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}