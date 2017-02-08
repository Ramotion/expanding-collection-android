package com.ramotion.expandingcollection.views;

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

import com.ramotion.expandingcollection.ECCardData;
import com.ramotion.expandingcollection.ECPagerViewAdapter;
import com.ramotion.expandingcollection.utils.AlphaScalePageTransformer;

public class ECPager extends ViewPager {

    private boolean pagingEnabled = true;
    private boolean animationInProgress;

    public ECPager(Context context) {
        super(context);
        init();
    }

    public ECPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.setOffscreenPageLimit(2);
        this.setPageTransformer(false, new AlphaScalePageTransformer());
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
        return ((ECPagerViewAdapter) this.getAdapter()).getDataset().get(position);
    }

    public void setPagingEnabled(boolean b) {
        this.pagingEnabled = b;
    }

    public boolean isPagingEnabled() {
        return pagingEnabled;
    }

    public boolean isAnimationInProgress() {
        return animationInProgress;
    }

    public void setAnimationInProgress(boolean animationInProgress) {
        this.animationInProgress = animationInProgress;
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
    }

    public void animateSize(int width, int height, int duration, int heightAnimationDelay) {
        final ViewGroup.LayoutParams pagerLayoutParams = this.getLayoutParams();

        ValueAnimator pagerWidthAnimation = new ValueAnimator();
        pagerWidthAnimation.setInterpolator(new AccelerateInterpolator());
        pagerWidthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                pagerLayoutParams.width = (int) animation.getAnimatedValue();
                ECPager.this.setLayoutParams(pagerLayoutParams);
            }
        });

        ValueAnimator pagerHeightAnimation = new ValueAnimator();
        pagerHeightAnimation.setInterpolator(new DecelerateInterpolator());
        pagerHeightAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                pagerLayoutParams.height = (int) animation.getAnimatedValue();
                ECPager.this.setLayoutParams(pagerLayoutParams);
            }
        });

        pagerWidthAnimation.setIntValues(pagerLayoutParams.width, width);
        pagerHeightAnimation.setIntValues(pagerLayoutParams.height, height);

        pagerWidthAnimation.setDuration(duration);
        pagerHeightAnimation.setDuration(duration);
        pagerHeightAnimation.setStartDelay(heightAnimationDelay);

        pagerWidthAnimation.start();
        pagerHeightAnimation.start();
    }
}
