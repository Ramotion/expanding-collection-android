package com.ramotion.expandingcollection.examples.simple;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ramotion.expandingcollection.ECCardData;
import com.ramotion.expandingcollection.ECPagerCardContentList;
import com.ramotion.expandingcollection.ECPagerView;
import com.ramotion.expandingcollection.ECPagerViewAdapter;

import java.util.List;

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
                final CardDataImpl cardData = (CardDataImpl) data;

                list.setEcArrayAdapter(new CardListItemAdapter(getApplicationContext(), cardData.getListItems()));

                head.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        ecPagerView.toggle();
                    }
                });
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (!ecPagerView.collapse())
            super.onBackPressed();
    }


}
