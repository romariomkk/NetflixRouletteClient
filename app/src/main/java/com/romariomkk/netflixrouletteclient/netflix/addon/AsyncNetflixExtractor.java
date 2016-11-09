package com.romariomkk.netflixrouletteclient.netflix.addon;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.romariomkk.netflixrouletteclient.model.MovieModel;

import java.util.ArrayList;

/**
 * Class that executes reading of info about films in the background(towards UI thread) thread
 * Created by romariomkk on 22.10.2016.
 */
public class AsyncNetflixExtractor extends AsyncTask<String, Context, ArrayList<MovieModel>> {
    NetflixRouletteEx netflixRoulette;
    Activity activity;

    public AsyncNetflixExtractor(Activity activity) {
        this.activity = activity;
        netflixRoulette = new NetflixRouletteEx(activity);
    }

    @Override
    protected ArrayList<MovieModel> doInBackground(String... params) {
        return netflixRoulette.getMoviesBy(params[0], params[1]);
    }
}
