package com.deli.deliverypp.util;

//import org.apache.log4j.Logger;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.core.Logger;

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

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest rq = (HttpServletRequest) servletRequest;
        HttpServletResponse rs = (HttpServletResponse) servletResponse;
        rq.setCharacterEncoding("UTF-8");
        rs.setCharacterEncoding("UTF-8");

        String optionRes = "OPTIONS, GET, POST, HEAD";
        if (rq.getMethod().equals("OPTION")) {
            rs.setHeader("Allow", optionRes);
        }


        if (rq.getHeader("origin") != null) {
            Enumeration<String> e = rq.getHeaderNames();
            while (e.hasMoreElements()){
                String s = e.nextElement();
//                log.debug(s+"  :  "+rq.getHeader(s));
            }

            rs.setHeader("Access-Control-Allow-Credentials", "true");
            rs.setHeader("Access-Control-Allow-Origin", "*");
            rs.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
            rs.setHeader("Access-Control-Max-Age", "3600");
            rs.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Accept, X-Requested-With, remember-me");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        log.info("destroyed");
    }
}
