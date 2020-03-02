package com.gmail.mashaduk1996.weather.adapters;

import android.icu.util.Calendar;
import android.support.annotation.NonNull;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.gmail.mashaduk1996.weather.App;
import com.gmail.mashaduk1996.weather.R;
import com.gmail.mashaduk1996.weather.models.WeatherDay;
import com.gmail.mashaduk1996.weather.models.WeatherForecast;
import com.gmail.mashaduk1996.weather.ui.IconsConverter;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<WeatherDay> weatherDayList;

    public RecyclerViewAdapter(List<WeatherDay> weatherDayList) {
        this.weatherDayList = weatherDayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //   View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_weather_forecast, viewGroup, false);
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_weather, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        WeatherDay weatherDay = weatherDayList.get(i);
        String date = String.format("%02d.%02d.%d", weatherDay.getDate().get(Calendar.DAY_OF_MONTH), weatherDay.getDate().get(Calendar.MONTH) + 1, weatherDay.getDate().get(Calendar.YEAR));
        viewHolder.wind.setText(weatherDay.getWind());
        viewHolder.pressure.setText(weatherDay.getPressureMmHg(weatherDayList.get(i).getPressure()));
        viewHolder.humidity.setText(weatherDay.getHumidity());
        viewHolder.temperature.setText(weatherDay.getTempInteger());
        IconsConverter converter = new IconsConverter();
        converter.setIcon(weatherDay.getIconUrl(), viewHolder.weatherIcon);
        //Glide.with(viewHolder.itemView.getContext()).load(weatherDayList.get(i).getIconUrl())
        viewHolder.itemView.setBackgroundColor(R.color.background);
        // Log.d("WeatherAAA", String.valueOf(weatherDayList.get(i).getDate().get(android.icu.util.Calendar.HOUR_OF_DAY)));
        String time = String.format("%02d:%02d", weatherDay.getDate().get(Calendar.HOUR_OF_DAY), weatherDay.getDate().get(Calendar.MINUTE));
        //String date = String.format("%02d.%02d.%d", weatherDay.getDate().get(Calendar.DAY_OF_MONTH), weatherDay.getDate().get(Calendar.MONTH) + 1, weatherDay.getDate().get(Calendar.YEAR));
        viewHolder.date.setText(date);
        viewHolder.time.setText(time);

    }

    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return weatherDayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView wind, pressure, humidity, temperature, date, time;
        ImageView weatherIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
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
