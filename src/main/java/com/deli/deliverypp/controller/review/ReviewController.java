package com.deli.deliverypp.controller.review;

import com.deli.deliverypp.DB.DeliUser;
import com.deli.deliverypp.model.ResponseMessage;
import com.deli.deliverypp.model.Review;
import com.deli.deliverypp.service.ReviewService;
import com.deli.deliverypp.util.ControlUtil;
import com.deli.deliverypp.util.annotaions.PathParam;
import com.deli.deliverypp.util.annotaions.ProtectedResource;
import com.deli.deliverypp.util.annotaions.RequiredModel;
import com.deli.deliverypp.util.annotaions.RequiredParam;
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
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        ControlUtil.sendResponseData(resp, service.updateReview(ControlUtil.getJson(req)));
    }



//    @ProtectedResource(uri = "/review", id = true, method = "delete")
//    @RequiredParam(value = "/review/{reviewId}")
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp){
        System.out.println(ControlUtil.getRequestUri(req, 2));
        log.info(req.getRequestURI());
        ControlUtil.responseMsg(resp, service.deleteReview(ControlUtil.getRequestUri(req)));
    }



    @RequiredParam(value = "/review/{reviewId}")
    @ProtectedResource(uri = "/review", id = true, method = "delete")
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
