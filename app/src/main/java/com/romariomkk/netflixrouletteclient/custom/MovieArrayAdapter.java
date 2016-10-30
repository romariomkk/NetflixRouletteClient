package com.romariomkk.netflixrouletteclient.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.romariomkk.netflixrouletteclient.Model.MovieModel;
import com.romariomkk.netflixrouletteclient.R;

import java.util.List;

/**
 * Created by romariomkk on 24.10.2016.
 */
public class MovieArrayAdapter extends ArrayAdapter<MovieModel> {
    Context context;
    List<MovieModel> objects;

    public MovieArrayAdapter(Context context, int resource, List<MovieModel> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.single_row_layout, parent, false);

        ImageView image = (ImageView) row.findViewById(R.id.movie_image);

        TextView ratingInfo = (TextView) row.findViewById(R.id.rating_info);
        TextView releaseYear = (TextView) row.findViewById(R.id.release_year_info);
        TextView title = (TextView) row.findViewById(R.id.title_info);
        TextView director = (TextView) row.findViewById(R.id.director_info);
        TextView category = (TextView) row.findViewById(R.id.category_info);

        MovieModel curr = getItem(position);

        image.setImageBitmap(curr.getPoster());
        ratingInfo.setText(context.getString(R.string.rating, curr.getRating()));
        releaseYear.setText(context.getString(R.string.release_year, curr.getReleaseYear()));
        title.setText(context.getString(R.string.title, curr.getTitle()));
        director.setText(context.getString(R.string.director, curr.getDirector()));
        category.setText(context.getString(R.string.category, curr.getCategory()));

        return row;
    }

    @Override
    public MovieModel getItem(int position) {
        return objects.get(position);
    }
}
