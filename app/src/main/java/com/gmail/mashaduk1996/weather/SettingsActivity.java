package com.gmail.mashaduk1996.weather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

public class SettingsActivity extends PreferenceActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  PreferenceManager.setDefaultValues(getBaseContext(), R.xml.pref, false);
        addPreferencesFromResource(R.xml.pref);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SettingsActivity.this, StartActivity.class);
        startActivity(intent);
    }


}
