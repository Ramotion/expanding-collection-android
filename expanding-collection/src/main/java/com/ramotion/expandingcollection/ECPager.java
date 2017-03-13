package com.ramotion.expandingcollection;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

public class ECPager extends ViewPager {
    public static final String TAG = "ecview";

    private int currentPosition = 0;

    private boolean pagingEnabled = true;

    public ECPager(Context context) {
        super(context);
        init();
    }

    public ECPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.setOffscreenPageLimit(3);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.pagingEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.pagingEnabled && super.onInterceptTouchEvent(event);
    }

    public void updateLayoutDimensions(int cardWidth, int cardHeight) {
        FrameLayout.LayoutParams pagerViewLayoutParams = (FrameLayout.LayoutParams) this.getLayoutParams();
        pagerViewLayoutParams.height = cardHeight;
        pagerViewLayoutParams.width = cardWidth;
    }

    public ECCardData getDataFromAdapterDataset(int position) {
        return ((ECPagerAdapter) this.getAdapter()).getDataset().get(position);
    }

    public void enablePaging() {
        this.pagingEnabled = true;
    }

    public void disablePaging() {
        this.pagingEnabled = false;
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
    }

    protected void animateWidth(int targetWidth, int duration, int startDelay, AnimationListener onAnimationEnd) {
        ValueAnimator pagerWidthAnimation = new ValueAnimator();
        pagerWidthAnimation.setInterpolator(new AccelerateInterpolator());
        pagerWidthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewGroup.LayoutParams pagerLayoutParams = getLayoutParams();
                pagerLayoutParams.width = (int) animation.getAnimatedValue();
                setLayoutParams(pagerLayoutParams);
            }
        });

        pagerWidthAnimation.setIntValues(getWidth(), targetWidth);

        pagerWidthAnimation.setStartDelay(startDelay);
        pagerWidthAnimation.setDuration(duration);
        if (onAnimationEnd != null)
            pagerWidthAnimation.addListener(onAnimationEnd);
        pagerWidthAnimation.start();
    }

    protected void animateHeight(int targetHeight, int duration, int startDelay, AnimationListener onAnimationEnd) {
        ValueAnimator pagerHeightAnimation = new ValueAnimator();
        pagerHeightAnimation.setInterpolator(new DecelerateInterpolator());
        pagerHeightAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewGroup.LayoutParams pagerLayoutParams = getLayoutParams();
                pagerLayoutParams.height = (int) animation.getAnimatedValue();
                setLayoutParams(pagerLayoutParams);
            }
        });

        pagerHeightAnimation.setIntValues(getHeight(), targetHeight);

        pagerHeightAnimation.setDuration(duration);
        pagerHeightAnimation.setStartDelay(startDelay);
        if (onAnimationEnd != null)
            pagerHeightAnimation.addListener(onAnimationEnd);

        pagerHeightAnimation.start();
    }

    @Override
    protected void onPageScrolled(int position, float offset, int offsetPixels) {
        super.onPageScrolled(position, offset, offsetPixels);
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public boolean expand() {
        ECPagerAdapter adapter = (ECPagerAdapter) getAdapter();
        return adapter.getActiveCard().expand();
    }

    public boolean collapse() {
        ECPagerAdapter adapter = (ECPagerAdapter) getAdapter();
        return adapter.getActiveCard().collapse();
    }

    public boolean toggle() {
        ECPagerAdapter adapter = (ECPagerAdapter) getAdapter();
        return adapter.getActiveCard().toggle();
    }
}
