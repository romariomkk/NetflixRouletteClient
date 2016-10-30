package com.romariomkk.netflixrouletteclient.caching;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.romariomkk.netflixrouletteclient.custom.MovieArrayAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by romariomkk on 24.10.2016.
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    private int inSampleSize = 0;
    private String imageUrl;
    private MovieArrayAdapter adapter;
    private ImageCache cache;
    private int desiredWidth, desiredHeight;
    private Bitmap image = null;
    private ImageView imageView;

    public DownloadImageTask(MovieArrayAdapter adapter, int desiredWidth, int desiredHeight){
        this.adapter = adapter;
        this.desiredWidth = desiredWidth;
        this.desiredHeight = desiredHeight;
        this.cache = ImageCache.getInstance();
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        imageUrl = strings[0];
        return getImage(imageUrl);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        if (bitmap != null){
            cache.addImageToWarehouse(imageUrl, bitmap);
            if (imageView != null) {
                imageView.setImageBitmap(bitmap);
            }
            if (adapter != null){
                adapter.notifyDataSetChanged();
            }
        }
    }

    private Bitmap getImage(String imageUrl) {
        if (cache.getImageFromWarehouse(imageUrl) == null){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inSampleSize = inSampleSize;

            try{
                URL url = new URL(imageUrl);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                InputStream stream = conn.getInputStream();
                image = BitmapFactory.decodeStream(stream, null, options);

                int imageWidth = options.outWidth;
                int imageHeight = options.outHeight;
                if(imageHeight > desiredHeight || imageWidth > desiredWidth){
                    System.out.println("ImgWidth: " + imageWidth + " ImgHeight: " + imageHeight);
                    inSampleSize += 2;
                    getImage(imageUrl);
                }else{
                    options.inJustDecodeBounds = false;
                    conn = (HttpURLConnection) url.openConnection();
                    stream = conn.getInputStream();
                    image = BitmapFactory.decodeStream(stream, null, options);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e("INFOerr", "URL is formed incorrectly");
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("INFOerr", "Internet connection");
            }
        }
        return image;
    }
}
