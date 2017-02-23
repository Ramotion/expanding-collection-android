package com.ramotion.expandingcollection.examples.simple;

import android.graphics.drawable.Drawable;

import com.ramotion.expandingcollection.ECCardData;

public class PagerCardDataHolder implements ECCardData {

    private String headTitle;
    private Drawable headBgImageDrawable;

    public PagerCardDataHolder() {
    }

    public PagerCardDataHolder(String headTitle, Drawable bgImageDrawable) {
        this.headTitle = headTitle;
        this.headBgImageDrawable = bgImageDrawable;
    }

    public String getHeadTitle() {
        return headTitle;
    }

    public void setHeadTitle(String headTitle) {
        this.headTitle = headTitle;
    }

    public Drawable getMainBgImageDrawable() {
        return headBgImageDrawable;
    }

    @Override
    public Drawable getHeadBgImageDrawable() {
        return headBgImageDrawable;
    }

    public void setBgImageDrawable(Drawable bgImageDrawable) {
        this.headBgImageDrawable = bgImageDrawable;
    }

}
