package com.romariomkk.netflixrouletteclient.caching;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by romariomkk on 24.10.2016.
 */
public class ImageCache {

    LruCache<String, Bitmap> imageWarehouse;
    private static ImageCache cache;

    public static ImageCache getInstance() {
        if (cache == null) {
            cache = new ImageCache();
        }
        return cache;
    }

    public void initCache() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        System.out.println("CacheSize: " + cacheSize);

        imageWarehouse = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                int bitmapByteCount = value.getRowBytes() * value.getHeight();
                return bitmapByteCount / 1024;
            }
        };
    }

    public void addImageToWarehouse(String key, Bitmap value) {
        if (imageWarehouse != null && imageWarehouse.get(key) == null) {
            imageWarehouse.put(key, value);
        }
    }

    public Bitmap getImageFromWarehouse(String key) {
        return (key != null) ? imageWarehouse.get(key) : null;
    }

    public void removeImageFromWarehouse(String key) {
        imageWarehouse.remove(key);
    }

    public void clearCache() {
        if (imageWarehouse != null) {
            imageWarehouse.evictAll();
        }
    }

}
