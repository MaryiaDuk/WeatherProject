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
import com.gmail.mashaduk1996.weather.geolocation.Geolocation;
import com.gmail.mashaduk1996.weather.models.WeatherDay;
import com.gmail.mashaduk1996.weather.ui.Backgrounds;
import com.gmail.mashaduk1996.weather.ui.EnterCityCirilic;
import com.gmail.mashaduk1996.weather.ui.IconsConverter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    final String SAVED_TEXT = "saved_text";
    private String lang, place;
    private TextView temp, city, pressure, descr, date, humidity, wind;
    private ImageView imageView;
    private WeatherAPI.ApiInterface api;
    private Backgrounds backgrounds;
    private EditText enterCiry;
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
    private ConstraintLayout constraintLayout, progressLayout;
    private SharedPreferences sp;
    private static final String pattern = "dd MMMM yyyy";

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
        enterCiry = findViewById(R.id.enterCity);
        locbutton = findViewById(R.id.locationButton);
        addCity = findViewById(R.id.addCity);
        enterCiry.setCursorVisible(false);
        toolbar = findViewById(R.id.toolbar);
        linearLayout = findViewById(R.id.root_layout);
        enter = new EnterCityCirilic();
        dateFormat = new SimpleDateFormat(pattern);
        constraintLayout = findViewById(R.id.constraint_layout);
        progressLayout = findViewById(R.id.progressLayout);
        settingsButton = findViewById(R.id.settingsButton);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = PreferenceManager.getDefaultSharedPreferences(App.context);
        //чтение файла настроек
        //Layout в зависимости от положения Toolbar
        boolean toolbarPlace = sp.getBoolean("toolbarPlace", false);
        lang=sp.getString("lang", "eng");
        units=sp.getString("temperature", "metric");
        if (toolbarPlace) setContentView(R.layout.activity_main);
        else setContentView(R.layout.activity_main2);
//        setContentView(R.layout.activity_main);
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
        date.setText(dateFormat.format(Calendar.getInstance().getTime()));
        setSupportActionBar(toolbar);
        if (name == null) getWeatherByCoord(city);
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
        getWeatherByName();
        enterCiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enterCiry.setCursorVisible(true);
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
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        name = enterCiry.getText().toString().trim();
    }

    public void getWeatherByCoord(View v) {
        Double lat;
        Double lng;
        try {
            //чтение координат
            lat = Geolocation.imHere.getLatitude();
            lng = Geolocation.imHere.getLongitude();

            final String key = WeatherAPI.KEY;
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
                    temp.setText("-");
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

        if (enterCiry.getText().length() == 0) {
            name = loadCity();
        } else name = enterCiry.getText().toString().trim();
        clear();
        name = enter.enterCirilicCty(name);
        if (name.isEmpty()) {
            Toast enterCity = Toast.makeText(getApplicationContext(), "Введите город", Toast.LENGTH_SHORT);
            enterCity.show();
        } else {

            Observable<WeatherDay> weatherDayObservable = api.getWeatherByName(name, units, key, lang);
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
                }

                @Override
                public void onError(Throwable e) {
                    temp.setText("-");
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

    private void save() {
        sPref = getSharedPreferences("CityPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        String cityS = city.getText().toString();
        editor.putString(SAVED_TEXT, cityS);
        editor.apply();
    }

    private void clear() {
        sPref = getSharedPreferences("CityPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString(SAVED_TEXT, null);
        editor.apply();
    }

    private String loadCity() {
        sPref = getSharedPreferences("CityPref", MODE_PRIVATE);
        return sPref.getString(SAVED_TEXT, "");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        save();
    }

    @SuppressLint("SetTextI18n")
    private void loadData(WeatherDay data) {
        String name = data.getCity();
        city.setText(enter.showCity(name) + ", " + data.getCountry());
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
        save();
        finish();
    }
}