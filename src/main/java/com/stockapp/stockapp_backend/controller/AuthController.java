/*
 * Copyright (c) 2021. stockapp.
 * Proprietary source code; any copy or modification is prohibited.
 *
 * @author Moulaye Abderrahmane <moolsbytheway@gmail.com>
 *
 */

package com.stockapp.stockapp_backend.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.stockapp.stockapp_backend.security.JwtProperties;
import com.stockapp.stockapp_backend.security.TokenUser;
import com.stockapp.stockapp_backend.model.User;
import com.stockapp.stockapp_backend.repository.UserRepository;
import com.stockapp.stockapp_backend.service.UserService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.stockapp.stockapp_backend.security.JwtProperties.userHaveProperRole;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<Token> registerUser(@RequestBody User user) {
        User saveUser = userService.registerUser(user);
        return ResponseEntity.ok(generateToken(saveUser));
    }

    @PostMapping("/token")
    public ResponseEntity<Token> add(@RequestBody UserPass userPass, HttpServletRequest request) {
        Token token = new Token();
        HttpStatus status = HttpStatus.FORBIDDEN;

        if (!headersAreValid(request)) {
            return ResponseEntity.status(status).body(token);
        }

        if (userPass.isEmpty() && tokenGivenAndIsValid(request)) {
            return ResponseEntity.ok(token);
        }

        if (userPass.isEmpty()) {
            return ResponseEntity.status(status).body(token);
        }

        if (StringUtils.isNumeric(userPass.username) && !userPass.username.startsWith("+")) {
            int prefix = 0;
            if (userPass.countryCode != null)
                prefix = PhoneNumberUtil.getInstance().getCountryCodeForRegion(userPass.countryCode);

            if (prefix <= 0) {
                // if no prefix found set it to Morocco's
                prefix = 212;
            }
            userPass.username = "+" + prefix + userPass.username;
        }
        User user = repository.findByUsername(userPass.username);
        if (isUserValid(user, userPass.password)) {

            if (!userHaveProperRole(user.getRole(), request)) {
                return ResponseEntity.status(status).body(token);
            }
            status = HttpStatus.OK;
            token = generateToken(user);
        }
        return ResponseEntity.status(status).body(token);
    }

    private Token generateToken(User user) {
        Token token = new Token();
        try {
            TokenUser tokenUser = user.toTokenUser(user.getWarehouse().getName());

            token.user = tokenUser;
            token.authenticated = true;
            token.authToken =
                    JwtProperties.generateToken(tokenUser, false, true);
            token.refreshToken =
                    JwtProperties.generateToken(tokenUser, true, true);
        } catch (JsonProcessingException e) {
            log.error("Unable to generate auth token {}", Arrays.toString(e.getStackTrace()));
        }
        return token;
    }


    private boolean headersAreValid(HttpServletRequest request) {
        // Check for custom headers
        String appIdHeader = request.getHeader(JwtProperties.APP_ID_HEADER);

        // If header does not contain BEARER or is null delegate to Spring impl and exit
        return appIdHeader != null && JwtProperties.SUPPORTED_APP_IDS.contains(appIdHeader);
    }

    private boolean tokenGivenAndIsValid(HttpServletRequest request) {
        String header = request.getHeader(JwtProperties.HEADER_STRING);

        // If header does not contain BEARER or is null delegate to Spring impl and exit
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            return false;
        }

        String token =
                request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");

        // parse the token and validate it
        DecodedJWT validToken;
        try {
            validToken = JWT.require(HMAC512(JwtProperties.SECRET.getBytes())).build().verify(token);
        } catch (JWTVerificationException e) {
            return false;
        }

        return validToken != null;

    }

    private boolean isUserValid(User user, String password) {
        return user != null &&
                user.getActiveUser() &&
                passwordEncoder.matches(password, user.getPassword());
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Token {
        private boolean authenticated = false;
        private String authToken;
        private String refreshToken;
        private TokenUser user;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserPass {
        private String username;
        private String password;
        private String countryCode;

        @JsonIgnore
        public boolean isEmpty() {
            return username == null || password == null;
        }
    }
}
