package com.deli.deliverypp.controller.review;

import com.deli.deliverypp.DB.DeliUser;
import com.deli.deliverypp.model.ResponseMessage;
import com.deli.deliverypp.model.Review;
import com.deli.deliverypp.service.ReviewService;
import com.deli.deliverypp.util.ControlUtil;
import com.deli.deliverypp.util.MessageGenerator;
import com.deli.deliverypp.util.annotaions.PathParam;
import com.deli.deliverypp.util.annotaions.ProtectedResource;
import com.deli.deliverypp.util.annotaions.RequiredModel;
import com.deli.deliverypp.util.annotaions.RequiredParam;
import com.deli.deliverypp.util.exp.AuthorityChecker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ReviewController", value = "/review/*")
public class ReviewController extends HttpServlet {



    private static final ReviewService service = new ReviewService();
    private static final Logger log = LogManager.getLogger(ReviewController.class);

    // option query string
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getReview(request, response);
    }

    @ProtectedResource(uri = "/review", role = DeliUser.UserRole.CLIENT, method = "post")
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ControlUtil.sendResponseData(response, service.insertNewReview(ControlUtil.getJson(request), true));
    }



    @RequiredModel(target = Review.class)
    @ProtectedResource(uri = "/review", id = true, method = "put")
    @RequiredParam(value = "json")
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        String json = ControlUtil.getJson(req);
        if (AuthorityChecker.checkUserEmailFromJson(req,Review.class,"reviewId", json)){
            ControlUtil.sendResponseData(resp, service.updateReview(json));
        }else {
            ControlUtil.sendResponseData(resp, MessageGenerator.makeErrorMsg("you don't have authority to access this resources \r\n 본인인증실패", "authority_error"));
            log.warn("failed to pass test");
        }
    }



    // TODO REFACTORING !!!!
    @ProtectedResource(uri = "/review", id = true, method = "delete")
    @RequiredParam(value = "/review/{reviewId}")
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp){
        String reviewId = ControlUtil.getRequestUri(req);
        if (AuthorityChecker.checkUserEmail(req,Review.class,"reviewId", reviewId)){
            log.warn("passed annotaions test");
            ControlUtil.responseMsg(resp, service.deleteReview(ControlUtil.getRequestUri(req)));
        }else{
            ControlUtil.sendResponseData(resp, MessageGenerator.makeErrorMsg("you don't have authority to access this resources \r\n 본인인증실패", "authority_error"));
            log.warn("failed to pass test");
        }
    }



//    @RequiredParam(value = "/review/{reviewId}")
//    @ProtectedResource(uri = "/review", id = true, method = "delete")
    public void delete(@PathParam(param = "reviewId") String reviewId) {
        System.out.println(reviewId);
    }


    public void getReview (HttpServletRequest request, HttpServletResponse response) {
        String options = request.getQueryString();
        String target = ControlUtil.getRequestUri(request);
        String value = ControlUtil.getRequestUri(request, 2);
        ResponseMessage<?> msg = null;
        System.out.println(value);
        switch (target) {
            case "user":
                msg = service.getReviewsByWriter(value);
                break;
            case "store":
                msg = service.getReviewsByStore(value);
                break;
            default:
                msg = service.getReviewById(target);
                break;

        }
        ControlUtil.sendResponseData(response, msg);
    }



}
