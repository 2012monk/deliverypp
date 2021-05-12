package com.deli.deliverypp.service;

import com.deli.deliverypp.DB.ReplyAccess;
import com.deli.deliverypp.DB.ReviewAccess;
import com.deli.deliverypp.model.Reply;
import com.deli.deliverypp.model.ResponseMessage;
import com.deli.deliverypp.model.Review;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

import static com.deli.deliverypp.util.JSONUtil.getMapper;

public class ReviewService {

    private static final ReviewAccess access = new ReviewAccess();
    private static final ObjectMapper mapper = getMapper();
    private static final ReplyAccess replyAccess = new ReplyAccess();

    public boolean insertNewReview (String json) {
        return false;
    }

    public ResponseMessage<Review> insertNewReview (String json, boolean objReturn) {
        Review review = null;
        try {
            review = mapper.readValue(json, Review.class);
            review.generateReviewId();
            review = access.insertReview(review);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return makeMsg("insert Success", review);
    }

    public boolean deleteReview (String reviewId) {
        return access.deleteReview(reviewId);
    }

    public ResponseMessage<Review> updateReview (String json) {
        Review review = null;
        try {
            review = access.updateReview(mapper.readValue(json, Review.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return makeMsg(review);
    }

    public ResponseMessage<Review> getReviewById (String reviewId) {
        Review review = access.getReviewByKey("reviewId", reviewId);
        try {
            review.setReplyList(replyAccess.getRepliesBiReview(reviewId));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return makeMsg(review);
    }

    public ResponseMessage<List<Review>> getReviewsByStore (String storeId) {
        List<Review> list = access.getReviewsByKey("storeId", storeId);
        for (Review r: list) {
            try {
                List<Reply> replies = replyAccess.getRepliesBiReview(r.getReviewId());
                r.setReplyList(replies);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return makeMsg(list);
    }

    public ResponseMessage<List<Review>> getReviewsByWriter (String writer) {
        List<Review> list = access.getReviewsByKey("userEmail", writer);
        for (Review r: list) {
            try {
                List<Reply> replies = replyAccess.getRepliesBiReview(r.getReviewId());
                r.setReplyList(replies);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return makeMsg(list);
    }


    // fill me
    public ResponseMessage<Review> getReviewsByOptions(Map<String, String[]> parameterMap){
        return null;
    }

    public <T> ResponseMessage<T> makeMsg(String msg,T data, String code) {
        return new ResponseMessage<T>(msg, data, code);
    }

    public <T> ResponseMessage<T> makeMsg(String msg,T data){
        if (data == null) {
            return makeMsg(null);
        }
        return new ResponseMessage<T>(msg, data, null);
    }

    public <T> ResponseMessage<T> makeMsg(T data) {
        return new ResponseMessage<T>(data == null ? "failed" : "success", data);
    }



}
