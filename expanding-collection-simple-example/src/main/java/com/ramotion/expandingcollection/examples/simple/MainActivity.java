package com.ramotion.expandingcollection.examples.simple;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
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

    private final String TAG = "MainEx";
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

        Log.e(TAG, "Display sizes is " + displaySize.x + " to " + displaySize.y);
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
                name.setText(cardDataHolder.getPersonName() + ":");

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
                .withCardSize((int) (displaySize.x * 0.7), (int) (displaySize.y * 0.45))
                .withCardExpandedHeaderHeight((int) (displaySize.y * 0.35))
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
        item5.setMainBgImageDrawableResource(R.drawable.attractions);
        item5.setHeadBgImageDrawableResource(R.drawable.attractions_head);
        item5.setHeadTitle("Attractions");
        item5.setPersonMessage("Vae, nix!");
        item5.setPersonName("Frederich Nilson");
        item5.setPersonPicture(getResources().getDrawable(R.drawable.m5));
        item5.setComments(prepareCommentsArray());
        dataset.add(item5);

        PagerCardDataPOJO item4 = new PagerCardDataPOJO();
        item4.setMainBgImageDrawableResource(R.drawable.cityscape);
        item4.setHeadBgImageDrawableResource(R.drawable.cityscape_head);
        item4.setHeadTitle("city Scape");
        item4.setPersonMessage("Celery can be covered with heated peanut butter.");
        item4.setPersonName("Peter Jackson");
        item4.setPersonPicture(getResources().getDrawable(R.drawable.f4));
        item4.setComments(prepareCommentsArray());
        dataset.add(item4);

        PagerCardDataPOJO item3 = new PagerCardDataPOJO();
        item3.setMainBgImageDrawableResource(R.drawable.cuisine);
        item3.setHeadBgImageDrawableResource(R.drawable.cuisine_head);
        item3.setHeadTitle("Cuisine");
        item3.setPersonMessage("Nanomachine of a clear advice, deserve the sonic shower!");
        item3.setPersonName("Fedor Bondarchuk");
        item3.setPersonPicture(getResources().getDrawable(R.drawable.m3));
        item3.setComments(prepareCommentsArray());
        dataset.add(item3);

        PagerCardDataPOJO item2 = new PagerCardDataPOJO();
        item2.setMainBgImageDrawableResource(R.drawable.nature);
        item2.setHeadBgImageDrawableResource(R.drawable.nature_head);
        item2.setHeadTitle("Nature");
        item2.setPersonName("James Parkinson");
        item2.setPersonMessage("Whales scream on fortune at port royal!");
        item2.setComments(prepareCommentsArray());
        item2.setPersonPicture(getResources().getDrawable(R.drawable.f2));
        dataset.add(item2);

        PagerCardDataPOJO item1 = new PagerCardDataPOJO();
        item1.setMainBgImageDrawableResource(R.drawable.nightlife);
        item1.setHeadBgImageDrawableResource(R.drawable.nightlife_head);
        item1.setHeadTitle("Night Life");
        item1.setPersonMessage("Crescere satis ducunt ad teres terror.");
        item1.setPersonName("Peter Robinson");
        item1.setPersonPicture(getResources().getDrawable(R.drawable.f1));
        item1.setComments(prepareCommentsArray());
        dataset.add(item1);

        return dataset;
    }

    private List<CommentPOJO> prepareCommentsArray() {
        return Arrays.asList(
                new CommentPOJO(getResources().getDrawable(R.drawable.f1), "Pamela Jenkins", "When the sensor experiments for deep space, all mermaids accelerate mysterious, vital moons.", "12 jan. 2014"),
                new CommentPOJO(getResources().getDrawable(R.drawable.m1), "Miguel Evans", "It is a cold powerdrain, sir.", "1 jun. 2015"),
                new CommentPOJO(getResources().getDrawable(R.drawable.f2), "Amber Butler", "Particle of a calm shield, control the alignment!", "21 sep. 1937"),
                new CommentPOJO(getResources().getDrawable(R.drawable.f3), "Chloe Ray", "The human kahless quickly promises the phenomenan.", "21 sep. 1937"),
                new CommentPOJO(getResources().getDrawable(R.drawable.m2), "Duane Miles", "Ionic cannon at the infinity room was the sensor of voyage, imitated to a dead pathway.", "21 sep. 1937"),
                new CommentPOJO(getResources().getDrawable(R.drawable.m3), "Francis Armstrong", "Vital particles, to the port.", "21 sep. 1937"),
                new CommentPOJO(getResources().getDrawable(R.drawable.f4), "Valerie Stephens", "Stars fly with hypnosis at the boldly infinity room!", "27 dec. 1952"),
                new CommentPOJO(getResources().getDrawable(R.drawable.m4), "Julio Ward", "Hypnosis, definition, and powerdrain.", "21 sep. 1937"),
                new CommentPOJO(getResources().getDrawable(R.drawable.m5), "David Gibson", "When the queen experiments for nowhere, all particles control reliable, cold captains.", "13 nov. 1964"));
    }

    public BitmapDrawable placePenguin(int w, int h) {
        try {
            InputStream is = (InputStream) new URL("http://placepenguin.com/" + w + "/" + h + "?" + System.currentTimeMillis()).getContent();
            return (BitmapDrawable) BitmapDrawable.createFromStream(is, "place the penguin");
        } catch (Exception e) {
            return null;
        }
    }
}
