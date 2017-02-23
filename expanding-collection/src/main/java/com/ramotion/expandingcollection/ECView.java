package com.ramotion.expandingcollection;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ViewSwitcher;

import com.ramotion.expandingcollection.utils.AlphaScalePageTransformer;
import com.ramotion.expandingcollection.utils.ViewPagerOnPageChangeListener;
import com.ramotion.expandingcollection.views.ECPager;
import com.ramotion.expandingcollection.views.ECPagerBackgroundImageSwitcher;
import com.ramotion.expandingcollection.views.ECPagerContainer;

import java.util.List;

import ramotion.com.expandingcollection.R;

public class ECView extends RelativeLayout {
    public static final String TAG = "ecview";

    private Integer cardWidth;
    private Integer cardHeight;
    private Integer cardHeaderHeightNormal;
    private Integer cardHeaderHeightExpanded;

    private int widthBackgroundImageGapPercent = 12;

    private ECPager pagerView;
    private ECPagerBackgroundImageSwitcher bgImageSwitcher;

    private int openedCardHorizontalMargin;
    private int openedCardVerticalMargin;

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

    private void inflateAndInitialize(final Context appContext) {
        LayoutInflater inflater = (LayoutInflater) appContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.ec_pager, this, true);

        ECPagerContainer pagerContainer = (ECPagerContainer) findViewById(R.id.pager_container);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) pagerContainer.getLayoutParams();
        layoutParams.topMargin = 500;
        pagerContainer.setLayoutParams(layoutParams);

        RelativeLayout.LayoutParams testViewLayoutparams = new LayoutParams(200, 200);
        testViewLayoutparams.setMargins(100, 100, 100, 100);
        ImageView testView = new ImageView(appContext);
        testView.setLayoutParams(testViewLayoutparams);
        testView.setBackgroundColor(Color.BLACK);
        this.addView(testView, 0);

        pagerView = (ECPager) findViewById(R.id.pager_view);

        bgImageSwitcher = new ECPagerBackgroundImageSwitcher(appContext);
        this.addView(bgImageSwitcher, 0, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        final DisplayMetrics displayMetrics = appContext.getResources().getDisplayMetrics();

        final int bgImageGap = (displayMetrics.widthPixels / 100) * widthBackgroundImageGapPercent;
        final int bgImageWidth = displayMetrics.widthPixels + bgImageGap * 2;

        bgImageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            public View makeView() {
                ImageView myView = new ImageView(appContext);
                myView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                myView.setLayoutParams(new FrameLayout.LayoutParams(bgImageWidth, FrameLayout.LayoutParams.MATCH_PARENT));
                myView.setTranslationX(-bgImageGap);
                return myView;
            }
        });

        pagerView.addOnPageChangeListener(new ViewPagerOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                bgImageSwitcher.setReverseDrawOrder(bgImageSwitcher.getDisplayedChild() == 1);

                if (pagerView.getPosition() < position) {
                    bgImageSwitcher.setInAnimation(createBgImageInAnimation(bgImageGap, 0, 500, 400));
                    bgImageSwitcher.setOutAnimation(createBgImageOutAnimation(0, -bgImageGap, 500));
                }
                if (pagerView.getPosition() > position) {
                    bgImageSwitcher.setInAnimation(createBgImageInAnimation(-bgImageGap, 0, 500, 400));
                    bgImageSwitcher.setOutAnimation(createBgImageOutAnimation(0, bgImageGap, 500));
                }
                pagerView.setPosition(position);
                bgImageSwitcher.setImageDrawable(pagerView.getDataFromAdapterDataset(position).getMainBgImageDrawable());
            }
        });

        pagerView.setPageTransformer(false, new AlphaScalePageTransformer());
    }

    private Animation createBgImageInAnimation(int fromX, int toX, int transitionDuration, int alphaDuration) {
        TranslateAnimation ta = new TranslateAnimation(fromX, toX, 0, 0);
        ta.setDuration(transitionDuration);

        AlphaAnimation alpha = new AlphaAnimation(0F, 1F);
        alpha.setDuration(alphaDuration);

        AnimationSet set = new AnimationSet(true);
        set.setInterpolator(new DecelerateInterpolator());
        set.addAnimation(ta);
        set.addAnimation(alpha);
        return set;
    }

    private Animation createBgImageOutAnimation(int fromX, int toX, int transitionDuration) {
        TranslateAnimation ta = new TranslateAnimation(fromX, toX, 0, 0);
        ta.setDuration(transitionDuration);
        ta.setInterpolator(new DecelerateInterpolator());
        return ta;
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
        if (dataset != null && dataset.size() > 1) {
            bgImageSwitcher.setImageDrawable(dataset.get(0).getMainBgImageDrawable());
        }
        return this;
    }

    public ECView withOpenedCardMargins(int horizontalMargin, int verticalMargin) {
        this.openedCardHorizontalMargin = horizontalMargin;
        this.openedCardVerticalMargin = verticalMargin;
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

    public int getOpenedCardHorizontalMargin() {
        return openedCardHorizontalMargin;
    }

    public int getOpenedCardVerticalMargin() {
        return openedCardVerticalMargin;
    }
}

