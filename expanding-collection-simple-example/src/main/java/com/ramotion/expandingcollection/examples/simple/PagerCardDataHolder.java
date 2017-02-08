package com.ramotion.expandingcollection.examples.simple;

import android.graphics.drawable.Drawable;

import com.ramotion.expandingcollection.ECCardData;

public class PagerCardDataHolder implements ECCardData {

    private String headerTitle;
    private Drawable bgImageDrawable;

    public PagerCardDataHolder() {
    }

    public PagerCardDataHolder(String headerTitle, Drawable bgImageDrawable) {
        this.headerTitle = headerTitle;
        this.bgImageDrawable = bgImageDrawable;
    }

    public String getHeadTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public Drawable getBgImageDrawable() {
        return bgImageDrawable;
    }

    public void setBgImageDrawable(Drawable bgImageDrawable) {
        this.bgImageDrawable = bgImageDrawable;
    }

}
