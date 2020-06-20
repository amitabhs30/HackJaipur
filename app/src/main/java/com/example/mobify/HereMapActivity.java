package com.example.mobify;

import androidx.appcompat.app.AppCompatActivity;
import com.here.sdk.mapviewlite.MapViewLite

import android.os.Bundle;

public class HereMapActivity extends AppCompatActivity {

    private MapViewLite mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_here_map);

        // Get a MapViewLite instance from the layout.
        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
    }
}
