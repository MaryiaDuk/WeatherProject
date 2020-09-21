package com.gmail.mashaduk1996.weather.adapters;

import android.icu.util.Calendar;
import android.support.annotation.NonNull;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.gmail.mashaduk1996.weather.App;
import com.gmail.mashaduk1996.weather.MVP.MainContract;
import com.gmail.mashaduk1996.weather.R;
import com.gmail.mashaduk1996.weather.models.WeatherDay;
import com.gmail.mashaduk1996.weather.models.WeatherForecast;
import com.gmail.mashaduk1996.weather.ui.IconsConverter;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<WeatherDay> weatherDayList;
    private final int TYPE_ITEM1 = 0;
    private final int TYPE_ITEM2 = 1;

    public RecyclerViewAdapter(ArrayList<WeatherDay> weatherDayList) {
        this.weatherDayList = weatherDayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        //   View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_weather_forecast, viewGroup, false);
        switch(i){
            case TYPE_ITEM1:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.header_item_layout, viewGroup, false);
                break;
            case TYPE_ITEM2:
                v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_weather, viewGroup, false);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + i);
        }
        //View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_weather, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        WeatherDay weatherDay = weatherDayList.get(i);
        int type = getItemViewType(i);
        String date = String.format("%02d.%02d.%d", weatherDay.getDate().get(Calendar.DAY_OF_MONTH), weatherDay.getDate().get(Calendar.MONTH) + 1, weatherDay.getDate().get(Calendar.YEAR));
        String time = String.format("%02d:%02d", weatherDay.getDate().get(Calendar.HOUR_OF_DAY), weatherDay.getDate().get(Calendar.MINUTE));

        switch (type) {
            case TYPE_ITEM1:
                viewHolder.header.setText(date);
                break;
            case TYPE_ITEM2:
                viewHolder.wind.setText(weatherDay.getWind());
                viewHolder.pressure.setText(weatherDay.getPressureMmHg(weatherDayList.get(i).getPressure()));
                viewHolder.humidity.setText(weatherDay.getHumidity());
                viewHolder.temperature.setText(weatherDay.getTempInteger());
                IconsConverter converter = new IconsConverter();
                converter.setIcon(weatherDay.getIconUrl(), viewHolder.weatherIcon);
                //Glide.with(viewHolder.itemView.getContext()).load(weatherDayList.get(i).getIconUrl())
                viewHolder.itemView.setBackgroundColor(R.color.background);

                // Log.d("WeatherAAA", String.valueOf(weatherDayList.get(i).getDate().get(android.icu.util.Calendar.HOUR_OF_DAY)));
                //String date = String.format("%02d.%02d.%d", weatherDay.getDate().get(Calendar.DAY_OF_MONTH), weatherDay.getDate().get(Calendar.MONTH) + 1, weatherDay.getDate().get(Calendar.YEAR));
                viewHolder.date.setText(date);
                viewHolder.time.setText(time);
                break;
        }



    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position==3) return TYPE_ITEM1;
        return TYPE_ITEM2;
     //   return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return weatherDayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView wind, pressure, humidity, temperature, date, time, header;
        ImageView weatherIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            header = itemView.findViewById(R.id.header_tv);
            date = itemView.findViewById(R.id.forecastDate);
            time = itemView.findViewById(R.id.forecastTime);
            temperature = itemView.findViewById(R.id.temperature_item);
            wind = itemView.findViewById(R.id.wind);
            pressure = itemView.findViewById(R.id.pressure);
            humidity = itemView.findViewById(R.id.humidity);
            weatherIcon = itemView.findViewById(R.id.imageView);
        }
    }


}
