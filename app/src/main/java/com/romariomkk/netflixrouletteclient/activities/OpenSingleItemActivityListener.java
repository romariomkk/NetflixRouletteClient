package com.romariomkk.netflixrouletteclient.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.AdapterView;

import com.romariomkk.netflixrouletteclient.Model.MovieModel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by romariomkk on 30.10.2016.
 */
public class OpenSingleItemActivityListener implements AdapterView.OnItemClickListener{
    Activity parentActivity;
    ArrayList<MovieModel> movieList;
    View inflaterView;
    public OpenSingleItemActivityListener(Activity parentActivity, View inflaterView, ArrayList<MovieModel> movieList){
        this.parentActivity = parentActivity;
        this.inflaterView = inflaterView;
        this.movieList = movieList;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        MovieModel movie = movieList.get(position);

        Intent singleMovieIntent = new Intent(inflaterView.getContext(), SingleItemActivity.class);
        singleMovieIntent.putExtra("movie", movie);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap poster = movie.getPoster();
        if (poster != null) {
            poster.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteBmp = stream.toByteArray();

            singleMovieIntent.putExtra("image", byteBmp);
        }
        parentActivity.startActivity(singleMovieIntent);
    }
}
