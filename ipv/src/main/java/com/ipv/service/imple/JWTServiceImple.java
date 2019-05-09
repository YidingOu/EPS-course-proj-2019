package com.ipv.service.imple;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ipv.entity.User;
import com.ipv.service.JWTService;
import com.ipv.util.wrapper.JWTUserInfoWrapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
/**
 *The function body JWT service
 *Reference 
 *https://developer.okta.com/blog/2018/10/31/jwts-with-java
 */
@Service
public class JWTServiceImple implements JWTService {

    @Value("${spring.jwt.SECRET_KEY}")
    private String SECRET_KEY;

    // fetch user id and role to create jwt
    @Override
    public String createJWT(User user) {
        int role = user.getRole();
        int uid = user.getId();
        String id = String.valueOf(uid);
        String issuer = "ipvTeam";
        String jwt = createJWTHelper(id, issuer, role, uid);
        return jwt;
    }

    // take a jwt and decode it to wrapper class
    @Override
    public JWTUserInfoWrapper decodeJWT(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(jwt).getBody();
        JWTUserInfoWrapper info = new JWTUserInfoWrapper();
        info.setId((Integer)claims.get("UserID"));
        info.setExpireDate(claims.getExpiration());
        info.setRole((Integer)claims.get("role"));
        
        return info;

    }

    // validate jwt, if it not expired, renew jwt.
    @Override
    public JWTUserInfoWrapper validate(String jwt, User user) {
        JWTUserInfoWrapper info = decodeJWT(jwt);
        long timeStamp = System.currentTimeMillis();
        Date now = new Date(timeStamp);
        Date jwtDate = info.getExpireDate();
        if (jwtDate.compareTo(now) < 0) {
            return null;
        } else {
            info.setNewJWT(createJWT(user));
            return info;
        }
    }

    //alternative validate method, do not need user input
    @Override
    public JWTUserInfoWrapper validate(String jwt) {
        JWTUserInfoWrapper info = decodeJWT(jwt);
        long timeStamp = System.currentTimeMillis();
        Date now = new Date(timeStamp);
        Date jwtDate = info.getExpireDate();
        if (jwtDate.compareTo(now) < 0) {
            return null;
        } else {
            int uid = info.getId();
            String issuer = "ipvTeam";
            String id = String.valueOf(uid);
            int role = info.getRole();
            info.setNewJWT(createJWTHelper(id, issuer, role, uid));
            return info;
        }
    }

    //create jwt, set fields
    private String createJWTHelper(String id, String issuer, int role, int uid) {

        //create JWT algorithm
        SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;

        //time stamp
        long timeStamp = System.currentTimeMillis();
        Date now = new Date(timeStamp);

        //sign the JWT
        byte[] apiKeySecretByte = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretByte, algorithm.getJcaName());

        //set JWT claim
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setIssuer(issuer)
                .claim("role", role)
                .claim("UserID", uid)
                .signWith(signingKey);

        long expTime = timeStamp + 1800000;
        Date exp = new Date(expTime);
        builder.setExpiration(exp);

        return builder.compact();
    }

}
