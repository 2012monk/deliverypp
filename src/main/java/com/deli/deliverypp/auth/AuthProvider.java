package com.deli.deliverypp.auth;

import com.deli.deliverypp.DB.DeliUser;
import com.deli.deliverypp.auth.jwt.JwtTokenProvider;
import com.deli.deliverypp.service.UserLoginService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class AuthProvider {

    private static final JwtTokenProvider provider = new JwtTokenProvider();
    private static final UserLoginService service = new UserLoginService();

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

    public boolean checkUserStatusValid(HttpServletRequest request) {
        String token = getToken(request.getHeader("Authorization"));
        if (token == null) return false;

        if (!provider.validateToken(token)) return false;

        Cookie[] cookies = request.getCookies();
        Cookie sid = Arrays.stream(cookies).filter(c -> c.getName().equals("SID")).collect(Collectors.toList()).get(0);

        if (sid == null) return false;

        return provider.validateRefreshToken(sid.getValue());
    }



    public String getToken (String header) {
        if (header == null) return null;
        try {
            String type = header.split(" ")[0];
            String token = header.split(" ")[1];
            if (!type.equals("Bearer")) {
                return null;
            }
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
