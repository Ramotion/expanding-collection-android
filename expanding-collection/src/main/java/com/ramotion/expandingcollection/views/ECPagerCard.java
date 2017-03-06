package com.ramotion.expandingcollection.views;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ramotion.expandingcollection.utils.AnimationListener;

public class ECPagerCard extends FrameLayout {

    private ECPagerCardContent pagerCardContent;
    private boolean animationInProgress;
    private boolean cardExpanded;

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
        super.onFinishInflate();
        try {
            pagerCardContent = (ECPagerCardContent) getChildAt(0);
            pagerCardContent.disableScroll();
        } catch (Exception e) {
            throw new IllegalStateException("Invalid children element in ECPagerCard.");
        }
    }

    public ECPagerCardContent getPagerCardContent() {
        return pagerCardContent;
    }

    public boolean expand() {
        if (animationInProgress || cardExpanded) return false;
        animationInProgress = true;

        final ECPager pager = (ECPager) getParent();
        final ECPagerView pagerView = (ECPagerView) pager.getParent();

        pager.disablePaging();

        ViewGroup rootElement = (ViewGroup) pagerView.getParent();
        int expandedCardWidth = rootElement.getWidth() - pagerView.getOpenedCardHorizontalMargin();
        int expandedCardHeight = rootElement.getHeight() - pagerView.getOpenedCardVerticalMargin();

        AnimationListener onAnimationEnd = new AnimationListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animationInProgress = false;
                pagerCardContent.getCardBody().enableTouch();
                pagerCardContent.enableScroll();
                cardExpanded = true;
            }
        };

        int pushNeighboursDuration = 200;
        int cardAnimDelay = 150;
        int cardAnimDuration = 250;

        pager.animateWidth(expandedCardWidth, pushNeighboursDuration, 0, null);

        pagerCardContent.animateWidth(expandedCardWidth, cardAnimDuration, cardAnimDelay);
        pagerView.toggleTopMargin(cardAnimDuration, cardAnimDelay);
        pager.animateHeight(expandedCardHeight, cardAnimDuration, cardAnimDelay, onAnimationEnd);
        pagerCardContent.getCardHead().animateHeight(pagerView.getCardHeaderHeightExpanded(), cardAnimDuration, cardAnimDelay);
        return true;
    }

    public boolean collapse() {
        if (animationInProgress || !cardExpanded) return false;
        animationInProgress = true;

        final ECPager pager = (ECPager) getParent();
        final ECPagerView pagerView = (ECPagerView) pager.getParent();

        pager.disablePaging();

        pagerCardContent.scrollToTop();
        pagerCardContent.disableScroll();

        AnimationListener onAnimationEnd = new AnimationListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animationInProgress = false;
                pager.enablePaging();
                pagerCardContent.getCardBody().disableTouch();
                cardExpanded = false;
            }
        };

        int cardAnimDuration = 250;
        int pushNeighboursDelay = 150;
        int pushNeighboursDuration = 200;

        pagerView.toggleTopMargin(cardAnimDuration, 0);
        pager.animateHeight(pagerView.getCardHeight(), cardAnimDuration, 0, null);
        pagerCardContent.getCardHead().animateHeight(pagerView.getCardHeaderHeightNormal(), cardAnimDuration, 0);
        pagerCardContent.animateWidth(pagerView.getCardWidth(), cardAnimDuration, 0);

        pager.animateWidth(pagerView.getCardWidth(), pushNeighboursDuration, pushNeighboursDelay, onAnimationEnd);
        return true;
    }

    public boolean toggle() {
        if (cardExpanded)
            return collapse();
        else return expand();
    }
}
