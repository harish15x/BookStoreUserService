package com.bridgelabz.bookstore.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import org.springframework.stereotype.Component;

@Component
public class TokenUtil {
    private final String TOKEN_SECRET = "LoginToken";

    public String createToken(Long id) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            return JWT.create().withClaim("user_id", id).sign(algorithm);
        } catch (IllegalArgumentException | JWTCreationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Long decodeToken(String token) {
        Long userId;
        Verification verification = null;
        try {
            verification = JWT.require(Algorithm.HMAC256(TOKEN_SECRET));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        JWTVerifier jwtVerifier = verification.build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        Claim claim = decodedJWT.getClaim("user_id");
        userId = claim.asLong();
        return userId;
    }
}

