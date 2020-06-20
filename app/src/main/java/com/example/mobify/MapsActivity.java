package com.example.mobify;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.mobify.jsonHelper.GetNearbyPlaaces;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    ImageButton hospitalSearch;
    EditText locationSearch;
    double latitude,longitude;
    LocationManager locationManager;
    LocationListener locationListener;
    int ProximityRadius = 1000;
    private GoogleMap mMap;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0,locationListener);
            }
        }
    }}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        locationSearch = findViewById(R.id.location_search);
         hospitalSearch = findViewById(R.id.hospitals_nearby);

    }
        @Override
        public void onMapReady(GoogleMap googleMap){
            mMap = googleMap;
            locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    mMap.clear();
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    LatLng userLocation = new LatLng(latitude, longitude);
                    mMap.addMarker(new MarkerOptions().position(userLocation).title("Your location"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));

                    mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault() );
                    try {
                        List<Address> list =geocoder.getFromLocation(latitude,longitude,1);
                        if (list!= null && list.size()>0){
                            String addresses = "";
                            if (list.get(0).getAdminArea()!= null){
                               addresses += list.get(0).getAdminArea();
                            }
                            if (list.get(0).getLocality()!= null){
                                addresses += list.get(0).getLocality();
                            }
                            if (list.get(0).getThoroughfare()!= null){
                                addresses += list.get(0).getThoroughfare();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            };

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                } else {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            }
            public void search(View view){
            String address = locationSearch.getText().toString();
                MarkerOptions usermarkerOptions = new MarkerOptions();
            List<Address> addressList = null;
            if (!TextUtils.isEmpty(address)){
                Geocoder geocoder = new Geocoder(this);
                try {

                    addressList = geocoder.getFromLocationName(address,6);
                    if (addressList!=null){
                    for (int i=0;i<addressList.size();i++) {
                        Address userAddress = addressList.get(i);
                        LatLng latLng = new LatLng(userAddress.getLatitude(), userAddress.getLongitude());

                        usermarkerOptions.position(latLng);
                        usermarkerOptions.title(address);
                        usermarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                        mMap.addMarker(usermarkerOptions);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
                    }
                        }else {
                        Toast.makeText(this,"Address Not Found:(",Toast.LENGTH_LONG).show();
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(this,"Wirte Some Address:)",Toast.LENGTH_LONG).show();
            }
            }
        public void hospital(View view){
        mMap.clear();
        String hospital = "Hospital";
            String url = getUrl(latitude,longitude,hospital);
        Object transferData[] = new Object[2];
        GetNearbyPlaaces getNearbyPlaaces= new GetNearbyPlaaces();
        transferData[0]=mMap;
        transferData[1]=url;
        getNearbyPlaaces.execute(transferData);
        Toast.makeText(this,"Searching for nearby Hospitals",Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Getting the Hospitals", Toast.LENGTH_SHORT).show();
        }

        private String getUrl(double latitude,double longitude,String hospital){
            StringBuilder googleUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/findplacefromtext/json?");
            googleUrl.append("location=" + latitude + "," + longitude);
            googleUrl.append("&radius=" + ProximityRadius);
            googleUrl.append("&type=" +hospital);
            googleUrl.append("&sensor=true");
            googleUrl.append("&key=" +"AIzaSyArFu1VaJ5pni9R77N2aw7gKWwz3yf6Iug");
            return googleUrl.toString();

        }

    }




