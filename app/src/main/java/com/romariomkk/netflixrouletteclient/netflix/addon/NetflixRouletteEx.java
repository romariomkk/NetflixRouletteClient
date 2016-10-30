package com.romariomkk.netflixrouletteclient.netflix.addon;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.romariomkk.netflixrouletteclient.Model.MovieModel;
import com.romariomkk.netflixrouletteclient.Model.Properties;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by romariomkk on 21.10.2016.
 */
public class NetflixRouletteEx {
    private static final String API_URL = "http://netflixroulette.net/api/api.php?";

    Activity activity;

    public NetflixRouletteEx(Activity activity) {
        this.activity = activity;
    }


    /**
     * @param field      is the only parameter to be inserted for filtering.
     *                   Preferable to be chosen from enum Properties.
     * @param searchValue the value for the corresponding role.
     * @return ArrayList of MovieModels containing the result of JSON parsing.
     * @see Properties
     */
    public ArrayList<MovieModel> getMoviesBy(String field, String searchValue)  {
        ArrayList<MovieModel> movies = new ArrayList<>();
        JSONArray jsonArray = null;
        JSONObject jsonObj = null;

        searchValue = searchValue.replace(" ", "%20");
        try {
            String jsonStr = RouletteFunctionsEx.readJsonFromUrl(API_URL + field + "=" + searchValue);
            if (jsonStr == null) {
                return null;
            }

            Log.i("INFOok", jsonStr);

            switch (field) {
                case "title":
                    jsonObj = new JSONObject(jsonStr);
                    break;
                case "director":
                    jsonArray = new JSONArray(jsonStr);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("INFOerr", "Info about films was not extracted");
            activity.runOnUiThread(() ->
                    Toast.makeText(activity.getBaseContext(), "Info about films was not extracted", Toast.LENGTH_SHORT).show());
        }

        if (jsonArray == null && jsonObj == null) { //Objects.isNull() preferred, but here min API level is 21
            return null;
        } else {
            try {
                movies = (jsonArray != null) ? getMoviesFromJSON(jsonArray) : getMoviesFromJSON(jsonObj);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("INFOerr", "The movies only couldn't be extracted");
            }
        }
        return movies;
    }

    private ArrayList<MovieModel> getMoviesFromJSON(JSONObject currObj) throws JSONException {
        ArrayList<MovieModel> movies = new ArrayList<>();
        movies.add(createMovie(currObj));
        return movies;
    }

    private ArrayList<MovieModel> getMoviesFromJSON(JSONArray jsonArray) throws JSONException {
        ArrayList<MovieModel> movies = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject currObj = jsonArray.getJSONObject(i);
            movies.add(createMovie(currObj));
        }
        return movies;
    }

    private MovieModel createMovie(JSONObject currObj) throws JSONException {
        URL url;
        Bitmap image = null;
        try {
            url = new URL(currObj.get("poster").toString());
            image = BitmapFactory.decodeStream(url.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new MovieModel(
                currObj.get("show_title").toString(),
                currObj.get("director").toString(),
                currObj.get("category").toString(),
                currObj.get("rating").toString(),
                currObj.get("release_year").toString(),
                image,
                currObj.get("summary").toString());
    }

}