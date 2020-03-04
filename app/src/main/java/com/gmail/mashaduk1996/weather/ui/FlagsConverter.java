package com.gmail.mashaduk1996.weather.ui;

import android.widget.ImageView;
import android.widget.Switch;

import com.gmail.mashaduk1996.weather.R;

public class FlagsConverter {


    public void setFlag(ImageView image, String country) {
        switch(country){
            case "by":
                image.setImageResource(R.drawable.by);
                break;
            case "ru":
                image.setImageResource(R.drawable.ru);
                break;
            case "gb":
                image.setImageResource(R.drawable.gb);
                break;
            case "au":
                image.setImageResource(R.drawable.au);
                break;
        }
    }
}
