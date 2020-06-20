package com.example.mobify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import pl.droidsonroids.gif.GifImageView;

public class SlideAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;

    public SlideAdapter(Context context) {
        this.context = context;
    }

    //Arrays for content

    public int[] images=
            {
                  R.drawable.logo,
                    R.drawable.logo,
                    R.drawable.logo,
                    R.drawable.logo

            };

    public int[] textMain=
            {
                     R.string.app_name,
                    R.string.app_name,
                    R.string.app_name,
                    R.string.app_name
            };

    public int[] textSub=
            {
                    R.string.app_name,
                    R.string.app_name,
                    R.string.app_name,
                    R.string.app_name
            };


    @Override
    public int getCount() {
        return textMain.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view== (RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        GifImageView gifImageView = view.findViewById(R.id.gif_view);
        TextView text_main = view.findViewById(R.id.text_main);
        TextView text_sub = view.findViewById(R.id.text_sub);

        gifImageView.setImageResource(images[position]);
        text_main.setText(textMain[position]);
        text_sub.setText(textSub[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
