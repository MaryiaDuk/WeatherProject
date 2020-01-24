package com.gmail.mashaduk1996.weather;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.mashaduk1996.weather.MVP.MainContract;
import com.gmail.mashaduk1996.weather.MVP.MainPresenter;
import com.gmail.mashaduk1996.weather.geolocation.Geolocation;
import com.gmail.mashaduk1996.weather.models.WeatherDay;
import com.gmail.mashaduk1996.weather.ui.Backgrounds;
import com.gmail.mashaduk1996.weather.ui.IconsConverter;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MainContract.View {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private TextView temp, city, pressure, descr, date, humidity, wind, sunset, sunrise;
    private ImageView imageView;
    private Backgrounds backgrounds;
    private EditText enterCity;
    private ImageButton addCity, locbutton, settingsButton;
    private IconsConverter icons;
    private Toolbar toolbar;
    private LinearLayout linearLayout, forecasrLayout;
    private SimpleDateFormat dateFormat;
    private ConstraintLayout constraintLayout, errorLayout;
    private SharedPreferences sp;
    private static final String pattern = "dd MMMM yyyy";
    private FragmentManager fragmentManager;
    private Locale russian, english;
    private MainContract.Presenter mainPresenter;
    private ProgressDialog progressDialog;

    //Инициализация
    @SuppressLint("SimpleDateFormat")
    private void init() {
        imageView = findViewById(R.id.imageView);
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
        dateFormat = new SimpleDateFormat(pattern);
        constraintLayout = findViewById(R.id.constraint_layout);
        settingsButton = findViewById(R.id.settingsButton);
        errorLayout = findViewById(R.id.layout_error);
        russian = new Locale("ru");
        english = new Locale("en");
        mainPresenter = new MainPresenter(this);
        sunrise = findViewById(R.id.sunrise);
        sunset = findViewById(R.id.sunset);
        forecasrLayout = findViewById(R.id.weatherLayout);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = PreferenceManager.getDefaultSharedPreferences(App.context);
        //Layout в зависимости от положения Toolbar
        boolean toolbarPlace = sp.getBoolean("toolbarPlace", false);
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
        setSupportActionBar(toolbar);
        mainPresenter.onActivityCreate();

        addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.findButtonWasClicked();
            }
        });
        locbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainPresenter.onGeoButtonWasClicked();
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
        forecasrLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent start = new Intent(MainActivity.this, WeatherForecastActivity.class);
                start.putExtra("city", city.getText());
                startActivity(start);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Geolocation.SetUpLocationListener(this);
            }
        }
    }

    @Override
    @SuppressLint("SetTextI18n")
    public void loadData(WeatherDay data, String language, String timeFormat) {
        if (errorLayout.getVisibility() == View.VISIBLE) {
            errorLayout.setVisibility(View.GONE);
            constraintLayout.setVisibility(View.VISIBLE);
        }
        Locale locale = new Locale("", data.getCountry());
        if (language.equals("ru"))
            city.setText(data.getCity() + ", " + locale.getDisplayCountry(russian));
        if (language.equals("eng"))
            city.setText(data.getCity() + ", " + locale.getDisplayCountry(english));
        String url = data.getIconUrl();
        //icons.setIcon(url, imageView);
        temp.setText(data.getTempWithDegree());
        pressure.setText(data.getPressureMmHg(data.getPressure()) + " мм рт ст");
        descr.setText(data.getDiscr().toUpperCase());
        humidity.setText(data.getHumidity());
        dateFormat.setCalendar(data.getDate());
        date.setText(dateFormat.format(data.getDate().getTime()));
        wind.setText(data.getWind() + " m/s" + " " + data.deg(data.getDeg()));
        backgrounds.set(url, linearLayout);
        if (timeFormat.equals("h:mm a")) {
            sunset.setText(data.getFormatSunset());
            sunrise.setText(data.getFormatSunrise());
        } else {
            sunset.setText(data.get24FormatSunset());
            sunrise.setText(data.get24FormatSunrise());
        }
        Log.d("WeatherAAA", data.getTemp());

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

    @Override
    public String getCity() {
        return enterCity.getText().toString().trim();
    }

    @Override
    public void showErrorToast(String error) {
        Toast toast = Toast.makeText(this, error, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void showLoadingDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Weather");
            progressDialog.setMessage("Loading...");
        }
        progressDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        progressDialog.hide();

    }

    @Override
    public void showErrorMessage() {
        linearLayout.setBackgroundResource(R.drawable.bg_df_day);
        if (constraintLayout.getVisibility() == View.VISIBLE) {
            constraintLayout.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
        }
    }
}