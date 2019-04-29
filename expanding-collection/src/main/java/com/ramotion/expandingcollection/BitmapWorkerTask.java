package com.ramotion.expandingcollection;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import androidx.annotation.DrawableRes;

/**
 * Worker for async processing bitmaps through cache {@link BackgroundBitmapCache}
 */
public class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {

    private final Resources mResources;
    private final BackgroundBitmapCache cache;
    private final int mProvidedBitmapResId;

    public BitmapWorkerTask(Resources resources, @DrawableRes int providedBitmapResId) {
        this.mResources = resources;
        this.cache = BackgroundBitmapCache.getInstance();
        this.mProvidedBitmapResId = providedBitmapResId;
    }

    @Override
    protected Bitmap doInBackground(Integer... params) {
        Integer key = params[0];
        Bitmap cachedBitmap = cache.getBitmapFromBgMemCache(key);
        if (cachedBitmap == null) {
            cachedBitmap = BitmapFactory.decodeResource(mResources, mProvidedBitmapResId, new BitmapFactoryOptions());
            cache.addBitmapToBgMemoryCache(mProvidedBitmapResId, cachedBitmap);
        }
        return cachedBitmap;
    }


}
