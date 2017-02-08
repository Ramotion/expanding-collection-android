package com.ramotion.expandingcollection.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class ECPagerCardContainer extends FrameLayout {

    private ECPagerCard pagerCard;

    public ECPagerCardContainer(Context context) {
        super(context);
    }

    public ECPagerCardContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ECPagerCardContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        try {
            pagerCard = (ECPagerCard) getChildAt(0);
        } catch (Exception e) {
            throw new IllegalStateException("Invalid children element in ECPagerCardContainer.");
        }
    }

    public ECPagerCard getPagerCard() {
        return pagerCard;
    }

}
