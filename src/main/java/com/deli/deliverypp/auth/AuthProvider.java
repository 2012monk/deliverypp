package com.deli.deliverypp.auth;

import com.deli.deliverypp.DB.DeliUser;
import com.deli.deliverypp.auth.jwt.JwtTokenProvider;
import com.deli.deliverypp.service.UserLoginService;

import java.util.Map;

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
        return provider.validateRefreshToken(token) != null;
    }



}
