package com.ramotion.expandingcollection.views;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.ramotion.expandingcollection.utils.AnimationListener;

public class ECPagerCard extends LinearLayout {

    private ECPagerCardHead cardHead;
    private ECPagerCardBody cardBody;

    public ECPagerCard(Context context) {
        super(context);
    }

    public ECPagerCard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ECPagerCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        try {
            cardHead = (ECPagerCardHead) getChildAt(0);
            cardBody = (ECPagerCardBody) getChildAt(1);
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

    public void animateSize(int width, int height, int duration, int delay, AnimationListener listener) {
        final ViewGroup.LayoutParams cardLayoutParams = this.getLayoutParams();
        ValueAnimator cardSizeAnimation = new ValueAnimator();
        cardSizeAnimation.setInterpolator(new DecelerateInterpolator());
        cardSizeAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                cardLayoutParams.width = (int) animation.getAnimatedValue("width");
                cardLayoutParams.height = (int) animation.getAnimatedValue("height");
                ECPagerCard.this.setLayoutParams(cardLayoutParams);
            }
        });

        cardSizeAnimation.setValues(
                PropertyValuesHolder.ofInt("width", this.getWidth(), width),
                PropertyValuesHolder.ofInt("height", this.getHeight(), height));

        cardSizeAnimation.addListener(listener);
        cardSizeAnimation.setDuration(duration);
        cardSizeAnimation.setStartDelay(delay);
        cardSizeAnimation.start();
    }

    public void setWidth(int width) {
        this.getLayoutParams().width = width;
    }

}
