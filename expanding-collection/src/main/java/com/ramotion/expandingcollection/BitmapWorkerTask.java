package com.ramotion.expandingcollection;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.annotation.DrawableRes;

public class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {

    private final Resources mResources;
    private final BackgroundBitmapCache cache;
    private final BitmapDrawable mProvidedBitmap;
    @DrawableRes
    private final int mProvidedBitmapResId;

    public BitmapWorkerTask(Resources resources, BitmapDrawable providedBitmap, @DrawableRes int providedBitmapResId) {
        this.mResources = resources;
        this.cache = BackgroundBitmapCache.getInstance();
        this.mProvidedBitmap = providedBitmap;
        this.mProvidedBitmapResId = providedBitmapResId;
    }

    @Override
    protected Bitmap doInBackground(Integer... params) {
        Integer key = params[0];

        if (mProvidedBitmap != null && !mProvidedBitmap.getBitmap().isRecycled()) {
            cache.addBitmapToBgMemoryCache(key, mProvidedBitmap.getBitmap());
            return mProvidedBitmap.getBitmap();
        } else {
            Bitmap cachedBitmap = cache.getBitmapFromBgMemCache(key);
            if (cachedBitmap == null) {
                cachedBitmap = BitmapFactory.decodeResource(mResources, mProvidedBitmapResId, new BitmapFactoryOptions());
                cache.addBitmapToBgMemoryCache(key, cachedBitmap);
            }
            return cachedBitmap;
        }
    }


}
