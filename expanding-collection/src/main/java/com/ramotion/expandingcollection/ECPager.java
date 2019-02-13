package com.ramotion.expandingcollection;

import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * Custom ViewPager used as main card pager, neighborhood elements are visible due to {@link ECPagerView},
 * so this pager element used only inside {@link ECPagerView}. Also pager can change self position and size
 * for animation purposes.
 */
public class ECPager extends ViewPager {
    private int currentPosition;
    private boolean pagingDisabled;

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
//        this.setOverScrollMode(OVER_SCROLL_NEVER);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return !this.pagingDisabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return !this.pagingDisabled && super.onInterceptTouchEvent(event);
    }

    public void updateLayoutDimensions(int cardWidth, int cardHeight) {
        FrameLayout.LayoutParams pagerViewLayoutParams = (FrameLayout.LayoutParams) this.getLayoutParams();
        pagerViewLayoutParams.height = cardHeight;
        pagerViewLayoutParams.width = cardWidth;
    }

    public ECCardData getDataFromAdapterDataset(int position) {
        return ((ECPagerViewAdapter) this.getAdapter()).getDataset().get(position);
    }

    public void enablePaging() {
        this.pagingDisabled = false;
    }

    public void disablePaging() {
        this.pagingDisabled = true;
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
    }

    protected void animateWidth(int targetWidth, int duration, int startDelay, AnimatorListenerAdapter onAnimationEnd) {
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

    protected void animateHeight(int targetHeight, int duration, int startDelay, AnimatorListenerAdapter onAnimationEnd) {
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

    /**
     * Start expand animation for currently active card.
     *
     * @return true if animation started
     */
    public boolean expand() {
        ECPagerViewAdapter adapter = (ECPagerViewAdapter) getAdapter();
        return adapter.getActiveCard().expand();
    }

    /**
     * Start collapse animation for currently active card.
     *
     * @return true if animation started
     */
    public boolean collapse() {
        ECPagerViewAdapter adapter = (ECPagerViewAdapter) getAdapter();
        return adapter.getActiveCard().collapse();
    }

    /**
     * Toggle state of currently active card - collapse if card is expanded and otherwise
     *
     * @return true if animation started
     */
    public boolean toggle() {
        ECPagerViewAdapter adapter = (ECPagerViewAdapter) getAdapter();
        return adapter.getActiveCard().toggle();
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }
}
