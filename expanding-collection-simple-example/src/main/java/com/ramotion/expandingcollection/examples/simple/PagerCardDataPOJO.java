package com.ramotion.expandingcollection.examples.simple;

import android.graphics.drawable.Drawable;

import com.ramotion.expandingcollection.ECCardData;

import java.util.Random;

public class PagerCardDataPOJO implements ECCardData {

    private String headTitle;
    private Drawable headBgImageDrawable;
    private Drawable mainBgImageDrawable;

    private Drawable personPicture;
    private String personName;
    private String personMessage;
    private int personViewsCount;
    private int personCommentsCount;
    private int personLikesCount;
    private CommentPOJO[] comments;

    public PagerCardDataPOJO() {
        Random rnd = new Random();
        this.personViewsCount = 50 + rnd.nextInt(950);
        this.personCommentsCount = 35 + rnd.nextInt(170);
        this.personLikesCount = 10 + rnd.nextInt(1000);
    }

    public String getHeadTitle() {
        return headTitle;
    }

    public void setHeadTitle(String headTitle) {
        this.headTitle = headTitle;
    }

    @Override
    public Drawable getMainBgImageDrawable() {
        return headBgImageDrawable;
    }

    @Override
    public Drawable getHeadBgImageDrawable() {
        return headBgImageDrawable;
    }

    public void setBgImageDrawable(Drawable bgImageDrawable) {
        this.headBgImageDrawable = bgImageDrawable;
    }

    public void setHeadBgImageDrawable(Drawable headBgImageDrawable) {
        this.headBgImageDrawable = headBgImageDrawable;
    }

    public void setMainBgImageDrawable(Drawable mainBgImageDrawable) {
        this.mainBgImageDrawable = mainBgImageDrawable;
    }

    public Drawable getPersonPicture() {
        return personPicture;
    }

    public void setPersonPicture(Drawable personPicture) {
        this.personPicture = personPicture;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonMessage() {
        return personMessage;
    }

    public void setPersonMessage(String personMessage) {
        this.personMessage = personMessage;
    }

    public int getPersonViewsCount() {
        return personViewsCount;
    }

    public void setPersonViewsCount(int personViewsCount) {
        this.personViewsCount = personViewsCount;
    }

    public int getPersonCommentsCount() {
        return personCommentsCount;
    }

    public void setPersonCommentsCount(int personCommentsCount) {
        this.personCommentsCount = personCommentsCount;
    }

    public int getPersonLikesCount() {
        return personLikesCount;
    }

    public void setPersonLikesCount(int personLikesCount) {
        this.personLikesCount = personLikesCount;
    }

    public CommentPOJO[] getComments() {
        return comments;
    }

    public void setComments(CommentPOJO[] comments) {
        this.comments = comments;
    }
}
