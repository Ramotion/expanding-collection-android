package com.ramotion.expandingcollection;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.media.MediaMetadataCompat;

import java.util.Collection;

public interface ECCardData<T> {


    @DrawableRes
    Integer getMainBgImageDrawableResource();

    BitmapDrawable getMainBgImageDrawable();

    @DrawableRes
    Integer getHeadBgImageDrawableResource();

    BitmapDrawable getHeadBgImageDrawable();

    Collection<T> getComments();
}
