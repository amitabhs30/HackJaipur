package com.example.mobify.jsonHelper;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadUrl {
    public String ReadTheUrl(String placeUrl){
        String result ="";
        URL url;
        HttpURLConnection connection = null;
        try {
            url = new URL(placeUrl);
            connection= (HttpURLConnection) url.openConnection();
            InputStream in = connection.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);
            int data = reader.read();
            while (data!=-1){
                char current = (char) data;
                result += current;
                data = reader.read();
            }return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }}

