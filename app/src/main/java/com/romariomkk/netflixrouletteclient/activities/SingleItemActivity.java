package com.romariomkk.netflixrouletteclient.activities;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.romariomkk.netflixrouletteclient.Model.MovieModel;
import com.romariomkk.netflixrouletteclient.R;
import com.romariomkk.netflixrouletteclient.dbhelp.DBHandler;

import java.util.Objects;

public class SingleItemActivity extends AppCompatActivity {

    DBHandler dbHandler = new DBHandler(this);
    MovieModel currMovie;
    ContentValues values;
    NavigationViewActivity navViewActivity = new NavigationViewActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item);

        Intent intent = getIntent();
        currMovie = (MovieModel) intent.getSerializableExtra("movie");
        byte[] bmpArr = intent.getByteArrayExtra("image");

        fillViewsContent(currMovie, bmpArr);
    }

    private void setTitle(String mainTitle) {
        try {
            getSupportActionBar().setTitle(Objects.requireNonNull(mainTitle));
        } catch (NullPointerException exc) {
            Log.e("UIerr", "Action Bar title not instantiated");
        }
    }

    private void fillViewsContent(MovieModel acqMovie, byte[] bmpArr) {
        TextView title = (TextView) findViewById(R.id.single_movie_title);
        TextView releaseYear = (TextView) findViewById(R.id.single_movie_release_year);
        TextView director = (TextView) findViewById(R.id.single_movie_director);
        TextView ratingInfo = (TextView) findViewById(R.id.single_movie_rating);
        TextView summary = (TextView) findViewById(R.id.single_movie_summary);
        ImageView image = (ImageView) findViewById(R.id.single_movie_image);

        Context context = getBaseContext();

        title.setText(context.getString(R.string.title, acqMovie.getTitle()));
        releaseYear.setText(context.getString(R.string.release_year, acqMovie.getReleaseYear()));
        director.setText(context.getString(R.string.director, acqMovie.getDirector()));
        ratingInfo.setText(context.getString(R.string.rating, acqMovie.getRating()));
        summary.setText(context.getString(R.string.summary, acqMovie.getSummary()));
        if (bmpArr != null) {
            image.setImageBitmap(BitmapFactory.decodeByteArray(bmpArr, 0, bmpArr.length));
        }

        addContentValues(acqMovie, bmpArr);

        setTitle(acqMovie.getTitle());
    }

    private void addContentValues(MovieModel acqMovie, byte[] bmpArr) {
        values = new ContentValues();

        values.put("title", acqMovie.getTitle());
        values.put("year", acqMovie.getReleaseYear());
        values.put("director", acqMovie.getDirector());
        values.put("rating", acqMovie.getRating());
        values.put("summary", acqMovie.getSummary());
        if (bmpArr != null) {
            values.put("image", bmpArr);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //navViewActivity.refreshForeground();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_single_movie_menu, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_movie_item:
                try (SQLiteDatabase db = dbHandler.getWritableDatabase()) {
                    long rowID = db.insertOrThrow("MovieTable", null, values);

                    Log.i("dbOK", "Row inserted ID " + rowID);
                    Toast.makeText(SingleItemActivity.this, "Successfully saved", Toast.LENGTH_LONG).show();
                } catch (SQLiteConstraintException exc) {
                    Log.e("dbERR", "This movie has already been saved");
                    Toast.makeText(SingleItemActivity.this, "This movie has already been saved", Toast.LENGTH_LONG).show();
                    return false;
                }
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
