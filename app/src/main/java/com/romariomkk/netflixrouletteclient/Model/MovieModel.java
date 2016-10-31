package com.romariomkk.netflixrouletteclient.Model;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by romariomkk on 21.10.2016.
 */

@SuppressWarnings("serial")
public class MovieModel implements Serializable{

    String title;
    String director;
    String category;
    String rating;
    String releaseYear;
    String imgUrl;
    transient Bitmap poster;
    String summary;

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public String getCategory() {
        return category;
    }

    public String getRating() {
        return rating;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public String getImgUrl() { return imgUrl; }

    public Bitmap getPoster() {
        return poster;
    }

    public String getSummary() { return summary; }

    public MovieModel(String title, String director,
                      String category, String rating,
                      String releaseYear,String imgUrl, Bitmap poster,
                      String summary) {
        this.title = title;
        this.director = director;
        this.category = category;
        this.rating = rating;
        this.releaseYear = releaseYear;
        this.imgUrl = imgUrl;
        this.poster = poster;
        this.summary = summary;
    }


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Movie{" +
                "title='" + title + '\'' +
                ", director='" + director + '\'' +
                ", category='" + category + '\'' +
                ", rating='" + rating + '\'' +
                ", releaseYear='" + releaseYear + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                /*", poster='" + poster + '\'' +*/
                ", summary='" + summary + '\'' +
                '}');
        return str.toString();
    }
}