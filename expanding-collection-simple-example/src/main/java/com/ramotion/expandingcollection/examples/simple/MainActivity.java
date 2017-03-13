package com.ramotion.expandingcollection.examples.simple;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
            public void instantiateCard(LayoutInflater inflaterService, ViewGroup cardHead, ViewGroup cardBody, ECCardData data) {
                PagerCardDataPOJO cardDataHolder = (PagerCardDataPOJO) data;

                // inflate custom layout and place it in card body
//                inflaterService.inflate(R.layout.simple_body, cardBody);
//
//                ImageView testListHeader = new ImageView(getApplicationContext());
//                testListHeader.setBackgroundColor(Color.RED);
//                testListHeader.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
//
//                ListView commentsList = (ListView) cardBody.findViewById(R.id.body_list_view);
//                commentsList.addHeaderView(testListHeader);
//                commentsList.setAdapter(new CommentsArrayAdapter(getApplicationContext(), R.layout.list_element, ((PagerCardDataPOJO) data).getComments()));

                // do the same for header
                inflaterService.inflate(R.layout.simple_head, cardHead);

                TextView title = (TextView) cardHead.findViewById(R.id.title);
                title.setText(cardDataHolder.getHeadTitle());

                ImageView avatar = (ImageView) cardHead.findViewById(R.id.avatar);
                avatar.setImageDrawable(cardDataHolder.getPersonPicture());

                TextView name = (TextView) cardHead.findViewById(R.id.name);
                name.setText(cardDataHolder.getPersonName());

                TextView message = (TextView) cardHead.findViewById(R.id.message);
                message.setText(cardDataHolder.getPersonMessage());

                TextView viewsCount = (TextView) cardHead.findViewById(R.id.socialViewsCount);
                viewsCount.setText(" " + cardDataHolder.getPersonViewsCount());

                TextView likesCount = (TextView) cardHead.findViewById(R.id.socialLikesCount);
                likesCount.setText(" " + cardDataHolder.getPersonLikesCount());

                TextView commentsCount = (TextView) cardHead.findViewById(R.id.socialCommentsCount);
                commentsCount.setText(" " + cardDataHolder.getPersonCommentsCount());

                // add toggling card by tap on card head
                cardHead.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        ecPagerView.toggle();
                    }
                });


            }
        };

        // Get views
        ECBackgroundView bgView = (ECBackgroundView) findViewById(R.id.ec_bg_switcher_element);
        ecPagerView = (ECPagerView) findViewById(R.id.ec_pager_element);

        // Tune pager view
        ecPagerView
                .withCardSize(500, 600)
                .withCardExpandedHeaderHeight(500)
                .withBackgroundImageSwitcher(bgView)
                .withPagerAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        if (!ecPagerView.collapse())
            super.onBackPressed();
    }

    private List<ECCardData> getExampleDataSet(int imageWidth, int imageHeight) {
        List<ECCardData> dataset = new ArrayList<>();

        PagerCardDataPOJO item3 = new PagerCardDataPOJO();
        item3.setBgImageDrawable(placePenguin(imageWidth, imageHeight));
        item3.setHeadTitle("AWESOMENESS");
        item3.setPersonMessage("Nanomachine of a clear advice, deserve the sonic shower!");
        item3.setPersonName("Fedor Bondarchuk");
        item3.setPersonPicture(placePenguin(100, 100));
        item3.setComments(prepareCommentsArray());
        dataset.add(item3);

        PagerCardDataPOJO item2 = new PagerCardDataPOJO();
        item2.setBgImageDrawable(placePenguin(imageWidth, imageHeight));
        item2.setHeadTitle("LANDMARK");
        item2.setPersonName("James Parkinson");
        item2.setPersonMessage("Whales scream on fortune at port royal!");
        item2.setComments(prepareCommentsArray());
        item2.setPersonPicture(placePenguin(100, 100));
        dataset.add(item2);

        PagerCardDataPOJO item1 = new PagerCardDataPOJO();
        item1.setBgImageDrawable(placePenguin(imageWidth, imageHeight));
        item1.setHeadTitle("Cuisine");
        item1.setPersonMessage("Crescere satis ducunt ad teres terror.");
        item1.setPersonName("Peter Robinson");
        item1.setPersonPicture(placePenguin(100, 100));
        item1.setComments(prepareCommentsArray());
        dataset.add(item1);

        return dataset;
    }

    private CommentPOJO[] prepareCommentsArray() {
        return new CommentPOJO[]{
                new CommentPOJO(placePenguin(100, 100), "Bob Marley", "No womans, no cry", "9 apr. 2014"),
                new CommentPOJO(placePenguin(100, 100), "John Avon", "For Zendikar!", "12 jan. 2014"),
                new CommentPOJO(placePenguin(100, 100), "Rebecca Guay", "Faeries are awesome", "1 jun. 2015"),
                new CommentPOJO(placePenguin(100, 100), "Peter Jackson", "There and Back Again", "21 sep. 1937")
        };

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
