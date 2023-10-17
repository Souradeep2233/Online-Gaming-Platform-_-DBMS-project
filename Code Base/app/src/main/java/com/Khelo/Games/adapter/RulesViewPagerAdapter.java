package com.example.quizapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.quizapp.R;

import pl.droidsonroids.gif.GifImageView;

public class CustomViewPagerAdapter extends PagerAdapter {

    private final Context appContext;

    public CustomViewPagerAdapter(Context context) {
        this.appContext = context;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        LayoutInflater inflater = (LayoutInflater) appContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slide_custom, container, false);

        GifImageView gifImage = view.findViewById(R.id.customGifImage);
        TextView description = view.findViewById(R.id.customDescription);

        switch (position) {
            case 0:
                gifImage.setImageResource(R.drawable.custom_image_1);
                description.setText(R.string.custom_description_1);
                break;
            case 1:
                gifImage.setImageResource(R.drawable.custom_image_2);
                description.setText(R.string.custom_description_2);
                break;
            case 2:
                gifImage.setImageResource(R.drawable.custom_image_3);
                description.setText(R.string.custom_description_3);
                break;
            case 3:
                gifImage.setImageResource(R.drawable.custom_image_4);
                description.setText(R.string.custom_description_4);
                break;
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
