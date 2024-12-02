
package com.api.security.providers;


import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtProvider {
	
	private Date expiresAt=new Date(System.currentTimeMillis()+3600*1000);

	private String secret="${my.secret.jwt}";
	private String issuer="candido@gmail.com";	

	
	public String createToken(Authentication authentication) {
		try {
			return JWT.create()
					.withSubject(authentication.getName())
					.withIssuer("candido@gmail.com")
					.withIssuedAt(new Date())
					.withExpiresAt(expiresAt)
					.sign(Algorithm.HMAC512(secret));
		} catch (IllegalArgumentException | JWTCreationException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public boolean validateToken(String Token ) throws JWTVerificationException, IllegalArgumentException, UnsupportedEncodingException {
		verifier(Token);
		return true;
	}
	
	public String getUserame(String token) throws JWTVerificationException, IllegalArgumentException, UnsupportedEncodingException {
		if(token!=null) {
			return verifier(token).getSubject();
		}
		return null;
	}
	
	public DecodedJWT verifier(String token) throws JWTVerificationException, IllegalArgumentException, UnsupportedEncodingException {
		return JWT.require(Algorithm.HMAC512(secret))
		.build()
		.verify(token);		
	}
	
}