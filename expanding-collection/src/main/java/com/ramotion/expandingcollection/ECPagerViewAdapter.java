package com.ramotion.expandingcollection;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import androidx.viewpager.widget.PagerAdapter;
import ramotion.com.expandingcollection.R;

import static android.R.attr.bitmap;

/**
 * Adapter must be implemented to provide your layouts and data(that implements {@link ECCardData})
 * to cards in {@link ECPagerView}.
 */
public abstract class ECPagerViewAdapter extends PagerAdapter {

    private ECPagerCard activeCard;
    private List<ECCardData> dataset;
    private LayoutInflater inflaterService;

    public ECPagerViewAdapter(Context applicationContext, List<ECCardData> dataset) {
        this.inflaterService = (LayoutInflater) applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.dataset = dataset;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final ECPager pager = (ECPager) container;
        final ECPagerCard pagerCard = (ECPagerCard) inflaterService.inflate(R.layout.ec_pager_card, null);
        final ECPagerView pagerContainer = (ECPagerView) pager.getParent();

        ECPagerCardContentList ecPagerCardContentList = pagerCard.getEcPagerCardContentList();
        ECPagerCardHead headView = ecPagerCardContentList.getHeadView();

        headView.setHeight(pagerContainer.getCardHeight());

        Integer drawableRes = dataset.get(position).getHeadBackgroundResource();
        if (drawableRes != null) {
            headView.setHeadImageBitmap(BitmapFactory.decodeResource(pagerContainer.getResources(), drawableRes, new BitmapFactoryOptions()));
        }

        instantiateCard(inflaterService, headView, ecPagerCardContentList, dataset.get(position));

        pager.addView(pagerCard, pagerContainer.getCardWidth(), pagerContainer.getCardHeight());
        return pagerCard;
    }


    public abstract void instantiateCard(LayoutInflater inflaterService, ViewGroup head, ListView list, ECCardData data);

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
