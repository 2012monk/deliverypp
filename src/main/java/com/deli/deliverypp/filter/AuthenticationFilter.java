package com.deli.deliverypp.filter;


import com.deli.deliverypp.auth.AuthProvider;
import com.deli.deliverypp.auth.jwt.JwtTokenProvider;
import com.deli.deliverypp.service.AuthService;
import com.deli.deliverypp.util.ControlUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebFilter(filterName = "auth filter" , value = "/*")
public class AuthenticationFilter implements Filter {

//    private static final AuthProvider provider = new AuthProvider();
    private static final AuthService service = new AuthService();
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        // html, js, css 파일 통과

        HttpServletRequest rq = (HttpServletRequest) servletRequest;

        String reqUri = ControlUtil.getRequestUri(rq, 0);


        Pattern p = Pattern.compile(".html$|.js$|.css$");
        Matcher matcher = p.matcher(reqUri);

        String token = rq.getHeader("Authentication");

        if (service.getAuthentication(token)){
            filterChain.doFilter(servletRequest, servletResponse);
        }

        else if (matcher.find()) {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else {
            filterChain.doFilter(servletRequest, servletResponse);
//            rq.getRequestDispatcher("/login.html").forward(servletRequest, servletResponse);
        }

    }


}
