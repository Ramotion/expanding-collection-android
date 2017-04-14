package com.ramotion.expandingcollection;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Pager Card it's simple container that wraps card content list and some logic for work with animations.
 */
public class ECPagerCard extends FrameLayout {

    private ECPagerCardContentList ecPagerCardContentList;
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
            ecPagerCardContentList = (ECPagerCardContentList) getChildAt(0);
//            ecPagerCardListContent.disableScroll();
        } catch (Exception e) {
            throw new IllegalStateException("Invalid children element in ECPagerCard.");
        }
    }

    /**
     * Start expand animation.
     *
     * @return true if animation started
     */
    public boolean expand() {
        if (animationInProgress || cardExpanded) return false;
        animationInProgress = true;

        final ECPager pager = (ECPager) getParent();
        final ECPagerView pagerView = (ECPagerView) pager.getParent();

        pager.disablePaging();

        ViewGroup pagerParent = (ViewGroup) pagerView.getParent();
        int expandedCardWidth = pagerParent.getWidth();
        int expandedCardHeight = pagerParent.getHeight();

        AnimatorListenerAdapter onAnimationEnd = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animationInProgress = false;
//                ecPagerCardContentList.enableScroll();
                cardExpanded = true;
            }
        };

        int pushNeighboursDuration = 200;
        int cardAnimDelay = 150;
        int cardAnimDuration = 250;

        pager.animateWidth(expandedCardWidth, pushNeighboursDuration, 0, null);

        ecPagerCardContentList.animateWidth(expandedCardWidth, cardAnimDuration, cardAnimDelay);
        pagerView.toggleTopMargin(cardAnimDuration, cardAnimDelay);
        pager.animateHeight(expandedCardHeight, cardAnimDuration, cardAnimDelay, onAnimationEnd);
        ecPagerCardContentList.getHeadView().animateHeight(pagerView.getCardHeaderExpandedHeight(), cardAnimDuration, cardAnimDelay);
        ecPagerCardContentList.showListElements();
        return true;
    }

    /**
     * Start collapse animation
     *
     * @return true if animation started
     */
    public boolean collapse() {
        if (animationInProgress || !cardExpanded) return false;
        animationInProgress = true;

        final ECPager pager = (ECPager) getParent();
        final ECPagerView pagerView = (ECPagerView) pager.getParent();

        pager.disablePaging();

        ecPagerCardContentList.scrollToTop();
//        ecPagerCardListContent.disableScroll();

        AnimatorListenerAdapter onAnimationEnd = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                animationInProgress = false;
                pager.enablePaging();
                cardExpanded = false;
                ecPagerCardContentList.hideListElements();
            }
        };

        int cardAnimDuration = 250;
        int pushNeighboursDelay = 150;
        int pushNeighboursDuration = 200;

        pagerView.toggleTopMargin(cardAnimDuration, 0);
        pager.animateHeight(pagerView.getCardHeight(), cardAnimDuration, 0, null);
        ecPagerCardContentList.animateWidth(pagerView.getCardWidth(), cardAnimDuration, 0);
        ecPagerCardContentList.getHeadView().animateHeight(pagerView.getCardHeight(), cardAnimDuration, 0);

        pager.animateWidth(pagerView.getCardWidth(), pushNeighboursDuration, pushNeighboursDelay, onAnimationEnd);
        return true;
    }

    /**
     * Toggle state of card - collapse if card is expanded and otherwise
     *
     * @return true if animation started
     */
    public boolean toggle() {
        if (cardExpanded)
            return collapse();
        else return expand();
    }

    public ECPagerCardContentList getEcPagerCardContentList() {
        return ecPagerCardContentList;
    }
}
