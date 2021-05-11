package com.deli.deliverypp.controller.review;

import com.deli.deliverypp.model.ResponseMessage;
import com.deli.deliverypp.service.ReviewService;
import com.deli.deliverypp.util.ControlUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ReviewController", value = "/review/*")
public class ReviewController extends HttpServlet {



    private static final ReviewService service = new ReviewService();

    // option query string
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getReview(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ControlUtil.sendResponseData(response, service.insertNewReview(ControlUtil.getJson(request), true));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ControlUtil.sendResponseData(resp, service.updateReview(ControlUtil.getJson(req)));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ControlUtil.responseMsg(resp, service.deleteReview(ControlUtil.getRequestUri(req, 2)));
    }

    public void getReview (HttpServletRequest request, HttpServletResponse response) {
        String options = request.getQueryString();
        String target = ControlUtil.getRequestUri(request);
        String value = ControlUtil.getRequestUri(request, 2);
        ResponseMessage<?> msg = null;
        switch (target) {
            case "user":
                msg = service.getReviewsByWriter(value);
                break;
            case "store":
                msg = service.getReviewsByStore(value);
                break;
            default:
                msg = service.getReviewById(value);
                break;

        }
        ControlUtil.sendResponseData(response, msg);
    }



}
