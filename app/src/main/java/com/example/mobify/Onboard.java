package com.example.mobify;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class Onboard extends AppCompatActivity {

    LinearLayout dotLayout;
    ViewPager view_onboard;
    SlideAdapter slideAdapter;
    private TextView[] mdots;
    Button back, next;
    int currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard);

        dotLayout = findViewById(R.id.dotLayout);
        view_onboard = findViewById(R.id.ViewPagerOnboard);

        back = findViewById(R.id.btn_back);
        next = findViewById(R.id.btn_next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view_onboard.setCurrentItem(currentPage+1);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view_onboard.setCurrentItem(currentPage-1);
            }
        });

        slideAdapter = new SlideAdapter(this);
        view_onboard.setAdapter(slideAdapter);

        view_onboard.addOnPageChangeListener(changeListener);

    }

    public void DotsIndicator(int pos)
    {
        mdots = new TextView[4];
        for(int i =0;i<mdots.length;i++)
        {
            mdots[i] = new TextView(this);
            mdots[i].setText(Html.fromHtml("&#8226;"));
            mdots[i].setTextSize(35);
            mdots[i].setTextColor(getResources().getColor(R.color.white));
            dotLayout.addView(mdots[i]);
        }


        if(mdots.length>0)
        {
            mdots[pos].setTextColor(getResources().getColor(R.color.white));
        }
    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            DotsIndicator(position);
            currentPage = position;

            if(position == 0) {
                next.setEnabled(true);
                back.setEnabled(false);
                back.setVisibility(View.INVISIBLE);
            }
                else if(position == mdots.length-1)
            {
                back.setEnabled(true);
                next.setEnabled(false);
                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.INVISIBLE);
                next.setText(getString(R.string.finish));

                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent i = new Intent(Onboard.this, LoginActivity.class);
                        startActivity(i);
                    }
                });
            }
                else
            {
                back.setEnabled(true);
                next.setEnabled(true);
                back.setVisibility(View.VISIBLE);
                next.setVisibility(View.VISIBLE);

            }
            }


        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
