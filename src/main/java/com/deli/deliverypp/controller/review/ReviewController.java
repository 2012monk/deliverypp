package com.deli.deliverypp.controller.review;

import com.deli.deliverypp.DB.DeliUser;
import com.deli.deliverypp.auth.AuthProvider;
import com.deli.deliverypp.model.ResponseMessage;
import com.deli.deliverypp.model.Review;
import com.deli.deliverypp.service.ReviewService;
import com.deli.deliverypp.util.ControlUtil;
import com.deli.deliverypp.util.MessageGenerator;
import com.deli.deliverypp.util.annotaions.ProtectedResource;
import com.deli.deliverypp.util.annotaions.RequiredModel;
import com.deli.deliverypp.util.annotaions.RequiredParam;
import com.deli.deliverypp.auth.AuthorityChecker;
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
    private static final AuthProvider provider = new AuthProvider();

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
        String json = ControlUtil.getJson(request);
        DeliUser user = provider.getUserFromHeader(request);

        log.info(json);
        log.info(user);
        if (user != null) {
            ControlUtil.sendResponseData(response, service.insertNewReview(json, user.getUserEmail()));
        }else {
            ControlUtil.sendResponseData(response, service.insertNewReview(json, null));
        }
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
            log.info(ControlUtil.getRequestUri(req));
            ControlUtil.responseMsg(resp, service.deleteReview(ControlUtil.getRequestUri(req)));
        }else{
            ControlUtil.sendResponseData(resp, MessageGenerator.makeErrorMsg("you don't have authority to access this resources \r\n 본인인증실패", "authority_error"));
            log.warn("failed to pass test");
        }
    }


    public void getReview (HttpServletRequest request, HttpServletResponse response) {
        String options = request.getQueryString();
        String target = ControlUtil.getRequestUri(request);
        String value = ControlUtil.getRequestUri(request, 2);
        ResponseMessage<?> msg = null;
        log.debug(value);
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
