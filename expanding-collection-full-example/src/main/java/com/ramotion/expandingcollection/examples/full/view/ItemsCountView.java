package com.ramotion.expandingcollection.examples.full.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.ramotion.expandingcollection.examples.full.R;

import androidx.annotation.Nullable;

public class ItemsCountView extends LinearLayout {

    private TextSwitcher textSwitcher;
    private TextView textView;

    public ItemsCountView(Context context) {
        super(context);
        init(context);
    }

    public ItemsCountView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        textSwitcher = new TextSwitcher(context);
        textSwitcher.addView(createViewForTextSwitcher(context));
        textSwitcher.addView(createViewForTextSwitcher(context));

        addView(textSwitcher, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        textView = new TextView(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textView.setTextAppearance(R.style.positionIndicator);
        } else {
            textView.setTextAppearance(context, R.style.positionIndicator);
        }

        addView(textView, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    private TextView createViewForTextSwitcher(Context context) {
        TextView textView = new TextView(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textView.setTextAppearance(R.style.positionIndicatorCurrent);
        } else {
            textView.setTextAppearance(context, R.style.positionIndicatorCurrent);
        }
        textView.setLayoutParams(new TextSwitcher.LayoutParams(TextSwitcher.LayoutParams.WRAP_CONTENT, TextSwitcher.LayoutParams.WRAP_CONTENT));
        return textView;
    }

    @SuppressLint("SetTextI18n")
    public void update(int newPosition, int oldPosition, int totalElements) {
        textView.setText(" / " + totalElements);
        int offset = (int) (textSwitcher.getHeight() * 0.75);
        int duration = 250;
        if (newPosition > oldPosition) {
            textSwitcher.setInAnimation(createPositionAnimation(-offset, 0, 0f, 1f, duration));
            textSwitcher.setOutAnimation(createPositionAnimation(0, offset, 1f, 0f, duration));
        } else if (oldPosition > newPosition) {
            textSwitcher.setInAnimation(createPositionAnimation(offset, 0, 0f, 1f, duration));
            textSwitcher.setOutAnimation(createPositionAnimation(0, -offset, 1f, 0f, duration));
        }
        textSwitcher.setText(String.valueOf(newPosition + 1));
    }

    private Animation createPositionAnimation(int fromY, int toY, float fromAlpha, float toAlpha, int duration) {
        TranslateAnimation translate = new TranslateAnimation(0, 0, fromY, toY);
        translate.setDuration(duration);

        AlphaAnimation alpha = new AlphaAnimation(fromAlpha, toAlpha);
        alpha.setDuration(duration);

        AnimationSet set = new AnimationSet(true);
        set.setInterpolator(new DecelerateInterpolator());
        set.addAnimation(translate);
        set.addAnimation(alpha);
        return set;
    }


}
