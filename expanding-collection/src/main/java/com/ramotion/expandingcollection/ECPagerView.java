package com.ramotion.expandingcollection;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.util.List;

import androidx.viewpager.widget.ViewPager;
import ramotion.com.expandingcollection.R;

/**
 * Root PagerView element. Wraps all logic, animations and behavior.
 */
public class ECPagerView extends FrameLayout implements ViewPager.OnPageChangeListener {
    private ECPager pager;
    private ECBackgroundSwitcherView attachedImageSwitcher;
    private OnCardSelectedListener onCardSelectedListener;

    private boolean needsRedraw;
    private int nextTopMargin = 0;

    private Point center = new Point();
    private Point initialTouch = new Point();

    private Integer cardWidth;
    private Integer cardHeight;
    private Integer cardHeaderExpandedHeight;

    public ECPagerView(Context context) {
        super(context);
        init(context);
    }

    public ECPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeFromAttributes(context, attrs);
        init(context);
    }

    public ECPagerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeFromAttributes(context, attrs);
        init(context);
    }

    protected void initializeFromAttributes(Context context, AttributeSet attrs) {
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ExpandingCollection, 0, 0);
        try {
            this.cardWidth = array.getDimensionPixelSize(R.styleable.ExpandingCollection_cardWidth, 500);
            this.cardHeight = array.getDimensionPixelSize(R.styleable.ExpandingCollection_cardHeight, 550);
            this.cardHeaderExpandedHeight = array.getDimensionPixelSize(R.styleable.ExpandingCollection_cardHeaderHeightExpanded, 450);
        } finally {
            array.recycle();
        }
    }

    private void init(Context context) {
        setClipChildren(false);
        setClipToPadding(false);

        if (Build.VERSION.SDK_INT < 21)
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        pager = new ECPager(context);

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        this.addView(pager, 0, layoutParams);

        pager.setPageTransformer(false, new AlphaScalePageTransformer());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        try {
            pager = (ECPager) getChildAt(0);
            pager.addOnPageChangeListener(this);
            pager.updateLayoutDimensions(cardWidth, cardHeight);
        } catch (Exception e) {
            throw new IllegalStateException("The root child of PagerContainer must be a ViewPager");
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        center.x = w / 2;
        center.y = h / 2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // We capture any touches not already handled by the ViewPager
        // to implement scrolling from a touch outside the pager bounds.
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initialTouch.x = (int) ev.getX();
                initialTouch.y = (int) ev.getY();
            default:
                ev.offsetLocation(center.x - initialTouch.x, center.y - initialTouch.y);
                break;
        }
        return pager.dispatchTouchEvent(ev);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //Force the container to redraw on scrolling.
        //Without this the outer pages render initially and then stay static
        if (needsRedraw) invalidate();
    }

    @Override
    public void onPageSelected(int position) {
        int oldPosition = pager.getCurrentPosition();
        pager.setCurrentPosition(position);

        ECBackgroundSwitcherView.AnimationDirection direction = null;
        int nextPositionPrediction = position;
        if (oldPosition < position) {
            direction = ECBackgroundSwitcherView.AnimationDirection.LEFT;
            nextPositionPrediction++;
        } else if (oldPosition > position) {
            direction = ECBackgroundSwitcherView.AnimationDirection.RIGHT;
            nextPositionPrediction--;
        }

        if (attachedImageSwitcher != null) {
            attachedImageSwitcher.setReverseDrawOrder(attachedImageSwitcher.getDisplayedChild() == 1);

            // change current image from cache or reinitialize it from resource
            BackgroundBitmapCache instance = BackgroundBitmapCache.getInstance();
            Integer mainBgImageDrawableResource = pager.getDataFromAdapterDataset(position).getMainBackgroundResource();
            if (instance.getBitmapFromBgMemCache(mainBgImageDrawableResource) != null) {
                attachedImageSwitcher.updateCurrentBackground(pager, direction);
            } else {
                attachedImageSwitcher.updateCurrentBackgroundAsync(pager, direction);
            }
            // change background on next predicted position
            attachedImageSwitcher.cacheBackgroundAtPosition(pager, nextPositionPrediction);
        }
        if (onCardSelectedListener != null)
            onCardSelectedListener.cardSelected(position, oldPosition, pager.getAdapter().getCount());
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        needsRedraw = (state != ViewPager.SCROLL_STATE_IDLE);
    }

    protected void toggleTopMargin(int duration, int delay) {
        final RelativeLayout.LayoutParams containerLayoutParams = (RelativeLayout.LayoutParams) this.getLayoutParams();
        int currentTopMargin = containerLayoutParams.topMargin;
        ValueAnimator marginAnimation = new ValueAnimator();
        marginAnimation.setInterpolator(new DecelerateInterpolator());
        marginAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                containerLayoutParams.topMargin = (int) animation.getAnimatedValue();
                ECPagerView.this.setLayoutParams(containerLayoutParams);
            }
        });
        marginAnimation.setIntValues(containerLayoutParams.topMargin, nextTopMargin);
        marginAnimation.setDuration(duration);
        marginAnimation.setStartDelay(delay);
        marginAnimation.start();
        nextTopMargin = currentTopMargin;
    }

    /**
     * Attach {@link ECBackgroundSwitcherView} element to pager view
     *
     * @param imageSwitcher already inflated {@link ECBackgroundSwitcherView} element
     */
    public void setBackgroundSwitcherView(ECBackgroundSwitcherView imageSwitcher) {
        this.attachedImageSwitcher = imageSwitcher;
        if (imageSwitcher == null) return;
        ECPagerViewAdapter adapter = (ECPagerViewAdapter) this.pager.getAdapter();
        if (adapter != null && adapter.getDataset() != null && adapter.getDataset().size() > 1) {
            attachedImageSwitcher.updateCurrentBackground(pager, null);
        }
    }

    /**
     * Set {@link ECPagerViewAdapter} to pager
     *
     * @param adapter implementation of {@link ECPagerViewAdapter}
     */
    public void setPagerViewAdapter(ECPagerViewAdapter adapter) {
        this.pager.setAdapter(adapter);
        if (adapter == null) return;
        List<ECCardData> dataset = adapter.getDataset();
        if (dataset != null && dataset.size() > 1 && attachedImageSwitcher != null) {
            attachedImageSwitcher.updateCurrentBackground(pager, null);
        }
        if (pager.getAdapter() != null && onCardSelectedListener != null)
            onCardSelectedListener.cardSelected(pager.getCurrentPosition(), pager.getCurrentPosition(), pager.getAdapter().getCount());
    }

    /**
     * Tune parameters of PagerView element.
     *
     * @param cardWidth                width of card in collapsed state
     * @param cardHeight               height of card in collapsed state
     * @param cardHeaderExpandedHeight height of card header in expanded state
     */
    public void setAttributes(int cardWidth, int cardHeight, int cardHeaderExpandedHeight) {
        this.cardWidth = cardWidth;
        this.cardHeight = cardHeight;
        this.cardHeaderExpandedHeight = cardHeaderExpandedHeight;
        this.pager.updateLayoutDimensions(cardWidth, cardHeight);
    }

    /**
     * Set {@link OnCardSelectedListener} to pager view.
     *
     * @param listener
     */
    public void setOnCardSelectedListener(OnCardSelectedListener listener) {
        this.onCardSelectedListener = listener;
        if (listener == null) return;
        if (pager.getAdapter() != null)
            onCardSelectedListener.cardSelected(pager.getCurrentPosition(), pager.getCurrentPosition(), pager.getAdapter().getCount());

    }

    public int getCardWidth() {
        return cardWidth;
    }

    public int getCardHeight() {
        return cardHeight;
    }

    public int getCardHeaderExpandedHeight() {
        return cardHeaderExpandedHeight;
    }

    /**
     * Start expand animation for currently active card.
     *
     * @return true if animation started
     */
    public boolean expand() {
        return pager.expand();
    }

    /**
     * Start collapse animation for currently active card.
     *
     * @return true if animation started
     */
    public boolean collapse() {
        return pager.collapse();
    }

    /**
     * Toggle state of currently active card - collapse if card is expanded and otherwise
     *
     * @return true if animation started
     */
    public boolean toggle() {
        return pager.toggle();
    }

    /**
     * Listener will be notified when pager select a new card
     */
    public interface OnCardSelectedListener {
        void cardSelected(int newPosition, int oldPosition, int totalElements);
    }

}
