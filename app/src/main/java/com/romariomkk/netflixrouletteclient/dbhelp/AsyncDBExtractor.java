package com.romariomkk.netflixrouletteclient.dbhelp;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.romariomkk.netflixrouletteclient.model.MovieModel;

import java.util.ArrayList;

/**
 * Created by romariomkk on 30.10.2016.
 */
public class AsyncDBExtractor extends AsyncTask<String, Context, ArrayList<MovieModel>> {
    DBHandler dbHandler;
    Activity activity;
    ArrayList<MovieModel> movieList = new ArrayList<>();

    public AsyncDBExtractor(Activity activity) {
        this.activity = activity;
        dbHandler = new DBHandler(this.activity.getBaseContext());
    }

    @Override
    protected ArrayList<MovieModel> doInBackground(String... strings) {
        try (SQLiteDatabase db = dbHandler.getReadableDatabase();
             Cursor cursor = db.query("MovieTable", null, null, null, null, null, null)) {

            if (cursor.moveToNext()) {
                do {
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String year = cursor.getString(cursor.getColumnIndex("year"));
                    String director = cursor.getString(cursor.getColumnIndex("director"));
                    String rating = cursor.getString(cursor.getColumnIndex("rating"));
                    String summary = cursor.getString(cursor.getColumnIndex("summary"));

                    Bitmap img = null;
                    byte[] image = cursor.getBlob(cursor.getColumnIndex("image"));
                    if (image != null) {
                        img = BitmapFactory.decodeByteArray(image, 0, image.length);
                    }
                    movieList.add(new MovieModel(title, director, null, rating, year, null, img, summary));
                } while (cursor.moveToNext());
                Log.i("dbOK", "DB Extracting finished");
            }
        }
        return movieList;
    }
}
