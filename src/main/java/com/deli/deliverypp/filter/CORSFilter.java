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
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@WebFilter(
        filterName = "CORSFilter",
        value = "/*",
        initParams = {
        @WebInitParam(name ="encoding", value="UTF-8")
    })
public class CORSFilter extends AuthenticationFilter {
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

        log.info("cors ");
//        rq.getServletContext().setResponseCharacterEncoding("UTF-8");
//        rq.getServletContext().setResponseCharacterEncoding("UTF-8");

        log.info(rq.getRemoteAddr()+"   port :   " + rq.getRemotePort());
        yellHeader(rq);

        if (rq.getHeader("Authorization") != null) {
            log.info(rq.getHeader("Authorization"));
        }
        try {
            log.info(rq.getHeader("Authorization"));
        } catch (Exception e) {

        }

        String local = "http://localhost:47788";
        String optionRes = "OPTIONS, GET, POST, HEAD, DELETE, PUT";
        String headers = "Origin, Content-Type, Authorization, Accept, X-Requested-With, remember-me,";
        // TODO options headers request intercepted after the filter fix me
//        log.info(rq.getMethod());
        if (rq.getMethod().equals("OPTIONS")) {
            log.info(rq.getMethod());
            String origin = rq.getHeader("origin");
            rs.setHeader("Allow", optionRes);
            rs.setHeader("Access-Control-Allow-Credentials", "true");
            rs.setHeader("Access-Control-Allow-Origin", allowedHosts(origin));
            rs.setHeader("Access-Control-Allow-Methods", optionRes);
            rs.setHeader("Access-Control-Allow-Headers", headers);
            rs.setStatus(200);
            rs.getWriter().close();
        }


        else if (rq.getHeader("origin") != null) {
            String origin = rq.getHeader("origin");
            log.info(origin);

            rs.setHeader("Access-Control-Allow-Credentials", "true");
//            rs.setHeader("Access-Control-Allow-Origin", local);
            rs.setHeader("Access-Control-Allow-Origin", allowedHosts(origin));
            rs.setHeader("Access-Control-Allow-Methods", optionRes);
            rs.setHeader("Access-Control-Allow-Headers", headers);
            rs.setHeader("Access-Control-Max-Age", "3600");
//            filterChain.doFilter(servletRequest, servletResponse);
            super.doFilter(servletRequest, servletResponse, filterChain);
        }else{
            super.doFilter(servletRequest, servletResponse, filterChain);
//            filterChain.doFilter(servletRequest, servletResponse);

        }
    }

    @Override
    public void destroy() {
        log.info("destroyed");
    }

    public void yellHeader(HttpServletRequest rq) {
        StringBuilder sb = new StringBuilder();
        Enumeration<String> e = rq.getHeaderNames();
        sb.append("\n");
        sb.append(rq.getMethod()).append(" ").append(rq.getRequestURI()).append("\n");
        while (e.hasMoreElements()){
            String s = e.nextElement();
            sb.append(s).append(": ").append(rq.getHeader(s)).append("\n");
        }

        log.info(sb.toString());
    }


    private String allowedHosts(String domain) {
        Pattern pattern = Pattern.compile("http://localhost:47788|http://127.0.0.1:5500|http://127.0.0.1:47788|https://deli.alconn.co|http://deli.alconn.co");
        Matcher matcher = pattern.matcher(domain);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }


}
