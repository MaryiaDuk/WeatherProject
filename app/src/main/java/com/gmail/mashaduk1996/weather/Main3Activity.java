package com.gmail.mashaduk1996.weather;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.gmail.mashaduk1996.weather.database.DBHelper;

public class Main3Activity extends AppCompatActivity {
    private DBHelper mDBHelper;
    private SQLiteDatabase mDb;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        textView = findViewById(R.id.dbText);
        mDBHelper = new DBHelper(this);
        mDb = mDBHelper.getWritableDatabase();
        String name = "";
        Cursor cursor = mDb.rawQuery("SELECT * FROM Cities", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            name += cursor.getString(3) + " | ";
            cursor.moveToNext();
        }
        cursor.close();

        textView.setText(name);
    }
}
