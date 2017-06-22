package com.example.hi.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hi on 06-05-2017.
 */

public final class Utils {
    public static  final String LOG_TAG = Utils.class.getSimpleName();
    private Utils()
    {
    }
    public static List<News> fetchNews(String reqUrl)
    {
        URL url= createURL(reqUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG, "Problm in req",e);
        }
        List<News> newses = extractFeatureFromJson(jsonResponse);
        return newses;
    }
    private static URL createURL(String reqUrl)
    {
        URL url = null;
        try {
            url = new URL(reqUrl);
        }
        catch (MalformedURLException e)
        {
        }
        return url;
    }
    private static String makeHttpRequest(URL url) throws IOException
    {
        String jsonResponse = null;
        if (url==null)
        {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(1500);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code" + urlConnection.getResponseCode());
            }
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG,"Problem getting data",e);
        }
        finally {
            if(urlConnection!= null)
            {
                urlConnection.disconnect();
            }
            if (inputStream!=null)
            {
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    private static String readFromStream(InputStream inputStream) throws IOException
    {
        StringBuilder value = new StringBuilder();
        if (inputStream!=null)
        {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line!=null)
            {
                value.append(line);
                line = reader.readLine();
            }
        }
        return value.toString();
    }
    private static List<News> extractFeatureFromJson(String NewsJSON)
    {
        if(TextUtils.isEmpty(NewsJSON))
        {
            return null;
        }

        List<News> newses = new ArrayList<>();
        try {
            JSONObject baseJsonResponse = new JSONObject(NewsJSON);
            JSONObject jsonObject = baseJsonResponse.getJSONObject("response");
            JSONArray NewsArray1 = jsonObject.getJSONArray("results");
            for (int i = 0; i < NewsArray1.length(); i++) {
                JSONObject getnews = NewsArray1.optJSONObject(i);
                String properties = getnews.optString("sectionName");
                String title = getnews.optString("webTitle");
                String news = getnews.optString("webUrl");
                newses.add(new News(properties,title,news));
            }
        }
        catch (JSONException e)
        {
            Log.e("utils","Problem parsing the result" , e);
        }
        return newses;
    }
}


