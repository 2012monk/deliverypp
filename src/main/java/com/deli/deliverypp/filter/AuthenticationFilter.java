package com.deli.deliverypp.filter;


import com.deli.deliverypp.DB.DeliUser;
import com.deli.deliverypp.auth.AuthProvider;
import com.deli.deliverypp.service.UserLoginService;
import com.deli.deliverypp.util.ControlUtil;
import com.deli.deliverypp.util.MappingLoader;
import com.deli.deliverypp.util.annotaions.ProtectedResource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.deli.deliverypp.util.JSONUtil.getMapper;

@WebFilter(filterName = "auth filter" , value = "/*")
public class AuthenticationFilter implements Filter {

//    private static final AuthProvider provider = new AuthProvider();
    private static final UserLoginService service = new UserLoginService();
    private static final Map<String, DeliUser.UserRole> resources = MappingLoader.protectedUriProperties;
    private static final Map<String , ProtectedResource> check = MappingLoader.resources;
    private static final Map<String , Class<?>> requiredModel = MappingLoader.requiredModel;
//    private static final Map<String , Requ>
    private static final AuthProvider provider = new AuthProvider();
    private final Logger log = LogManager.getLogger(AuthenticationFilter.class);
    private static final ObjectMapper mapper = getMapper();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        if (resources != null) {
            resources.keySet().forEach(System.out::println);
        }

        if (check != null){
            check.keySet().forEach(System.out::println);
        }

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        // html, js, css 파일 통과

        HttpServletRequest rq = (HttpServletRequest) servletRequest;

        String reqUri = ControlUtil.getRequestUri(rq, 0);
        String fullUri = rq.getRequestURI();

        Pattern p = Pattern.compile(".html$|.js$|.css$");
        Matcher matcher = p.matcher(reqUri);


        log.info(fullUri);
        // check authority
        // check refresh token
        // TODO 요청이 2번씩 들어감 왜?

        // check if resource protected
        String reqKey = fullUri+" "+rq.getMethod().toLowerCase(Locale.ROOT);
        ProtectedResource resource = check.get(reqKey);
        String refreshToken = getRefreshToken(rq);
        log.info(reqKey);
        log.info(resource);
        if (resource != null) {
            boolean isPassed = false;


            log.info("auth required");


            boolean checkId = resource.id();
            String token = getToken(rq);

            try {
                DeliUser user = service.parseUserFromToken(token);
                if (checkId) {

                }
            } catch (Exception e) {

            }
            // check token is present
            // check refresh token
//            if (token != null && provider.checkRefreshToken(
//                    getRefreshToken(rq))) {
//                // check authority
//                if (provider.checkAuthority(token,
//                        (DeliUser.UserRole) resources.get(fullUri)
//                )){
//                    filterChain.doFilter(servletRequest, servletResponse);
//                }
//                // redirect to unauthorized page
//            }else {
//                rq.getRequestDispatcher("/unauthorized.html").forward(servletRequest,servletResponse);
//            }
        }
        filterChain.doFilter(servletRequest, servletResponse);



    }


    private String getToken(HttpServletRequest request) throws NullPointerException{
        String header = request.getHeader("Authorization");

        if (header != null) {
            log.info(header);
            String scheme = header.split(" ")[0];
            String token = header.split(" ")[1];
            if (scheme.equals("Bearer")) {
                return token;
            }
        }
        return null;
    }

    private String getRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        try {
            Cookie cookie = Arrays.stream(cookies)
                    .filter(c -> c.getName().equals("SID"))
                    .collect(Collectors.toList()).get(0);

            if (cookie != null) {
                return cookie.getValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    // identification ; userEmail
    // key to check : String Field name
    // where to check : Class<?>
    // if Class id doesn't exist throw illegalArgumentException
    // if

    private <T> boolean checkAuthority(String json, DeliUser user, Class<T> target) {
        try {
            T m = mapper.readValue(json, target);
            String name = (String) m.getClass().getDeclaredField("userEmail").get(m);

            if (name != null) {
                return name.equals(user.getUserEmail());
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }



}
