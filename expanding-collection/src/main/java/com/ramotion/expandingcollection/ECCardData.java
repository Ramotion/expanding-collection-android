package com.ramotion.expandingcollection;

import android.support.annotation.DrawableRes;

import java.util.List;

/**
 * Implement this interface to provide data to pager view and content list inside pager card
 *
 * @param <T> Type of items in card content list
 */
public interface ECCardData<T> {

    @DrawableRes
    Integer getMainBackgroundResource();

    @DrawableRes
    Integer getHeadBackgroundResource();

    List<T> getListItems();
}
