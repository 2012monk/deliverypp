package com.deli.deliverypp.controller.review;

import com.deli.deliverypp.DB.DeliUser;
import com.deli.deliverypp.DB.ReplyAccess;
import com.deli.deliverypp.auth.AuthProvider;
import com.deli.deliverypp.model.Reply;
import com.deli.deliverypp.model.ResponseMessage;
import com.deli.deliverypp.util.ControlUtil;
import com.deli.deliverypp.util.MessageGenerator;
import com.deli.deliverypp.util.annotaions.ProtectedResource;
import com.deli.deliverypp.util.annotaions.RequiredModel;
import com.deli.deliverypp.auth.AuthorityChecker;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    private final AuthProvider provider = new AuthProvider();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResponseMessage<?> msg = new ResponseMessage<>();
        switch (ControlUtil.getRequestUri(request)) {
            case "review-id":
                List<Reply> list = access.getRepliesBiReview(ControlUtil.getRequestUri(request, 2));
                ControlUtil.sendResponseData(response, MessageGenerator.makeResultMsg(list));
                break;
            case "reply-id":
                Reply reply = access.getReplyById(ControlUtil.getRequestUri(request, 2));
                ControlUtil.sendResponseData(response, MessageGenerator.makeResultMsg(reply));
                break;
            default:
                break;
        }
    }


    @ProtectedResource(uri = "/reply", role = DeliUser.UserRole.CLIENT)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Reply reply = getReply(ControlUtil.getJson(request));
        DeliUser user = provider.getUserFromHeader(request);
        if (reply != null) {
            reply.generateReplyId();
            if (reply.getUserEmail() == null) {
                reply.setUserEmail(user.getUserEmail());
            }
        }
        ControlUtil.sendResponseData(response,
                MessageGenerator.makeResultMsg(access.insertReply(reply)));
    }

    @RequiredModel(target = Reply.class)
    @ProtectedResource(uri = "/reply", id = true, method = "put")
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {

        String json = ControlUtil.getJson(req);
        if (AuthorityChecker.checkUserEmailFromJson(req, Reply.class, "replyId", json)){
            try {
                Reply reply = mapper.readValue(json, Reply.class);
                ControlUtil.sendResponseData(resp,
                        MessageGenerator.makeResultMsg(access.updateReply(reply)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            ControlUtil.sendUnAuthorizeMsg(resp);
        }

    }

    @RequiredModel(target = Reply.class)
    @ProtectedResource(uri = "/reply", id = true, method = "delete")
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String id = ControlUtil.getRequestUri(req);
        if (AuthorityChecker.checkUserEmail(req, Reply.class, "replyId", id)){
            ControlUtil.responseMsg(resp, access.deleteReply(id));
        }else {
            ControlUtil.sendUnAuthorizeMsg(resp);
        }

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
