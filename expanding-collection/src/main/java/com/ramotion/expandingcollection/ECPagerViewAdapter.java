package com.ramotion.expandingcollection;

import android.animation.Animator;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ramotion.expandingcollection.utils.AnimationListener;
import com.ramotion.expandingcollection.views.ECPagerCard;
import com.ramotion.expandingcollection.views.ECPagerCardBody;
import com.ramotion.expandingcollection.views.ECPager;
import com.ramotion.expandingcollection.views.ECPagerCardContainer;
import com.ramotion.expandingcollection.views.ECPagerCardHead;
import com.ramotion.expandingcollection.views.ECPagerContainer;

import java.util.List;

import ramotion.com.expandingcollection.R;

// TODO: Refactor class
public abstract class ECPagerViewAdapter extends PagerAdapter {
    public static final String TAG = "ecview";

    private List<ECCardData> dataset;
    private LayoutInflater mInflater;

    public ECPagerViewAdapter(Context applicationContext, List<ECCardData> dataset) {
        this.mInflater = (LayoutInflater) applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.dataset = dataset;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final ECPagerCardContainer pagerCardContainer = (ECPagerCardContainer) mInflater.inflate(R.layout.ec_pager_card, null);
        final ECPagerCard pagerCard = pagerCardContainer.getPagerCard();
        final ECPagerCardHead cardHeader = pagerCard.getCardHead();

        pagerCard.getCardBody().disableTouch();

        final ECPager pager = (ECPager) container;
        final ECPagerContainer pagerContainer = (ECPagerContainer) pager.getParent();
        final ECView ecRootView = (ECView) pagerContainer.getParent();

        pagerCard.setWidth(ecRootView.getCardWidth());

        cardHeader.setHeadImageDrawable(dataset.get(position).getBgImageDrawable());
        cardHeader.setHeadTitleText(dataset.get(position).getHeadTitle());
        cardHeader.setHeight(ecRootView.getCardHeaderHeightNormal());

        cardHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (pager.isAnimationInProgress()) return;
                pager.setAnimationInProgress(true);

                int openedCardHorizontalMargin = 50;
                int openedCardVerticalMargin = 50;

                if (pager.isPagingEnabled()) {
                    pager.setPagingEnabled(false);
                    pager.animateSize(pagerContainer.getWidth() - openedCardHorizontalMargin, ecRootView.getHeight() - openedCardVerticalMargin, 250, 200);
                    cardHeader.animateHeight(ecRootView.getCardHeaderHeightExpanded(), 200, 200);
                    pagerCard.animateSize(pagerContainer.getWidth() - openedCardHorizontalMargin, ecRootView.getHeight() - openedCardVerticalMargin, 300, 200, new AnimationListener() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            pager.setAnimationInProgress(false);
                            pagerCard.getCardBody().enableTouch();
                        }
                    });
                } else {
                    pager.animateSize(ecRootView.getCardWidth(), ecRootView.getCardHeight(), 250, 0);
                    cardHeader.animateHeight(ecRootView.getCardHeaderHeightNormal(), 200, 0);
                    pagerCard.animateSize(ecRootView.getCardWidth(), ecRootView.getCardHeight(), 300, 0, new AnimationListener() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            pager.setAnimationInProgress(false);
                            pager.setPagingEnabled(true);
                            pagerCard.getCardBody().disableTouch();
                        }
                    });
                }
            }
        });

        bindCardContentData(mInflater, pagerCard.getCardBody(), dataset.get(position));

        pager.addView(pagerCardContainer);
        return pagerCardContainer;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }

    public abstract void bindCardContentData(LayoutInflater mInflater, ViewGroup rootCardContentView, ECCardData dataHolder);

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return dataset.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    public List<ECCardData> getDataset() {
        return dataset;
    }
}
