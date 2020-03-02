package com.gmail.mashaduk1996.weather;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.gmail.mashaduk1996.weather.database.DBHelper;

public class StartActivity extends AppCompatActivity {
    private SharedPreferences prefs = null;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences("com.gmail.mashaduk1996.weather", MODE_PRIVATE);
        //ContentValues cv = new ContentValues();


    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent firstRun = new Intent(StartActivity.this, FirstRunActivity.class);
        Intent intent = new Intent(StartActivity.this, MainActivity.class);
        if (prefs.getBoolean("first_run", true)) {
            prefs.edit().putBoolean("first_run", false).apply();
            startActivity(firstRun);
        } else {
            startActivity(intent);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
