package com.romariomkk.netflixrouletteclient.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.romariomkk.netflixrouletteclient.model.MovieModel;
import com.romariomkk.netflixrouletteclient.R;
import com.romariomkk.netflixrouletteclient.caching.ImageCache;

import java.util.List;

/**
 * Created by romariomkk on 24.10.2016.
 */
public class MovieArrayAdapter extends ArrayAdapter<MovieModel> {
    Context context;
    List<MovieModel> objects;

    ImageCache imgCache = ImageCache.getInstance();

    class ViewHolder{
        ImageView image;
        TextView ratingInfo;
        TextView releaseYear;
        TextView title;
        TextView director;
        TextView category;
    }

    public MovieArrayAdapter(Context context, int resource, List<MovieModel> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        imgCache.initCache();

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.single_row_layout, parent, false);

            ViewHolder holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.movie_image);

            holder.ratingInfo = (TextView) convertView.findViewById(R.id.rating_info);
            holder.releaseYear = (TextView) convertView.findViewById(R.id.release_year_info);
            holder.title = (TextView) convertView.findViewById(R.id.title_info);
            holder.director = (TextView) convertView.findViewById(R.id.director_info);
            holder.category = (TextView) convertView.findViewById(R.id.category_info);

            convertView.setTag(holder);
        }

        MovieModel curr = getItem(position);

        ViewHolder holder = (ViewHolder) convertView.getTag();

        Bitmap bmp = imgCache.getImageFromWarehouse(curr.getImgUrl());
        if (bmp != null) {
            holder.image.setImageBitmap(bmp);
            Log.i("UIok", "Image from cache initialized");
        } else {
            holder.image.setImageBitmap(curr.getPoster());
            //new DownloadImageTask(this, 300, 300).execute(curr.getImgUrl());
        }
        holder.ratingInfo.setText(context.getString(R.string.rating, curr.getRating()));
        holder.releaseYear.setText(context.getString(R.string.release_year, curr.getReleaseYear()));
        holder.title.setText(context.getString(R.string.title, curr.getTitle()));
        holder.director.setText(context.getString(R.string.director, curr.getDirector()));
        holder.category.setText(context.getString(R.string.category, curr.getCategory()));

        return convertView;
    }

    @Override
    public MovieModel getItem(int position) {
        return objects.get(position);
    }
}
