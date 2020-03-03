package com.gmail.mashaduk1996.weather.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmail.mashaduk1996.weather.R;

import java.util.ArrayList;

public class CityRecyclerAdapter extends RecyclerView.Adapter<CityRecyclerAdapter.ViewHolder> {

ArrayList<String> cities;

    public CityRecyclerAdapter(ArrayList<String> cities) {
        this.cities = cities;
    }

    @NonNull
    @Override
    public CityRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cityitem, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CityRecyclerAdapter.ViewHolder viewHolder, int i) {
viewHolder.textView.setText(cities.get(i));
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.cityTV);
        }
    }
}
