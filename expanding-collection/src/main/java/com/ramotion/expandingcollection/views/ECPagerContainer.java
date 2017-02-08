package com.ramotion.expandingcollection.views;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Pager container for simulate needed pager behavior - show parts of nearby pager elements
 */
public class ECPagerContainer extends FrameLayout implements ViewPager.OnPageChangeListener {

    private ECPager pager;
    private boolean needsRedraw = false;
    private Point center = new Point();
    private Point initialTouch = new Point();

    public ECPagerContainer(Context context) {
        super(context);
        init();
    }

    public ECPagerContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ECPagerContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        // Disable clipping of children so non-selected pages are visible
        setClipChildren(false);
        setClipToPadding(false);

        // Child clipping doesn't work with hardware acceleration in Android 3.x/4.x
        if (Build.VERSION.SDK_INT < 21)
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onFinishInflate() {
        try {
            pager = (ECPager) getChildAt(0);
            pager.addOnPageChangeListener(this);
        } catch (Exception e) {
            throw new IllegalStateException("The root child of PagerContainer must be a ViewPager");
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        center.x = w / 2;
        center.y = h / 2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //We capture any touches not already handled by the ViewPager
        // to implement scrolling from a touch outside the pager bounds.
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialTouch.x = (int) ev.getX();
                initialTouch.y = (int) ev.getY();
            default:
                ev.offsetLocation(center.x - initialTouch.x, center.y - initialTouch.y);
                break;
        }
        return pager.dispatchTouchEvent(ev);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //Force the container to redraw on scrolling.
        //Without this the outer pages render initially and then stay static
        if (needsRedraw) invalidate();
    }

    @Override
    public void onPageSelected(int position) {
//        Toast.makeText(this.getContext(), "Selected " + position + " element", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        needsRedraw = (state != ViewPager.SCROLL_STATE_IDLE);
    }

}
