package com.gmail.mashaduk1996.weather;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class SettingsActivity extends AppCompatActivity {
    private RadioButton eng, rus, metric, imperial, up, down;
    public final String LANGUAGE = "language";
    final String UNITS = "units";
    final String PLACE = "place";
    private SharedPreferences sPref;
private Button saveBtn;
    private void init() {
        eng = findViewById(R.id.english);
        rus = findViewById(R.id.russian);
        metric = findViewById(R.id.metric);
        imperial = findViewById(R.id.imperial);
        metric.setChecked(true);
        saveBtn=findViewById(R.id.saveBtn);
        up=findViewById(R.id.toolbarSetUp);
        down=findViewById(R.id.toolbarSetDown);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        init();
        loadSettings();
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        saveSettings();
    }
//Сохранение настроек
    private void saveSettings() {
        String lang, units, place;
        sPref = getSharedPreferences("SettingsPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        if (eng.isChecked()) {
            lang = "eng";
            editor.putString(LANGUAGE, lang);
        }
        if (rus.isChecked()) {
            lang = "ru";
            editor.putString(LANGUAGE, lang);
        }

        if (metric.isChecked()) {
            units = "metric";
            editor.putString(UNITS, units);
        }
        if (imperial.isChecked()) {
            units = "imperial";
            editor.putString(UNITS, units);
        }
        if(up.isChecked()){
            place = "up";
            editor.putString(PLACE, place);
        }
        if(down.isChecked()){
            place = "down";
            editor.putString(PLACE, place);
        }
        editor.apply();
    }
//Отображение положения настроек
    private void loadSettings() {
        sPref = getSharedPreferences("SettingsPref", MODE_PRIVATE);
        String lang = sPref.getString("language", "");
        String units = sPref.getString("units", "");
        String place = sPref.getString("place","");
        if (lang.equals("ru"))
            rus.setChecked(true);
        else eng.setChecked(true);

        if(units.equals("metric"))
            metric.setChecked(true);
        else imperial.setChecked(true);

        if(place.equals("up"))
            up.setChecked(true);
        else down.setChecked(true);
    }
}
