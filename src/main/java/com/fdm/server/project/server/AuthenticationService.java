package com.fdm.server.project.server;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static java.util.Collections.emptyList;

@Service
public class AuthenticationService {

    static final long EXPIRATION_TIME = 86_400_000;
    static final String SIGNING_KEY = "SecretKey";
    static final String PREFIX = "Bearer";

    // Add token to Authorization header
    static public void addToken(HttpServletResponse response, String username) {
        System.out.println("inside addToken method in backend!");
        String JwtToken = Jwts.builder().setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis()
                        + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SIGNING_KEY)
                .compact();
        response.addHeader("Authorization", PREFIX + " " + JwtToken);
        response.addHeader("Access-Control-Expose-Headers", "Authorization");
    }
    // Get token from Authorization header
    static public Authentication getAuthentication(HttpServletRequest request) {

        System.out.println("in getAuthentication method!");
        String token = request.getHeader("Authorization");
        if (token != null) {
            String user = Jwts.parser()
                    .setSigningKey(SIGNING_KEY)
                    .parseClaimsJws(token.replace(PREFIX, ""))
                    .getBody()
                    .getSubject();
            if (user != null)
                return new UsernamePasswordAuthenticationToken(user, null,
                        emptyList());
        }
        return null;
    }
}

