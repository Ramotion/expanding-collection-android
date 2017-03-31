package com.ramotion.expandingcollection.examples.simple;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.ramotion.expandingcollection.ECCardData;
import com.ramotion.expandingcollection.ECPagerViewAdapter;
import com.ramotion.expandingcollection.ECBackgroundView;
import com.ramotion.expandingcollection.ECPagerCardContentList;
import com.ramotion.expandingcollection.ECPagerView;

import ramotion.com.expandingcollection.examples.simple.R;

// Simple example of usage expanding collection library
public class MainActivity extends FragmentActivity {
    private ECPagerView ecPagerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Create adapter for pager
        ECPagerViewAdapter adapter = new ECPagerViewAdapter(this, new ExampleDataset().getDataset()) {
            @SuppressLint("SetTextI18n")
            @Override
            public void instantiateCard(LayoutInflater inflaterService, ViewGroup head, ECPagerCardContentList list, final ECCardData data) {
                final CardData cardDataHolder = (CardData) data;

                // Create adapter for list inside a card and set adapter to card content
                CommentArrayAdapter commentArrayAdapter = new CommentArrayAdapter(getApplicationContext(), R.layout.list_element, cardDataHolder.getListItems());
                list.setEcArrayAdapter(commentArrayAdapter);

                // Inflate header layout and attach it to header
                View gradient = new View(getApplicationContext());
                gradient.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT));
                gradient.setBackgroundDrawable(getResources().getDrawable(R.drawable.card_head_gradient));
                head.addView(gradient);

                inflaterService.inflate(R.layout.simple_head, head);

                // Set header data
                TextView title = (TextView) head.findViewById(R.id.title);
                title.setText(cardDataHolder.getHeadTitle());

                ImageView avatar = (ImageView) head.findViewById(R.id.avatar);
                avatar.setImageResource(cardDataHolder.getPersonPicture());

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

        ECBackgroundView bgView = (ECBackgroundView) findViewById(R.id.ec_bg_switcher_element);
        ecPagerView = (ECPagerView) findViewById(R.id.ec_pager_element);

        // Tune pager view
        ecPagerView.setPagerViewAdapter(adapter);
        ecPagerView.setBackgroundImageSwitcher(bgView);

        final ItemsCountView itemsCountView = (ItemsCountView) findViewById(R.id.items_count_view);
        ecPagerView.setOnCardSelectedListener(new ECPagerView.OnCardSelectedListener() {
            @Override
            public void cardSelected(int newPosition, int oldPosition, int totalElements) {
                itemsCountView.update(newPosition, oldPosition, totalElements);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!ecPagerView.collapse())
            super.onBackPressed();
    }


}
