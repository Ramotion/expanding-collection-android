package com.ramotion.expandingcollection.examples.simple;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ramotion.expandingcollection.ECBackgroundSwitcherView;
import com.ramotion.expandingcollection.ECCardData;
import com.ramotion.expandingcollection.ECPagerCardContentList;
import com.ramotion.expandingcollection.ECPagerView;
import com.ramotion.expandingcollection.ECPagerViewAdapter;

import java.util.List;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;

public class MainActivity extends Activity {

    private ECPagerView ecPagerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ecPagerView = (ECPagerView) findViewById(R.id.ec_pager_element);


        List<ECCardData> dataset = CardDataImpl.generateExampleData();

        ecPagerView.setPagerViewAdapter(new ECPagerViewAdapter(getApplicationContext(), dataset) {
            @Override
            public void instantiateCard(LayoutInflater inflaterService, ViewGroup head, ECPagerCardContentList list, ECCardData data) {
                CardDataImpl cardData = (CardDataImpl) data;

                list.setEcArrayAdapter(new CardListItemAdapter(getApplicationContext(), cardData.getListItems()));
                list.setBackgroundColor(Color.WHITE);

                TextView cardTitle = new TextView(getApplicationContext());
                cardTitle.setTextSize(COMPLEX_UNIT_DIP, 20);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.CENTER;
                cardTitle.setText(cardData.getCardTitle());
                head.addView(cardTitle, layoutParams);

                head.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        ecPagerView.toggle();
                    }
                });
            }
        });

        ecPagerView.setBackgroundSwitcherView((ECBackgroundSwitcherView) findViewById(R.id.ec_bg_switcher_element));

    }

    @Override
    public void onBackPressed() {
        if (!ecPagerView.collapse())
            super.onBackPressed();
    }

}
