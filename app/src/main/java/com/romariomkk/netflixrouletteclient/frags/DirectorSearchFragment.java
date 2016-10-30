package com.romariomkk.netflixrouletteclient.frags;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.romariomkk.netflixrouletteclient.Model.MovieModel;
import com.romariomkk.netflixrouletteclient.Model.Properties;
import com.romariomkk.netflixrouletteclient.R;
import com.romariomkk.netflixrouletteclient.activities.OpenSingleItemActivityListener;
import com.romariomkk.netflixrouletteclient.custom.MovieArrayAdapter;
import com.romariomkk.netflixrouletteclient.netflix.addon.AsyncNetflixExtractor;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class DirectorSearchFragment extends BasicFragment implements BasicFragment.OnFragmentInteractionListener {

    public DirectorSearchFragment() {
    }

    View inflaterView;
    Button buttonSearch;

    TextView directorText;
    ListView moviesListView;
    ArrayList<MovieModel> movieList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflaterView = inflater.inflate(R.layout.fragment_director_search, container, false);

        directorText = (TextView) inflaterView.findViewById(R.id.director_text);

        moviesListView = (ListView) inflaterView.findViewById(R.id.director_movie_list);

        buttonSearch = (Button) inflaterView.findViewById(R.id.start_director_search);
        buttonSearch.setOnClickListener(view -> {

            hideKeyboard();
            String input = directorText.getText().toString().trim();
            AsyncNetflixExtractor task = new AsyncNetflixExtractor(getActivity());

            if (!isOnline()) {
                Toast.makeText(inflaterView.getContext(),
                        "Cannot load data. Please check your network connection",
                        Toast.LENGTH_LONG).show();
            } else {
                try {
                    task.execute(Properties.DIRECTOR.get(), input);

                    ArrayList<MovieModel> result = task.get();
                    if (result != null) {
                        movieList = new ArrayList<>(result);
                        moviesListView.setOnItemClickListener(
                                new OpenSingleItemActivityListener(this.getActivity(), inflaterView, movieList));
                    } else {
                        Toast.makeText(inflaterView.getContext(), "No film of this director", Toast.LENGTH_LONG).show();
                        return;
                    }
                    Toast.makeText(inflaterView.getContext(), "Movie FOUND", Toast.LENGTH_LONG).show();

                    setMovieArrAdapter(movieList);

                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        return inflaterView;
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
    }

    private void setMovieArrAdapter(ArrayList<MovieModel> movieList) {
        MovieArrayAdapter adapter = new MovieArrayAdapter(inflaterView.getContext(),
                R.layout.single_row_layout, movieList);
        moviesListView.setAdapter(adapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}