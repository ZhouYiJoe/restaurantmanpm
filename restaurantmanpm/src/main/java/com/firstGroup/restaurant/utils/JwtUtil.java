package com.firstGroup.restaurant.utils;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.UUID;

public class JwtUtil {
    public static final int EXP_TIME = 1000 * 60 * 60 * 24;
    public static final String SECRET_KEY = "fmf4+3D&LP@$rVFGEuKrr*8mY-%umxG7";

    public static String generateToken(String userId) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .claim("userId", userId)
                .setExpiration(new Date(System.currentTimeMillis() + EXP_TIME))
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static String getUserId(String token) {
        if (token == null) {
            return null;
        }
        try {
            JwtParser parser = Jwts.parser();
            parser.setSigningKey(SECRET_KEY);
            Jws<Claims> jwt = parser.parseClaimsJws(token);
            Claims body = jwt.getBody();
            return body.get("userId", String.class);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean checkToken(String token) {
        return getUserId(token) != null;
    }
}