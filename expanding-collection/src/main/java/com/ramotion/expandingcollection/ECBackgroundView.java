package com.ramotion.expandingcollection;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

public class ECBackgroundView extends ImageSwitcher {

    private final int[] REVERSE_ORDER = new int[]{1, 0};
    private final int[] NORMAL_ORDER = new int[]{0, 1};

    private boolean reverseDrawOrder;

    private int bgImageGap;
    private int bgImageWidth;

    private int alphaDuration = 400;
    private int movementDuration = 500;
    private int widthBackgroundImageGapPercent = 12;

    private AnimationDirection currentAnimationDirection;

    public ECBackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateAndInit(context);
    }

    public ECBackgroundView(Context context) {
        super(context);
        inflateAndInit(context);
    }

    private void inflateAndInit(final Context context) {
        setChildrenDrawingOrderEnabled(true);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        bgImageGap = (displayMetrics.widthPixels / 100) * widthBackgroundImageGapPercent;
        bgImageWidth = displayMetrics.widthPixels + bgImageGap * 2;

        this.setFactory(new ViewSwitcher.ViewFactory() {
            public View makeView() {
                ImageView myView = new ImageView(context);
                myView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                myView.setLayoutParams(new FrameLayout.LayoutParams(bgImageWidth, FrameLayout.LayoutParams.MATCH_PARENT));
                myView.setTranslationX(-bgImageGap);
                return myView;
            }
        });
    }

    public ECBackgroundView withAnimationSettings(int movementDuration, int alphaDuration) {
        this.movementDuration = movementDuration;
        this.alphaDuration = alphaDuration;
        return this;
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        if (reverseDrawOrder)
            return REVERSE_ORDER[i];
        else
            return NORMAL_ORDER[i];
    }

    public boolean isReverseDrawOrder() {
        return reverseDrawOrder;
    }

    public void setReverseDrawOrder(boolean reverseDrawOrder) {
        this.reverseDrawOrder = reverseDrawOrder;
    }

    public void setImageDrawableWithAnimation(Drawable newDrawable, AnimationDirection animationDirection) {
        if (this.currentAnimationDirection == animationDirection) {
            this.setImageDrawable(newDrawable);
        } else if (animationDirection == AnimationDirection.LEFT) {
            this.setInAnimation(createBgImageInAnimation(bgImageGap, 0, movementDuration, alphaDuration));
            this.setOutAnimation(createBgImageOutAnimation(0, -bgImageGap, movementDuration));
            this.setImageDrawable(newDrawable);
        } else if (animationDirection == AnimationDirection.RIGHT) {
            this.setInAnimation(createBgImageInAnimation(-bgImageGap, 0, movementDuration, alphaDuration));
            this.setOutAnimation(createBgImageOutAnimation(0, bgImageGap, movementDuration));
            this.setImageDrawable(newDrawable);
        }
        this.currentAnimationDirection = animationDirection;
    }

    private Animation createBgImageInAnimation(int fromX, int toX, int transitionDuration, int alphaDuration) {
        TranslateAnimation translate = new TranslateAnimation(fromX, toX, 0, 0);
        translate.setDuration(transitionDuration);

        AlphaAnimation alpha = new AlphaAnimation(0F, 1F);
        alpha.setDuration(alphaDuration);

        AnimationSet set = new AnimationSet(true);
        set.setInterpolator(new DecelerateInterpolator());
        set.addAnimation(translate);
        set.addAnimation(alpha);
        return set;
    }

    private Animation createBgImageOutAnimation(int fromX, int toX, int transitionDuration) {
        TranslateAnimation ta = new TranslateAnimation(fromX, toX, 0, 0);
        ta.setDuration(transitionDuration);
        ta.setInterpolator(new DecelerateInterpolator());
        return ta;
    }

    enum AnimationDirection {
        LEFT, RIGHT;
    }
}
