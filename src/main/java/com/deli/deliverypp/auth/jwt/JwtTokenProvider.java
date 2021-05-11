package com.deli.deliverypp.auth.jwt;

import com.deli.deliverypp.DB.DeliUser;
import com.deli.deliverypp.util.annotaions.KeyLoad;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

public class JwtTokenProvider {


    private static String privateKey;
    @KeyLoad(path = "jwt.key.pem")
    private static SecretKey secretKey;
    private static final long tokenValidateTime = 1000L * 60 * 60; // 1 hour
    private static final long refreshTokenValidateTime = 1000L * 60 * 60 * 24 * 7; // 7days
    private final Logger log = LogManager.getLogger(JwtTokenProvider.class);

    static {
        try {
            Class.forName("com.deli.deliverypp.util.ConfigLoader");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    public Jws<Claims> getClaims(String jws){
        try {
            return Jwts
                    .parserBuilder()
                    .requireIssuer("https://deli.alconn.co")
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(jws);
        }catch (SignatureException signatureException){
            log.warn(signatureException.getMessage());
        }catch (ExpiredJwtException expiredJwtException) {
            log.warn("token expired");
        }
        return null;
    }

    public Map<String ,Object> getTokenBody(String jws) {
        Jws<Claims> claimsJws = getClaims(jws);
        return claimsJws.getBody();
    }

    public Map<String, Object> getRefreshTokenBody(String jws) {
        return getClaimsFromRefresh(jws).getBody();
    }


    // Claims
    // 1. user role
    // 2. user type
    // 3. issuer

    /*
    Header
    1. auth_type Bearer
     */

    // TODO jackson deserializer
    public String generateToken (String userEmail, String userRole, String userType) {
        Date date = new Date();
        Date exp = new Date(date.getTime() + tokenValidateTime);
        log.info(date.getTime());
        log.info(date.getTime() + tokenValidateTime);
        log.info(exp.getTime());
        return Jwts.builder()
                .setId(userEmail)
                .setExpiration(exp)
                .setIssuedAt(date)
                .setIssuer("https://deli.alconn.co")
                .claim("userEmail", userEmail)
                .claim("userType", userType)
                .claim("userRole", userRole)
                .setAudience(userEmail)
                .signWith(secretKey)
                .compact();
    }


    public String generateRefreshToken (DeliUser user) {
        Date date = new Date();
        Date exp = new Date(date.getTime() + refreshTokenValidateTime);

        return Jwts
                .builder()
                .setSubject("refresh")
                .setId(user.getUserEmail())
                .setIssuer("https://deli.alconn.co")
                .setIssuedAt(date)
                .setExpiration(exp)
                .setAudience(user.getUserEmail())
                .signWith(secretKey)
                .compact();

    }


    public Jws<Claims> getClaimsFromRefresh (String jws) {
        try {
            return Jwts
                    .parserBuilder()
                    .requireSubject("refresh")
                    .requireIssuer("https://deli.alconn.co")
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(jws);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    public boolean validateToken (String jws) {
        try{
            validateToken(jws);
            return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean validateRefreshToken (String jws) {
        try {
            getClaimsFromRefresh(jws);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }





}
