package com.ddcsoftware.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Service
public class JWTUtil {

    //Creating a secret key to pass it as bytes. For production use a config file or secret
    private static final String SECRET_KEY = "randomstring_123456_example_randomstring_";

    /**
     *  Creating token that contains the structure of JWT
     *         -Header: encryption type
     *         -Payload: message
     *         -Signature: key
     *
     * @param subject the username
     * @param claims the data in key value configuration
     * @return token
     */
    public String issueToken(String subject, Map<String, Object> claims){
        //Note: most of these values can come from a config file instead of hardcoded
        String token = Jwts.builder()
                .claims(claims)//make sure subject is set after claims, or it will override
                .subject(subject)
                .issuer("http://localhost:8080/api/v1/customers")//Website url that is issuing the token
                .issuedAt(Date.from(Instant.now()))//Sets current date
                .expiration(Date.from(Instant.now().plus(15, ChronoUnit.DAYS)))//Adding expiration date
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();//this gives us the key
        return token;
    }

    /**
     *  Creating token with empty claims
     *  This just uses subject
     * @param subject the username
     * @return token
     */
    public String issueToken(String subject){
        //Just return the subject and empty map
        return issueToken(subject, Map.of());
    }

    /**
     *  Creating token that contains subject and string array

     * @param subject username
     * @param scopes String array to pass claims
     * @return
     */
    public String issueToken(String subject, String ...scopes){
        //Just return the subject and empty map
        return issueToken(subject, Map.of("scopes", scopes));
    }

    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
}
