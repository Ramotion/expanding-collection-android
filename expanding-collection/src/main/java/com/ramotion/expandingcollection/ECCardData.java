package com.ramotion.expandingcollection;

import android.graphics.drawable.Drawable;

import java.util.Collection;

public interface ECCardData<T> {

    Drawable getMainBgImageDrawable();

    Drawable getHeadBgImageDrawable();

    Collection<T> getComments();
}
