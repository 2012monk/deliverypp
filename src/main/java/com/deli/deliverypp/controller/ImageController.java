package com.deli.deliverypp.controller;

import com.oreilly.servlet.MultipartRequest;
import org.apache.commons.fileupload.FileUpload;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ImageController", value = "/ImageController")
public class ImageController extends HttpServlet {


    private static MultipartRequest multipartRequest;
    private static final FileUpload upLoader = new FileUpload();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
