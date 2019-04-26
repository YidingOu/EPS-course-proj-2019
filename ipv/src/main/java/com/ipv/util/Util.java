package com.ipv.util;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;

import com.ipv.entity.User;
import com.ipv.reporsitory.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class Util {

	@Value("${spring.jwt.SECRET_KEY}")
	private static String SECRET_KEY;
	// removing the password and salt when user is returned
	public static void processUser(User user, UserRepository repo) {
		repo.detach(user);
		user.setPass(null);
		user.setSalt(null);
	}

	public static String create(User user) {
		int role = user.getRole();
		String id = String.valueOf(user.getId());
		String issuer = "ipvTeam";
		String jwt = createJWT(id, issuer, role);
		return jwt;
	}

	private static String createJWT(String id, String issuer, int role) {
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

	public static Claims decodeJWT(String jwt) {
		Claims claims = Jwts.parser()
				.setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
				.parseClaimsJws(jwt).getBody();
		return claims;

	}
}

