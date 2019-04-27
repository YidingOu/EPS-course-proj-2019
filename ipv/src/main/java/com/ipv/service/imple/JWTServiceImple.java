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

@Service
public class JWTServiceImple implements JWTService {

    @Value("${spring.jwt.SECRET_KEY}")
    private String SECRET_KEY;

    @Override
    public String createJWT(User user) {
        int role = user.getRole();
        String id = String.valueOf(user.getId());
        String issuer = "ipvTeam";
        String jwt = createJWTHelper(id, issuer, role);
        return jwt;
    }

    @Override
    public JWTUserInfoWrapper decodeJWT(String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(jwt).getBody();
        JWTUserInfoWrapper info = new JWTUserInfoWrapper();
        info.setId((Integer)claims.get("id"));
        info.setExpireDate(claims.getExpiration());
        info.setRole((Integer)claims.get("role"));
        
        return info;

    }

    private String createJWTHelper(String id, String issuer, int role) {
        //https://developer.okta.com/blog/2018/10/31/jwts-with-java

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
                .signWith(signingKey);

        long expTime = timeStamp + 1800000;
        Date exp = new Date(expTime);
        builder.setExpiration(exp);

        return builder.compact();
    }

}
