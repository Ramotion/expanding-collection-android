package com.ramotion.expandingcollection;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class ECPagerCardContent extends ScrollView {

    private ECPagerCardHead cardHead;
    private ECPagerCardBody cardBody;

    private boolean scrollingEnabled = true;

    public ECPagerCardContent(Context context) {
        super(context);
    }

    public ECPagerCardContent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ECPagerCardContent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        try {
            LinearLayout layout = (LinearLayout) getChildAt(0);
            cardHead = (ECPagerCardHead) layout.getChildAt(0);
            cardBody = (ECPagerCardBody) layout.getChildAt(1);
        } catch (Exception e) {
            throw new IllegalStateException("Invalid children elements in ECPagerCard.");
        }
    }

    public ECPagerCardHead getCardHead() {
        return cardHead;
    }

    public ECPagerCardBody getCardBody() {
        return cardBody;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return scrollingEnabled && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return scrollingEnabled && super.onTouchEvent(ev);
    }

    public void enableScroll() {
        this.scrollingEnabled = true;
        setVerticalScrollBarEnabled(true);
    }

    public void disableScroll() {
        this.scrollingEnabled = false;
        setVerticalScrollBarEnabled(false);
    }

    public void scrollToTop() {
        this.smoothScrollTo(0, 0);
    }

    public void animateWidth(int targetWidth, int duration, int delay) {
        // reset own width for smooth animation and avoid values like 'MATCH_PARENT'
        this.getLayoutParams().width = this.getWidth();

        ValueAnimator widthAnimation = new ValueAnimator();
        widthAnimation.setInterpolator(new DecelerateInterpolator());
        widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewGroup.LayoutParams pagerLayoutParams = getLayoutParams();
                pagerLayoutParams.width = (int) animation.getAnimatedValue();
                setLayoutParams(pagerLayoutParams);
            }
        });
        widthAnimation.setIntValues(getWidth(), targetWidth);
        widthAnimation.setStartDelay(delay);
        widthAnimation.setDuration(duration);
        widthAnimation.start();
    }
}
