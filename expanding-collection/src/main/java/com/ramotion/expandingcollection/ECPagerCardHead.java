package com.ramotion.expandingcollection;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;


/**
 * View used to be a card head that is visible when card is collapsed. Just frame layout with background image
 * and logic for animate height.
 */
public class ECPagerCardHead extends FrameLayout {

    private ImageView headBackgroundImageView;

    public ECPagerCardHead(Context context) {
        super(context);
        init(context);
    }

    public ECPagerCardHead(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ECPagerCardHead(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
//        headBackgroundImageView = new TopCropImageView(context);
        headBackgroundImageView = new ImageView(context);
        headBackgroundImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT);
        headBackgroundImageView.setLayoutParams(params);
        this.addView(headBackgroundImageView);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        try {
            headBackgroundImageView = (ImageView) getChildAt(0);
        } catch (Exception e) {
            throw new IllegalStateException("Invalid children elements in ECPagerCardHead.");
        }
    }

    protected void setHeadImageDrawable(Drawable headImageDrawable) {
        if (this.headBackgroundImageView != null)
            this.headBackgroundImageView.setImageDrawable(headImageDrawable);
    }

    protected void setHeadImageDrawable(@DrawableRes int headImageDrawableRes) {
        if (this.headBackgroundImageView != null)
            this.headBackgroundImageView.setImageResource(headImageDrawableRes);
    }

    protected void setHeadImageBitmap(Bitmap headImageBitmap) {
        if (this.headBackgroundImageView != null)
            this.headBackgroundImageView.setImageBitmap(headImageBitmap);
    }

    protected void animateHeight(int targetHeight, int duration, int delay) {
        final ViewGroup.LayoutParams cardHeaderLayoutParams = this.getLayoutParams();

        ValueAnimator cardHeadHeightAnimation = new ValueAnimator();
        cardHeadHeightAnimation.setInterpolator(new DecelerateInterpolator());
        cardHeadHeightAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                cardHeaderLayoutParams.height = (int) animation.getAnimatedValue();
                ECPagerCardHead.this.setLayoutParams(cardHeaderLayoutParams);
            }
        });

        cardHeadHeightAnimation.setIntValues(cardHeaderLayoutParams.height, targetHeight);

        cardHeadHeightAnimation.setDuration(duration);
        cardHeadHeightAnimation.setStartDelay(delay);
        cardHeadHeightAnimation.start();
    }

    protected void setHeight(int height) {
        this.getLayoutParams().height = height;
    }

}
