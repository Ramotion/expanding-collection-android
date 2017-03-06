package com.ramotion.expandingcollection;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ramotion.expandingcollection.views.ECPagerCard;
import com.ramotion.expandingcollection.views.ECPager;
import com.ramotion.expandingcollection.views.ECPagerCardBody;
import com.ramotion.expandingcollection.views.ECPagerCardHead;
import com.ramotion.expandingcollection.views.ECPagerView;

import java.util.List;

import ramotion.com.expandingcollection.R;

public abstract class ECPagerAdapter extends PagerAdapter {
    public static final String TAG = "ecview";

    private ECPagerCard activeCard;
    private List<ECCardData> dataset;
    private LayoutInflater mInflater;

    public ECPagerAdapter(Context applicationContext, List<ECCardData> dataset) {
        this.mInflater = (LayoutInflater) applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.dataset = dataset;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final ECPager pager = (ECPager) container;
        final ECPagerCard pagerCard = (ECPagerCard) mInflater.inflate(R.layout.ec_pager_card, null);
        final ECPagerView pagerContainer = (ECPagerView) pager.getParent();

        ECPagerCardHead cardHead = pagerCard.getPagerCardContent().getCardHead();
        ECPagerCardBody cardBody = pagerCard.getPagerCardContent().getCardBody();

        cardHead.setHeight(pagerContainer.getCardHeaderHeightNormal());
        cardHead.setHeadImageDrawable(dataset.get(position).getHeadBgImageDrawable());
        cardHead.setHeadTitleText(dataset.get(position).getHeadTitle());
        cardHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                pagerCard.toggle();
            }
        });

        bindCardContentData(mInflater, cardBody, dataset.get(position));

        pager.addView(pagerCard, pagerContainer.getCardWidth(), pagerContainer.getCardHeight());
        return pagerCard;
    }

    public abstract void bindCardContentData(LayoutInflater mInflater, ViewGroup rootCardContentView, ECCardData dataHolder);

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        activeCard = (ECPagerCard) object;
    }

    public ECPagerCard getActiveCard() {
        return activeCard;
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
