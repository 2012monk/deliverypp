package com.deli.deliverypp.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Review {

    private String storeId;
    private String reviewId;
    private int reviewRating;
    private String reviewImage;
    private String userEmail;
    private String reviewContent;
    private String reviewDate;

    public Review() {
    }

    public Review(String storeId, String reviewId, int reviewRating, String reviewImage, String userEmail, String reviewContent, String reviewDate) {
        this.storeId = storeId;
        this.reviewId = reviewId;
        this.reviewRating = reviewRating;
        this.reviewImage = reviewImage;
        this.userEmail = userEmail;
        this.reviewContent = reviewContent;
        this.reviewDate = reviewDate;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public int getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(int reviewRating) {
        this.reviewRating = reviewRating;
    }

    public String getReviewImage() {
        return reviewImage;
    }

    public void setReviewImage(String reviewImage) {
        this.reviewImage = reviewImage;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        SimpleDateFormat fmt = new SimpleDateFormat("yy-MM-dd hh-mm");
        this.reviewDate = fmt.format(new Date());
    }
}
