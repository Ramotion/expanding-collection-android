package com.ramotion.expandingcollection;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

public class ECPagerCardBody extends FrameLayout {
    public ECPagerCardBody(Context context) {
        super(context);
    }

    public ECPagerCardBody(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ECPagerCardBody(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private boolean touchDisabled = true;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return touchDisabled;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return touchDisabled;
    }

    public void disableTouch() {
        touchDisabled = true;
    }

    public void enableTouch() {
        touchDisabled = false;
    }


}
