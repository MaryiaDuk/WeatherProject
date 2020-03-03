package com.gmail.mashaduk1996.weather;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.SearchView;
import android.widget.TextView;

import com.gmail.mashaduk1996.weather.adapters.CityRecyclerAdapter;
import com.gmail.mashaduk1996.weather.database.DBHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Main3Activity extends AppCompatActivity {
    private DBHelper mDBHelper;
    private SQLiteDatabase mDb;
    SearchView searchView;
    TextView textView;
    Toolbar toolbar;
    Menu menu;
    ArrayList<String> list;
    RecyclerView recyclerView;
CityRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        textView = findViewById(R.id.dbText);
        mDBHelper = new DBHelper(this);
        mDb = mDBHelper.getReadableDatabase();
        String name = "";
        toolbar = findViewById(R.id.toolbar_test);
        setSupportActionBar(toolbar);
        //    setActionBar(toolbar);
        list = new ArrayList<>();
        Cursor cursor = mDb.rawQuery("SELECT * FROM Cities", null);
        Log.d("AAAAAA7", "Error");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            name += cursor.getString(3) + " | ";
            list.add(cursor.getString(3));
            cursor.moveToNext();
        }
        cursor.close();
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                return s.compareTo(t1);
            }
        });
        recyclerView = findViewById(R.id.cityRecycler);
        adapter = new CityRecyclerAdapter(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        textView.setText(name);
        textView.setText(String.valueOf(list.size()));
        Log.d("AAAAAA7", String.valueOf(list.size()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_test, menu);
        MenuItem item = menu.findItem(R.id.app_bar_search);
        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        searchView = (SearchView) item.getActionView();
        searchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

}

