/*
 * Copyright (c) 2021. stockapp.
 * Proprietary source code; any copy or modification is prohibited.
 *
 * @author Moulaye Abderrahmane <moolsbytheway@gmail.com>
 *
 */

package com.stockapp.stockapp_backend.security;

import com.auth0.jwt.JWT;
import com.stockapp.stockapp_backend.enumeration.UserRole;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.stockapp.stockapp_backend.config.JacksonConfiguration.staticObjectMapper;

public class JwtProperties {
    public static final String SECRET =
            "R1oR2eDGibm7KZLiv24IJ";

    public static final int EXPIRATION_TIME = 3600000; // 1 hour
    public static final int EXPIRATION_TIME_REFRESH = 5400000; // 1 hour + 30 min
    public static final int EXPIRATION_TIME_REMEMBER_ME = 864000000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String APP_ID_HEADER = "X-app-id";
    public static final List<String> SUPPORTED_APP_IDS = Arrays.asList("stockapp-flutter", "stockapp-admin");
    public static final String HEADER_REFRESH = "X-refresh-token";

    public static String generateTokenFromPrincipal(UserPrincipal userPrincipal,
                                                    String warehouseName,
                                                    boolean isRefresh,
                                                    boolean isRememberMe) throws JsonProcessingException {
        TokenUser tokenUser = userPrincipal.toTokenUser(warehouseName);
        return JwtProperties.generateToken(tokenUser, isRefresh, isRememberMe);
    }

    public static String generateToken(TokenUser tokenUser, boolean isRefresh, boolean isRememberMe)
            throws JsonProcessingException {

        // convert User to json
        String user = staticObjectMapper.writeValueAsString(tokenUser);

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date exp;
        if (!isRefresh) {
            exp =
                    new Date(
                            nowMillis
                                    +
                                    // if remember me is selected set the time to EXPIRATION_TIME_REMEMBER_ME
                                    (isRememberMe
                                            ? JwtProperties.EXPIRATION_TIME_REMEMBER_ME
                                            : JwtProperties.EXPIRATION_TIME));
        } else {
            exp =
                    new Date(
                            nowMillis
                                    +
                                    // if remember me is selected set the time to EXPIRATION_TIME_REMEMBER_ME
                                    (isRememberMe
                                            ? JwtProperties.EXPIRATION_TIME_REMEMBER_ME
                                            : JwtProperties.EXPIRATION_TIME_REFRESH));
        }


        // Create JWT Token
        return JWT.create()
                .withIssuer("stockapp")
                .withSubject(tokenUser.getUsername())
                .withClaim("user", user)
                .withNotBefore(now)
                .withIssuedAt(now)
                .withExpiresAt(exp)
                .sign(HMAC512(JwtProperties.SECRET.getBytes()));
    }

    public static boolean userHaveProperRole(UserRole role, HttpServletRequest request) {

        String appIdHeader = request.getHeader(JwtProperties.APP_ID_HEADER);
        if("stockapp-admin".equals(appIdHeader)) {
            return UserRole.ADMIN.equals(role) || UserRole.USER.equals(role);
        }
        return false;
    }
}
