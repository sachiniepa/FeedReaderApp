package com.example.feedreader.Common;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class HTTPDataHandler {
    static String stream = null;

    public HTTPDataHandler() {
    }

    public String GetHTTPData(String urlString){
        try{
            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null){
                    sb.append(line);

                }
                stream = sb.toString();
                httpURLConnection.disconnect();
            }

        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        return stream;
    }
}
