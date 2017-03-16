package com.ramotion.expandingcollection.examples.simple;

import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ramotion.expandingcollection.ECCardData;
import com.ramotion.expandingcollection.ECPagerViewAdapter;
import com.ramotion.expandingcollection.ECBackgroundView;
import com.ramotion.expandingcollection.ECPagerCardContentList;
import com.ramotion.expandingcollection.ECPagerView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
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
        ECPagerViewAdapter adapter = new ECPagerViewAdapter(this, exampleDataSet) {

            @Override
            public void instantiateCard(LayoutInflater inflaterService, ViewGroup head, ECPagerCardContentList list, final ECCardData data) {
                final PagerCardDataPOJO cardDataHolder = (PagerCardDataPOJO) data;

                // Create adapter for list inside a card and set adapter to card content
                CommentsArrayAdapter commentsArrayAdapter = new CommentsArrayAdapter(getApplicationContext(), R.layout.list_element, cardDataHolder.getComments());
                list.setEcArrayAdapter(commentsArrayAdapter);

                // Inflate header layout and attach it to header
                inflaterService.inflate(R.layout.simple_head, head);

                // Set header data
                TextView title = (TextView) head.findViewById(R.id.title);
                title.setText(cardDataHolder.getHeadTitle());

                ImageView avatar = (ImageView) head.findViewById(R.id.avatar);
                avatar.setImageDrawable(cardDataHolder.getPersonPicture());

                TextView name = (TextView) head.findViewById(R.id.name);
                name.setText(cardDataHolder.getPersonName());

                TextView message = (TextView) head.findViewById(R.id.message);
                message.setText(cardDataHolder.getPersonMessage());

                TextView viewsCount = (TextView) head.findViewById(R.id.socialViewsCount);
                viewsCount.setText(" " + cardDataHolder.getPersonViewsCount());

                TextView likesCount = (TextView) head.findViewById(R.id.socialLikesCount);
                likesCount.setText(" " + cardDataHolder.getPersonLikesCount());

                TextView commentsCount = (TextView) head.findViewById(R.id.socialCommentsCount);
                commentsCount.setText(" " + cardDataHolder.getPersonCommentsCount());

                // add toggling card by tap on card head
                head.setOnClickListener(new View.OnClickListener() {
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
                .withCardSize(500, 550)
                .withCardExpandedHeaderHeight(400)
                .withBackgroundImageSwitcher(bgView)
                .withPagerViewAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        if (!ecPagerView.collapse())
            super.onBackPressed();
    }

    private List<ECCardData> getExampleDataSet(int imageWidth, int imageHeight) {
        List<ECCardData> dataset = new ArrayList<>();

        PagerCardDataPOJO item5 = new PagerCardDataPOJO();
        item5.setBgImageDrawable(placePenguin(imageWidth, imageHeight));
        item5.setHeadTitle("FiftH");
        item5.setPersonMessage("Vae, nix!");
        item5.setPersonName("Frederich Nilson");
        item5.setPersonPicture(placePenguin(100, 100));
        item5.setComments(prepareCommentsArray());
        dataset.add(item5);

        PagerCardDataPOJO item4 = new PagerCardDataPOJO();
        item4.setBgImageDrawable(placePenguin(imageWidth, imageHeight));
        item4.setHeadTitle("FOurTH");
        item4.setPersonMessage("Celery can be covered with heated peanut butter.");
        item4.setPersonName("Peter Jackson");
        item4.setPersonPicture(placePenguin(100, 100));
        item4.setComments(prepareCommentsArray());
        dataset.add(item4);

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

    private List<CommentPOJO> prepareCommentsArray() {
        return Arrays.asList(new CommentPOJO(placePenguin(100, 100), "CASEY AFFLECK", "Manchester by the Sea", "9 apr. 2014"),
                new CommentPOJO(placePenguin(100, 100), "MAHERSHALA ALI", "Moonlight", "12 jan. 2014"),
                new CommentPOJO(placePenguin(100, 100), "EMMA STONE", "La La Land", "1 jun. 2015"),
                new CommentPOJO(placePenguin(100, 100), "VIOLA DAVIS", "Fences", "21 sep. 1937"),
                new CommentPOJO(placePenguin(100, 100), "ZOOTOPIA", "Byron Howard, Rich Moore and Clark Spencer", "21 sep. 1937"),
                new CommentPOJO(placePenguin(100, 100), "LA LA LAND", "Linus Sandgren", "21 sep. 1937"),
                new CommentPOJO(placePenguin(100, 100), "THE WHITE HELMETS", "Orlando von Einsiedel and Joanna Natasegara", "21 sep. 1937"),
                new CommentPOJO(placePenguin(100, 100), "LA LA LAND", "Damien Chazelle", "27 dec. 1952"),
                new CommentPOJO(placePenguin(100, 100), "HACKSAW RIDGE", "John Gilbert", "21 sep. 1937"),
                new CommentPOJO(placePenguin(100, 100), "THE SALESMAN", "Iran", "22 sep. 1938"),
                new CommentPOJO(placePenguin(100, 100), "SUICIDE SQUAD", "Alessandro Bertolazzi, Giorgio Gregorini and Christopher Nelson", "13 nov. 1964"),
                new CommentPOJO(placePenguin(100, 100), "LA LA LAND", "Justin Hurwitz", "11 oct. 1987"));
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
