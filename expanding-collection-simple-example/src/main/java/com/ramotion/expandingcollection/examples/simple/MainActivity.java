package com.ramotion.expandingcollection.examples.simple;

import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ramotion.expandingcollection.ECCardData;
import com.ramotion.expandingcollection.ECPagerAdapter;
import com.ramotion.expandingcollection.ECBackgroundView;
import com.ramotion.expandingcollection.ECPagerView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ramotion.com.expandingcollection.examples.simple.R;

// Simple example of usage expanding collection library
public class MainActivity extends FragmentActivity {

    private ECPagerView ecPagerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // For proper internet access
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());

        // Display size for load proper background images
        Display display = getWindowManager().getDefaultDisplay();
        Point displaySize = new Point();
        display.getSize(displaySize);
        setContentView(R.layout.activity_main);

        // Prepare example dataset with cute penguins images
        List<ECCardData> exampleDataSet = getExampleDataSet(displaySize.x, displaySize.y);

        // Create adapter for pager
        ECPagerAdapter adapter = new ECPagerAdapter(this, exampleDataSet) {
            @Override
            public void bindCardContentData(LayoutInflater mInflater, ViewGroup rootCardContentView, ECCardData dataHolder) {
                mInflater.inflate(R.layout.simple_list, rootCardContentView);
            }

        };

        // Get views
        ECBackgroundView bgView = (ECBackgroundView) findViewById(R.id.ec_bg_switcher_element);
        ecPagerView = (ECPagerView) findViewById(R.id.ec_pager_element);

        // Tune pager view
        ecPagerView
                .withCardSize(500, 400)             //required
                .withCardHeaderHeight(300, 300)     //required
                .withBackgroundImageSwitcher(bgView)
                .withPagerAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        if (!ecPagerView.collapseCurrentCard())
            super.onBackPressed();
    }

    private List<ECCardData> getExampleDataSet(int imageWidth, int imageHeight) {
        List<ECCardData> dataset = new ArrayList<>();
        dataset.add(new PagerCardDataHolder("Item 1", placePenguin(imageWidth, imageHeight)));
        dataset.add(new PagerCardDataHolder("Item 2", placePenguin(imageWidth, imageHeight)));
        dataset.add(new PagerCardDataHolder("Item 3", placePenguin(imageWidth, imageHeight)));
        dataset.add(new PagerCardDataHolder("Item 4", placePenguin(imageWidth, imageHeight)));
        dataset.add(new PagerCardDataHolder("Item 5", placePenguin(imageWidth, imageHeight)));
        dataset.add(new PagerCardDataHolder("Item 6", placePenguin(imageWidth, imageHeight)));
        dataset.add(new PagerCardDataHolder("Item 7", placePenguin(imageWidth, imageHeight)));
        dataset.add(new PagerCardDataHolder("Item 8", placePenguin(imageWidth, imageHeight)));
        dataset.add(new PagerCardDataHolder("Item 9", placePenguin(imageWidth, imageHeight)));
        return dataset;
    }

    public Drawable placePenguin(int w, int h) {
        try {
            InputStream is = (InputStream) new URL("http://placepenguin.com/" + w + "/" + h + "?" + System.currentTimeMillis()).getContent();
            return Drawable.createFromStream(is, "place the penguin");
        } catch (Exception e) {
            return null;
        }
    }
}
