package com.contract.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.contract.domain.Admin;
import com.contract.domain.Share;


import java.util.Date;

public class TokenUtil {

    private static final String password = "LiCHOLjq40546697";

    public static String getAppToken(Admin admin) throws Exception { //time秒
        Date start = new Date();
        String token = "";
        token = JWT.create().withAudience(admin.getAdminId()).withIssuedAt(start).withSubject("App")
                .sign(Algorithm.HMAC256(admin.getAdminPassword()));
        return AESUtil.encrypt(password,token);
    }


    public static String getAdminToken(Admin admin, int time) throws Exception { //time秒
        Date start = new Date();
        long currentTime = System.currentTimeMillis() + time * 1000;//一小时有效时间
        Date end = new Date(currentTime);
        String token = "";
        token = JWT.create().withAudience(admin.getAdminId()).withIssuedAt(start).withExpiresAt(end).withSubject("Admin")
                .sign(Algorithm.HMAC256(admin.getAdminPassword()));
        return AESUtil.encrypt(password,token);
    }

    public static String getShareToken(Share share, int time) throws Exception { //time秒
        Date start = new Date();
        long currentTime = System.currentTimeMillis() + time * 1000;//一小时有效时间
        Date end = new Date(currentTime);
        String token = "";
        token = JWT.create().withAudience(share.getShareId()).withIssuedAt(start).withExpiresAt(end).withSubject("Share")
                .sign(Algorithm.HMAC256(share.getSharePassword()));
        return AESUtil.encrypt(password,token);
    }


    public static String getSubject(String AESToken) throws Exception {
        String token = AESUtil.decrypt(password,AESToken);
        try {
            return JWT.decode(token).getSubject();
        } catch (JWTDecodeException j) {
            return null;
        }
    }


    public static String getId(String AESToken) throws Exception {
        String token = AESUtil.decrypt(password,AESToken);
        try {
            return JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            return null;
        }
    }

    public static boolean verifyPassword(String AESToken, String adminPassword) throws Exception {
        String token = AESUtil.decrypt(password,AESToken);
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(adminPassword)).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            return false;
        }
        return true;
    }



}
