package com.deli.deliverypp.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Reply {

    private String reviewId;
    private String replyId;
    private String replyContent;
    private String userEmail;
    private String replyDate;

    public Reply(String reviewId, String replyId, String replyContent, String userEmail, String replyDate) {
        this.reviewId = reviewId;
        this.replyId = replyId;
        this.replyContent = replyContent;
        this.userEmail = userEmail;
        this.replyDate = replyDate;
    }

    public Reply() {
    }

    public void generateReplyId() {
        this.replyId = UUID.randomUUID().toString();
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(String replyDate) {
        SimpleDateFormat fmt = new SimpleDateFormat("yy-MM-dd hh-mm");
        this.replyDate = fmt.format(new Date());
    }

}
