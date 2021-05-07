package com.deli.deliverypp.auth.jwt;

import com.deli.deliverypp.DB.DeliUser;
import com.deli.deliverypp.util.KeyLoad;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

public class JwtTokenProvider {


    private static String privateKey;
    @KeyLoad(path = "jwt.key.pem")
    private static SecretKey secretKey;
    private static final long tokenValidateTime = 1000L * 60 * 60;
    private final Logger log = LogManager.getLogger(JwtTokenProvider.class);

    static {
        try {
            Class.forName("com.deli.deliverypp.util.ConfigLoader");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    public Jws<Claims> validateToken(String jws){
        try {
            return Jwts
                    .parserBuilder()
                    .requireIssuer("http://deli.alconn.co")
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
        Jws<Claims> claimsJws = validateToken(jws);
        return claimsJws.getBody();
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
                .setIssuer("http://deli.alconn.co")
                .claim("userEmail", userEmail)
                .claim("userType", userType)
                .claim("userRole", userRole)
                .setAudience(userEmail)
                .signWith(secretKey)
                .compact();
    }



}
