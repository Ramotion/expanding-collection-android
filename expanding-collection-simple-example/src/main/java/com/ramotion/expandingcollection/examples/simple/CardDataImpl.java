package com.ramotion.expandingcollection.examples.simple;

import android.graphics.drawable.BitmapDrawable;

import com.ramotion.expandingcollection.ECCardData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CardDataImpl implements ECCardData<String> {

    private Integer mainBackgroundResource;
    private Integer headBackgroundResource;
    private List<String> listItems;

    public CardDataImpl(Integer mainBackgroundResource, Integer headBackgroundResource, List<String> listItems) {
        this.mainBackgroundResource = mainBackgroundResource;
        this.headBackgroundResource = headBackgroundResource;
        this.listItems = listItems;
    }

    @Override
    public Integer getMainBackgroundResource() {
        return mainBackgroundResource;
    }

    @Override
    public BitmapDrawable getMainBackgroundDrawable() {
        return null;
    }

    @Override
    public Integer getHeadBackgroundResource() {
        return headBackgroundResource;
    }

    @Override
    public BitmapDrawable getHeadBackgroundDrawable() {
        return null;
    }

    @Override
    public List<String> getListItems() {
        return listItems;
    }

    public static List<ECCardData> generateExampleData() {
        List<ECCardData> list = new ArrayList<>();
        list.add(new CardDataImpl(R.drawable.bg1, R.drawable.bg1, createItemsList("Card 1")));
        list.add(new CardDataImpl(R.drawable.bg1, R.drawable.bg1, createItemsList("Card 2")));
        list.add(new CardDataImpl(R.drawable.bg1, R.drawable.bg1, createItemsList("Card 3")));
        return list;
    }

    private static List<String> createItemsList(String cardName) {
        return Arrays.asList(
                cardName + " - Item 1",
                cardName + " - Item 2",
                cardName + " - Item 3",
                cardName + " - Item 4",
                cardName + " - Item 5",
                cardName + " - Item 6",
                cardName + " - Item 7"
        );
    }

}