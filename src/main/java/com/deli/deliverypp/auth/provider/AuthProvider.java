package com.deli.deliverypp.auth.provider;

import com.deli.deliverypp.DB.DeliUser;
import com.deli.deliverypp.auth.jwt.JwtTokenProvider;
import com.deli.deliverypp.service.UserLoginService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class AuthProvider {

    private static final JwtTokenProvider provider = new JwtTokenProvider();
    private static final UserLoginService service = new UserLoginService();
    private final Logger log = LogManager.getLogger(AuthProvider.class);

    public DeliUser.UserRole checkAuthority(String token) {
        return service.parseUserFromToken(token).getUserRole();
    }

    public boolean checkAuthority(String token, DeliUser.UserRole role) {
        DeliUser user = service.parseUserFromToken(token);
        if (user != null) {
            return role.isHigher(user.getUserRole());
        }
        return false;
    }

    public boolean checkRefreshToken (String token) {
        return provider.validateRefreshToken(token);
    }

    public String parseHeader (HttpServletRequest request) {
        String header = request.getHeader("Authorization");

        if (header != null) {
            String scheme = header.split(" ")[0];
            String token = header.split(" ")[1];
            if (scheme.equals("Bearer")) {
                return token;
            }
        }

//        Cookie[] cookies = request.getCookies();
//        Cookie sid = null;
//        try {
//            sid = Arrays.stream(cookies).filter(c -> c.getName().equals("SID")).collect(Collectors.toList()).get(0);
//            return sid.getValue();
//        } catch (Exception e) {
//            log.debug("refresh token xx");
////            e.printStackTrace();
//        }
        return null;
    }

    // check only access token
    public boolean checkUserStatusValid(HttpServletRequest request) {
        String token = parseHeader(request);
//        String token = getToken(request.getHeader("Authorization"));
//        if (token == null) return false;
//
//        if (!provider.validateToken(token)) return false;


//        Cookie[] cookies = request.getCookies();
//        Cookie sid = null;
//        try {
//            sid = Arrays.stream(cookies).filter(c -> c.getName().equals("SID")).collect(Collectors.toList()).get(0);
//        } catch (Exception e) {
//            log.debug("refresh token xx");
////            e.printStackTrace();
//        }
//
//        if (sid == null) return false;
        if (token == null) return false;
        try {
//            return provider.validateToken(sid.getValue());
            return provider.validateToken(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }
//    public boolean checkUserStatusValid(HttpServletRequest request) {return true;}

//
//
//    public String getToken (String header) {
//        if (header == null) return null;
//        try {
//            log.info(header);
//            String type = header.split(" ")[0];
//            String token = header.split(" ")[1];
//            if (!type.equals("Bearer")) {
//                return null;
//            }
//            return token;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

//    public String getTokenFromHeader (HttpServletRequest request) {
//        return parseHeader(request);
//        String header = parseHeader(request);
//        return getToken(header);
//    }

    /**
     *
     * @param userEmail email from request
     * @param request request obj
     * @return true if passed
     */
    public boolean checkId(String userEmail, HttpServletRequest request) {

        try {
            String token = parseHeader(request);
            log.info(token);
            String email = service.parseUserFromToken(token).getUserEmail();
            return userEmail.equals(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
//    public boolean checkId(String userEmail, HttpServletRequest request) {
//       return true;
//    }


    public DeliUser getUserFromHeader (HttpServletRequest request) {
        try {
            String token = parseHeader(request);
//            System.out.println(token);
            log.debug(token);
            return service.parseUserFromToken(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getUserEmailFromHeader (HttpServletRequest request) {
        DeliUser user = getUserFromHeader(request);
        if (user != null) {
            try {
                return user.getUserEmail();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }





}
