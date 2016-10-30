package com.romariomkk.netflixrouletteclient.frags;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.romariomkk.netflixrouletteclient.Model.MovieModel;
import com.romariomkk.netflixrouletteclient.R;
import com.romariomkk.netflixrouletteclient.activities.OpenSingleItemActivityListener;
import com.romariomkk.netflixrouletteclient.activities.SingleItemActivity;
import com.romariomkk.netflixrouletteclient.custom.MovieArrayAdapter;
import com.romariomkk.netflixrouletteclient.dbhelp.AsyncDBExtractor;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class SavedMoviesFragment extends BasicFragment implements BasicFragment.OnFragmentInteractionListener {

    ListView savedMovieListView;
    View inflaterView;
    ArrayList<MovieModel> movieList = new ArrayList<>();

    public SavedMoviesFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflaterView = inflater.inflate(R.layout.fragment_saved_movies, container, false);
        savedMovieListView = (ListView) inflaterView.findViewById(R.id.saved_movies_list_view);

        try {
            AsyncDBExtractor dbExtractor = new AsyncDBExtractor(this.getActivity());
            movieList = dbExtractor.execute().get();

            if (movieList != null) {
                setMovieArrAdapter(movieList);
                savedMovieListView.setOnItemClickListener(
                        new OpenSingleItemActivityListener(this.getActivity(), inflaterView, movieList));
            } else {
                Log.i("dbOK","Null MovieList");
                Toast.makeText(SavedMoviesFragment.this.getContext(),
                        "Error while building Movie List", Toast.LENGTH_LONG).show();
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            Log.e("ERR", "Process interrupted. Can't show Saved Movies.Try again later");
            Toast.makeText(SavedMoviesFragment.this.getContext(), "Process interrupted.\n" +
                    "Can't show Saved Movies.Try again later", Toast.LENGTH_LONG).show();
        }

        return inflaterView;
    }

    private void setMovieArrAdapter(ArrayList<MovieModel> movieList) {
        MovieArrayAdapter adapter = new MovieArrayAdapter(inflaterView.getContext(),
                R.layout.single_row_layout, movieList);
        savedMovieListView.setAdapter(adapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}
