package com.ramotion.expandingcollection.utils;

import android.support.v4.view.ViewPager;
import android.view.View;

public class AlphaScalePageTransformer implements ViewPager.PageTransformer {

    private static final float INACTIVE_SCALE = 0.8f;
    private static final float INACTIVE_ALPHA = 0.5f;

    public void transformPage(View view, float position) {
        if (position < -1) {
            view.setAlpha(INACTIVE_ALPHA);
            view.setScaleX(INACTIVE_SCALE);
            view.setScaleY(INACTIVE_SCALE);
        } else if (position <= 1) {
            float scaleFactor = INACTIVE_SCALE + (1 - INACTIVE_SCALE) * (1 - Math.abs(position));
            float alphaFactor = INACTIVE_ALPHA + (1 - INACTIVE_ALPHA) * (1 - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
            view.setAlpha(alphaFactor);

//            float scaleFactor2 = Math.max(INACTIVE_SCALE, 1 - Math.abs(position));
//            float vertMargin = view.getHeight() * (1 - scaleFactor) / 2;
//            float horzMargin = view.getWidth() * (1 - scaleFactor) / 2;
//            if (position < 0) {
//                view.setTranslationX(horzMargin - vertMargin / 2);
//            } else {
//                view.setTranslationX(-horzMargin + vertMargin / 2);
//            }

        } else {
            view.setAlpha(INACTIVE_ALPHA);
            view.setScaleX(INACTIVE_SCALE);
            view.setScaleY(INACTIVE_SCALE);
        }
    }
}
