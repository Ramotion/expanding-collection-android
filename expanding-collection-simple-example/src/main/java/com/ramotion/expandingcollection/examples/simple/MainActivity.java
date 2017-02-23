package com.ramotion.expandingcollection.examples.simple;

import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ramotion.expandingcollection.ECCardData;
import com.ramotion.expandingcollection.ECPagerViewAdapter;
import com.ramotion.expandingcollection.ECView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ramotion.com.expandingcollection.examples.simple.R;

// Simple example of usage expanding collection library
public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // For proper internet access - to get penguins =)
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        // Display sizes for penguins to get penguins =)
        Display display = getWindowManager().getDefaultDisplay();
        Point displaySize = new Point();
        display.getSize(displaySize);
        setContentView(R.layout.activity_main);
        // Prepare example dataset with cute penguins =)
        List<ECCardData> exampleDataSet = getExampleDataSet(displaySize.x, displaySize.y);
        // Create adapter for pager
        ECPagerViewAdapter adapter = new ECPagerViewAdapter(this, exampleDataSet) {
            @Override
            public void bindCardContentData(LayoutInflater mInflater, ViewGroup rootCardContentView, ECCardData dataHolder) {
                mInflater.inflate(R.layout.simple_list, rootCardContentView);
            }
        };
        // Get our view
        ECView ecView = (ECView) findViewById(R.id.ec_view);
        // Tune it with fluent api
        ecView
                .withCardSize(500, 400)
                .withCardHeaderHeight(300, 300)
                .withOpenedCardMargins(0, 0)
                .withPagerAdapter(adapter);
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
