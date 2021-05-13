package com.deli.deliverypp.controller.uploader;

import com.deli.deliverypp.model.ResponseMessage;
import com.deli.deliverypp.util.ControlUtil;
import com.deli.deliverypp.util.MessageGenerator;
import com.deli.deliverypp.util.annotaions.LoadConfig;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileExistsException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "UploadController", value = "/upload")
//@LoadConfig()
public class UploadController extends HttpServlet {

    private static final String  CHAR_SET = "UTF-8";
    private static String IMAGE_PATH = "static/image";
    private static final long MAX_FILE_SIZE = 1024 * 1024 * 15;
    private static String PATH;
    private final Logger log = LogManager.getLogger(UploadController.class);



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }



    // image uploader
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (ServletFileUpload.isMultipartContent(request)) {
            String realPath = getServletContext().getRealPath(IMAGE_PATH);

            File t= new File(realPath);
            if (!t.exists()) {
                System.out.println(t.mkdirs());
//                realPath = "./"+IMAGE_PATH;
            }
//            Map<String ,String >

            System.out.println(realPath);
            DiskFileItemFactory factory = new DiskFileItemFactory();

            factory.setSizeThreshold(0);
            factory.setRepository(new File(realPath));

            ServletFileUpload fileUpload = new ServletFileUpload(factory);
            fileUpload.setFileSizeMax(MAX_FILE_SIZE);
            fileUpload.setSizeMax(MAX_FILE_SIZE);



            File dir = new File(realPath);
            if (!dir.exists()) {
                System.out.println(dir.mkdir());
            }

            try {
                ResponseMessage<?> msg = new ResponseMessage<>();
                Map<String, String> data = new HashMap<>();

                List<FileItem> itemList = fileUpload.parseRequest(request);

                if (itemList != null && !itemList.isEmpty()) {
                    for (FileItem f: itemList) {
                        System.out.println(f.getName());
                        if (!f.isFormField()) {
                            String fileName = new File(f.getName()).getName();

                            log.info(fileName);

                            File storeFile = new File(realPath+"/"+fileName);
                            try {
                                f.write(storeFile);
                                data.put(fileName, "upload success");
                            } catch (FileExistsException e) {
                                data.put(fileName, "file exist");
                                // TODO 파일 존재 메시지!
                                e.printStackTrace();
                            }
                        }
                    }
                    ControlUtil.sendResponseData(response, MessageGenerator.makeMsg("success", data));
                }
            } catch (Exception e) {
                ControlUtil.sendResponseData(response, MessageGenerator.makeErrorMsg("uploadFailed", "131"));
                e.printStackTrace();
            }
        }

        else {
            ControlUtil.sendResponseData(response, MessageGenerator.makeErrorMsg("enctype mismatched", "113"));
            response.setStatus(400);
        }
    }


}
