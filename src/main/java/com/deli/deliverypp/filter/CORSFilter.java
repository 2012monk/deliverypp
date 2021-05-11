package com.deli.deliverypp.filter;

//import org.apache.log4j.Logger;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.core.Logger;

import com.deli.deliverypp.auth.jwt.JwtTokenProvider;
import com.deli.deliverypp.util.ConfigLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;


@WebFilter(
        filterName = "CORSFilter",
        value = "/*",
        initParams = {
        @WebInitParam(name ="encoding", value="UTF-8")
    })
public class CORSFilter implements Filter {
    private final Logger log = LogManager.getLogger(CORSFilter.class);
//    private static final String url = "http://localhost:47788";

    private static final String url = "http://112.169.196.76:47788";
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        try {
            Class.forName("com.deli.deliverypp.util.ConfigLoader");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    // TODO CORS issue
    // TODO Access-Control-Allow-Origin origin source 확인
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest rq = (HttpServletRequest) servletRequest;
        HttpServletResponse rs = (HttpServletResponse) servletResponse;
        rq.setCharacterEncoding("UTF-8");
        rs.setCharacterEncoding("UTF-8");
//        rq.getServletContext().setResponseCharacterEncoding("UTF-8");
//        rq.getServletContext().setResponseCharacterEncoding("UTF-8");

        log.info(rq.getRemoteAddr()+"   port :   " + rq.getRemotePort());

        String local = "http://localhost:47788";
        String optionRes = "OPTIONS, GET, POST, HEAD, DELETE, PUT";
        // TODO options header request intercepted after the filter fix me
//        log.info(rq.getMethod());
        if (rq.getMethod().equals("OPTIONS")) {
            log.info(rq.getMethod());
            rs.setHeader("Allow", optionRes);
            rs.setHeader("Access-Control-Allow-Origin", local);
            rs.setHeader("Access-Control-Allow-Methods", optionRes);
            rs.setStatus(200);
            rs.getWriter().close();
        }


        else if (rq.getHeader("origin") != null) {
            Enumeration<String> e = rq.getHeaderNames();
            while (e.hasMoreElements()){
                String s = e.nextElement();
//                log.info(s+"  :  "+rq.getHeader(s));
            }

            rs.setHeader("Access-Control-Allow-Credentials", "true");
//            rs.setHeader("Access-Control-Allow-Origin", local);
            rs.setHeader("Access-Control-Allow-Origin", "*");
            rs.setHeader("Access-Control-Allow-Methods", optionRes);
            rs.setHeader("Access-Control-Allow-Headers", "Origin, Content-Type, Authorization, Accept, X-Requested-With, remember-me,");
            rs.setHeader("Access-Control-Max-Age", "3600");
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
            filterChain.doFilter(servletRequest, servletResponse);

        }
    }

    @Override
    public void destroy() {
        log.info("destroyed");
    }
}
