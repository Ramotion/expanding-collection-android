package com.ramotion.expandingcollection.examples.simple;

import android.graphics.drawable.Drawable;

public class CommentPOJO {
    private Drawable commentPersonPicture;
    private String commentPersonName;
    private String commentText;
    private String commentDate;

    public CommentPOJO(Drawable commentPersonPicture, String commentPersonName, String commentText, String commentDate) {
        this.commentPersonPicture = commentPersonPicture;
        this.commentPersonName = commentPersonName;
        this.commentText = commentText;
        this.commentDate = commentDate;
    }

    public Drawable getCommentPersonPicture() {
        return commentPersonPicture;
    }

    public void setCommentPersonPicture(Drawable commentPersonPicture) {
        this.commentPersonPicture = commentPersonPicture;
    }

    public String getCommentPersonName() {
        return commentPersonName;
    }

    public void setCommentPersonName(String commentPersonName) {
        this.commentPersonName = commentPersonName;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }
}
