package com.gmail.mashaduk1996.weather.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
    List<WeatherDay> weatherDayList;

    public RecyclerViewAdapter(List<WeatherDay> weatherDayList) {
        this.weatherDayList = weatherDayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_weather_forecast, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.wind.setText(weatherDayList.get(i).getWind());
        viewHolder.pressure.setText(weatherDayList.get(i).getPressureMmHg(weatherDayList.get(i).getPressure()));
        viewHolder.humidity.setText(weatherDayList.get(i).getHumidity());
        IconsConverter converter = new IconsConverter();
        converter.setIcon(weatherDayList.get(i).getIconUrl(), viewHolder.weatherIcon);
        //Glide.with(viewHolder.itemView.getContext()).load(weatherDayList.get(i).getIconUrl())
    }


    @Override
    public int getItemCount() {
        return weatherDayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView wind, pressure, humidity;
        ImageView weatherIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            wind = itemView.findViewById(R.id.wind);
            pressure = itemView.findViewById(R.id.pressure);
            humidity = itemView.findViewById(R.id.humidity);
            weatherIcon = itemView.findViewById(R.id.imageView);
        }
    }
}
