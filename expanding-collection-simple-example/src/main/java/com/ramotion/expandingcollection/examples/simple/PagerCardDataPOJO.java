package com.ramotion.expandingcollection.examples.simple;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.ramotion.expandingcollection.ECCardData;

import java.util.Collection;
import java.util.List;
import java.util.Random;

public class PagerCardDataPOJO implements ECCardData<CommentPOJO> {

    private String headTitle;

    private BitmapDrawable headBgImageDrawable;
    private BitmapDrawable mainBgImageDrawable;

    private Integer headBgImageDrawableResource;
    private Integer mainBgImageDrawableResource;

    private Drawable personPicture;
    private String personName;
    private String personMessage;
    private int personViewsCount;
    private int personCommentsCount;
    private int personLikesCount;
    private List<CommentPOJO> comments;

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
    public BitmapDrawable getHeadBgImageDrawable() {
        return headBgImageDrawable;
    }

    public void setHeadBgImageDrawable(BitmapDrawable headBgImageDrawable) {
        this.headBgImageDrawable = headBgImageDrawable;
    }

    @Override
    public BitmapDrawable getMainBgImageDrawable() {
        return mainBgImageDrawable;
    }

    public void setMainBgImageDrawable(BitmapDrawable mainBgImageDrawable) {
        this.mainBgImageDrawable = mainBgImageDrawable;
    }

    @Override
    public Integer getHeadBgImageDrawableResource() {
        return headBgImageDrawableResource;
    }

    public void setHeadBgImageDrawableResource(Integer headBgImageDrawableResource) {
        this.headBgImageDrawableResource = headBgImageDrawableResource;
    }

    @Override
    public Integer getMainBgImageDrawableResource() {
        return mainBgImageDrawableResource;
    }

    public void setMainBgImageDrawableResource(Integer mainBgImageDrawableResource) {
        this.mainBgImageDrawableResource = mainBgImageDrawableResource;
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

    @Override
    public List<CommentPOJO> getComments() {
        return comments;
    }

    public void setComments(List<CommentPOJO> comments) {
        this.comments = comments;
    }
}
