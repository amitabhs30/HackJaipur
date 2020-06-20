package com.example.mobify.jsonHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParse {
    private HashMap<String,String> getSingleNearbyPlace(JSONObject googlePlacesJson){
        HashMap<String,String> googlePlaceMap= new HashMap<>();
        String nameofPlace = "-NA-";
        String vicinity = "-NA-";
        String latitude ="";
        String longitude = "";
        String reference = "";
        try{
        if (!googlePlacesJson.isNull("name")) {
            nameofPlace = googlePlacesJson.getString("name");
        }
        if (!googlePlacesJson.isNull("vicinity")){
                vicinity = googlePlacesJson.getString("vicinity");
            }
        latitude = googlePlacesJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
        longitude = googlePlacesJson.getJSONObject("geometry").getJSONObject("location").getString("lng");
        reference =  googlePlacesJson.getString("reference");
        googlePlaceMap.put("place_name",nameofPlace);
            googlePlaceMap.put("vicinity",vicinity);
            googlePlaceMap.put("lat",latitude);
            googlePlaceMap.put("lng",longitude);
            googlePlaceMap.put("ref",reference);


        }catch (Exception e){
            e.printStackTrace();
        }
       return googlePlaceMap;
    }
    private List<HashMap<String,String>> getAllNearbyPlaces(JSONArray jsonArray){
        int counter = jsonArray.length();
        List<HashMap<String,String>> NearbypLaces = new ArrayList<>();
        HashMap<String,String> NearbyPlaceMap = null;
        for (int i=0;i<counter;i++){
            try {
                NearbyPlaceMap = getSingleNearbyPlace((JSONObject) jsonArray.get(i));
                NearbypLaces.add(NearbyPlaceMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return NearbypLaces;
    }
    public List<HashMap<String,String>> parse(String jsonData){
        JSONArray jsonArray = null;
        JSONObject jsonObject;
        try {
            jsonObject= new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getAllNearbyPlaces(jsonArray);

    }

}
