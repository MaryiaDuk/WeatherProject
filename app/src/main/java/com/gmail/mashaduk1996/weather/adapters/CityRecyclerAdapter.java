package com.gmail.mashaduk1996.weather.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gmail.mashaduk1996.weather.App;
import com.gmail.mashaduk1996.weather.R;
import com.gmail.mashaduk1996.weather.models.CityModel;
import com.gmail.mashaduk1996.weather.ui.FlagsConverter;

import java.util.ArrayList;
import java.util.List;

public class CityRecyclerAdapter extends RecyclerView.Adapter<CityRecyclerAdapter.ViewHolder> implements Filterable {

    private ArrayList<CityModel> cities;
    private List<CityModel> citiesFull;
    private FlagsConverter converter;

    public CityRecyclerAdapter(ArrayList<CityModel> cities) {
        this.cities = cities;
        citiesFull = new ArrayList<>(cities);
        converter = new FlagsConverter();
    }

    @NonNull
    @Override
    public CityRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cityitem, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CityRecyclerAdapter.ViewHolder viewHolder, int i) {
        final CityModel city = cities.get(i);
        viewHolder.textView.setText(city.getName_rus() + ", " + city.getRegion());
        String str = "Выбран н/п: "+city.getName_rus();
        converter.setFlag(viewHolder.flag, city.getCountry().toLowerCase());
        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(App.context, "Выбран н/п: "+ city.getId(), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    @Override
    public Filter getFilter() {
        return citiesFilter;
    }

    private Filter citiesFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<CityModel> citiesFiltered = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                citiesFiltered.addAll(citiesFull);
            } else {
                String pattern = charSequence.toString().toLowerCase().trim();
                for (CityModel city : citiesFull) {

                    if (city.getName_rus().toLowerCase().contains(pattern)) {
                        citiesFiltered.add(city);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = citiesFiltered;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            cities.clear();
            cities.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView flag;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.cityTV);
            flag = itemView.findViewById(R.id.flag);
        }
    }
}
