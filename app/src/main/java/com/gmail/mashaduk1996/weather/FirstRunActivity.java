package com.gmail.mashaduk1996.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class FirstRunActivity extends AppCompatActivity {
    Intent settingsIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_Light_Dialog_MinWidth);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_run);
        Button okBtn;
        okBtn=findViewById(R.id.okBtn);
        settingsIntent = new Intent(FirstRunActivity.this, SettingsActivity.class);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(settingsIntent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(settingsIntent);
    }
}
