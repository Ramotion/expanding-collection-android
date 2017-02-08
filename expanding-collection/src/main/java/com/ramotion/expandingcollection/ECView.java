package com.ramotion.expandingcollection;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ramotion.expandingcollection.views.ECPager;

import java.util.List;

import ramotion.com.expandingcollection.R;

public class ECView extends RelativeLayout {
    public static final String TAG = "ecview";

    private Integer cardWidth;
    private Integer cardHeight;
    private Integer cardHeaderHeightNormal;
    private Integer cardHeaderHeightExpanded;

    private LayoutInflater inflater;
    private ECPager pagerView;
    private ImageView bgImageView;

    public ECView(Context context) {
        super(context);
        inflateAndInitialize(context);
    }

    public ECView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateAndInitialize(context);
    }

    public ECView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateAndInitialize(context);
    }

    private void inflateAndInitialize(Context applicationContext) {
        inflater = (LayoutInflater) applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.ec_pager, this, true);

        pagerView = (ECPager) findViewById(R.id.pager_view);

        //TODO: Animate background changes
        RelativeLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        bgImageView = new ImageView(applicationContext);
        bgImageView.setBackgroundColor(Color.YELLOW);
        bgImageView.setLayoutParams(layoutParams);
        bgImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        this.addView(bgImageView, 0, layoutParams);

        pagerView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bgImageView.setImageDrawable(pagerView.getDataFromAdapterDataset(position).getBgImageDrawable());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public ECView withCardSize(int cardWidth, int cardHeight) {
        this.cardWidth = cardWidth;
        this.cardHeight = cardHeight;
        this.pagerView.updateLayoutDimensions(cardWidth, cardHeight);
        return this;
    }

    public ECView withCardHeaderHeight(int cardHeaderHeightNormal, int cardHeaderHeightExpanded) {
        this.cardHeaderHeightNormal = cardHeaderHeightNormal;
        this.cardHeaderHeightExpanded = cardHeaderHeightExpanded;
        return this;
    }

    public ECView withPagerAdapter(ECPagerViewAdapter adapter) {
        this.pagerView.setAdapter(adapter);
        List<ECCardData> dataset = adapter.getDataset();
        if (dataset != null && dataset.size() > 1)
            bgImageView.setImageDrawable(dataset.get(0).getBgImageDrawable());
        return this;
    }

    public int getCardWidth() {
        return cardWidth;
    }

    public int getCardHeight() {
        return cardHeight;
    }

    public int getCardHeaderHeightNormal() {
        return cardHeaderHeightNormal;
    }

    public int getCardHeaderHeightExpanded() {
        return cardHeaderHeightExpanded;
    }
}

