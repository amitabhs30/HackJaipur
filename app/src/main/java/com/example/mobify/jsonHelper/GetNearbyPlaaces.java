package com.example.mobify.jsonHelper;

import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;

public class GetNearbyPlaaces extends AsyncTask<Object,String,String> {
    private String googlePlaceData,Url;
    private GoogleMap map;

    @Override
    protected String doInBackground(Object... objects) {
        map = (GoogleMap) objects[0];
        Url = (String) objects[1];
        DownloadUrl downloadUrl = new DownloadUrl();
        googlePlaceData = downloadUrl.ReadTheUrl(Url);


        return googlePlaceData;
    }

    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String,String>> nearbyPlaceList = null;
        DataParse dataParse = new DataParse();
        nearbyPlaceList = dataParse.parse(s);
        DisplayNearbyPlaces(nearbyPlaceList);
    }

    private void DisplayNearbyPlaces(List<HashMap<String,String>> nearbyPlaceList){

        for (int i=0;i<nearbyPlaceList.size();i++){
            MarkerOptions hmarkerOptions = new MarkerOptions();
            HashMap<String,String > googleNearByPlace =nearbyPlaceList.get(i);
            String nameOfPLace = googleNearByPlace.get("place_name");
            String vicinity = googleNearByPlace.get("vicinity");
            double lat = Double.parseDouble(googleNearByPlace.get("lat"));
            double lng = Double.parseDouble(googleNearByPlace.get("lng"));
            LatLng latLng = new LatLng(lat,lng);

            hmarkerOptions.position(latLng);
            hmarkerOptions.title(nameOfPLace+ ":" +vicinity);
            hmarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));

            map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            map.animateCamera(CameraUpdateFactory.zoomTo(10));
        }
    }
}
