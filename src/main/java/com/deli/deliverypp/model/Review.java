package com.deli.deliverypp.model;

import com.deli.deliverypp.util.PathSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Review {

    private String storeId;
    private String storeName;
    private String reviewId;
    private int reviewRating;
    @JsonSerialize(using = PathSerializer.class, as = String.class)
    private String reviewImage;
    private String userEmail;
    private String reviewContent;
    private String reviewDate;
    private List<Reply> replyList;

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

    public void generateReviewId() {
        this.reviewId = UUID.randomUUID().toString();
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
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

    public List<Reply> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<Reply> replyList) {
        this.replyList = replyList;
    }

    @Override
    public String toString() {
        return "Review{" +
                "storeId='" + storeId + '\'' +
                ", storeName='" + storeName + '\'' +
                ", reviewId='" + reviewId + '\'' +
                ", reviewRating=" + reviewRating +
                ", reviewImage='" + reviewImage + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", reviewContent='" + reviewContent + '\'' +
                ", reviewDate='" + reviewDate + '\'' +
                ", replyList=" + replyList +
                '}';
    }
}
