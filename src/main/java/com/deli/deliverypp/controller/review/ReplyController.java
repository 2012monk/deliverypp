package com.deli.deliverypp.controller.review;

import com.deli.deliverypp.DB.ReplyAccess;
import com.deli.deliverypp.model.Reply;
import com.deli.deliverypp.model.ResponseMessage;
import com.deli.deliverypp.model.Review;
import com.deli.deliverypp.util.ControlUtil;
import com.deli.deliverypp.util.MessageGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.naming.ldap.Control;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

import static com.deli.deliverypp.util.JSONUtil.getMapper;

@WebServlet(name = "ReplyController", value = "/reply/*")
public class ReplyController extends HttpServlet {

    private static final ReplyAccess access = new ReplyAccess();
    private static final ObjectMapper mapper = getMapper();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResponseMessage<?> msg = new ResponseMessage<>();
        switch (ControlUtil.getRequestUri(request)) {
            case "review-id":
                List<Reply> list = access.getRepliesBiReview(ControlUtil.getRequestUri(request, 2));
                ControlUtil.sendResponseData(response, MessageGenerator.makeResultMsg(list));
                break;
            default:
                Reply reply = access.getReplyById(ControlUtil.getRequestUri(request, 2));
                ControlUtil.sendResponseData(response, MessageGenerator.makeResultMsg(reply));
                break;
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Reply reply = getReply(ControlUtil.getJson(request));
        if (reply != null) {
            reply.generateReplyId();
        }
        ControlUtil.sendResponseData(response,
                MessageGenerator.makeResultMsg(access.insertReply(reply)));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {

        Reply reply = getReply(ControlUtil.getJson(req));
        ControlUtil.sendResponseData(resp,
                MessageGenerator.makeResultMsg(access.updateReply(reply)));

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {

        ControlUtil.responseMsg(resp, access.deleteReply(ControlUtil.getRequestUri(req)));
    }

    private Reply getReply(String json) {
        Reply reply = null;
        try {
            return mapper.readValue(json, Reply.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
