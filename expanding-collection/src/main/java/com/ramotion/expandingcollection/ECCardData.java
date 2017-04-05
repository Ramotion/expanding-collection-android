package com.ramotion.expandingcollection;

import android.support.annotation.DrawableRes;

import java.util.List;

public interface ECCardData<T> {

    @DrawableRes
    Integer getMainBackgroundResource();

    @DrawableRes
    Integer getHeadBackgroundResource();

    List<T> getListItems();
}
