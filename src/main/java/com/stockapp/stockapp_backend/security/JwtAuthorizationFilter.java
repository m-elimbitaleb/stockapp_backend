/*
 * Copyright (c) 2021. stockapp.
 * Proprietary source code; any copy or modification is prohibited.
 *
 * @author Moulaye Abderrahmane <moolsbytheway@gmail.com>
 *
 */

package com.stockapp.stockapp_backend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.stockapp.stockapp_backend.model.User;
import com.stockapp.stockapp_backend.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.stockapp.stockapp_backend.security.JwtProperties.userHaveProperRole;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final UserRepository repository;

    JwtAuthorizationFilter(
            AuthenticationManager authenticationManager, UserRepository repository) {
        super(authenticationManager);
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // Read the Authorization header, where the JWT token should be
        String header = request.getHeader(JwtProperties.HEADER_STRING);

        // If header does not contain BEARER or is null delegate to Spring impl and exit
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        // Check for custom headers
        String appIdHeader = request.getHeader(JwtProperties.APP_ID_HEADER);

        // If header does not contain BEARER or is null delegate to Spring impl and exit
        if (appIdHeader == null || !JwtProperties.SUPPORTED_APP_IDS.contains(appIdHeader)) {
            chain.doFilter(request, response);
            return;
        }

        // If header is present, try grab user principal from database and perform authorization
        Authentication authentication = getUsernamePasswordAuthentication(request);

        // check if auth is okay, if not return 403
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            response.setStatus(403);
        }
        // Continue filter execution
        chain.doFilter(request, response);
    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
        String token =
                request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX, "");

        // parse the token and validate it
        DecodedJWT validToken = null;
        try {
            validToken = JWT.require(HMAC512(JwtProperties.SECRET.getBytes())).build().verify(token);
        } catch (JWTVerificationException e) {
            logger.error("Token verification failed");
        }

        // Search in the DB if we find the user by token subject (username)
        // If so, then grab user details and create spring auth token using username, pass,
        // authorities/roles
        if (validToken != null) {
            String userName = validToken.getSubject();
            User user = repository.findByUsername(userName);

            if (!userHaveProperRole(user.getRole(), request)) {
                return null;
            }

            UserPrincipal principal = new UserPrincipal(user);
            return new UsernamePasswordAuthenticationToken(userName, null, principal.getAuthorities());
        }
        return null;
    }
}
