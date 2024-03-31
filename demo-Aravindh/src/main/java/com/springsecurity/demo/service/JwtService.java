package com.springsecurity.demo.service;


import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.springsecurity.demo.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	private final String SECRET_KEY="044af83952167ccd3640086deb532e7a035a7400f58286b4a568f1555e7939e9";
	
	// This method helps to generate the token 
	public String generateToken(User user) {
		String token=Jwts
				.builder()
				.subject(user.getUsername())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()+24*60*60*1000))
				.signWith(getSigninKey())
				.compact();
		return token;
	}
	private SecretKey getSigninKey() {
		byte[] KeyBytes=Decoders.BASE64URL.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(KeyBytes);
	}
	private Claims extractallClaims(String token) {
		return Jwts
				.parser()
				.verifyWith(getSigninKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	public <T>T extractClaim(String token, Function<Claims,T> resolver){
		Claims claims=extractallClaims(token);
		return resolver.apply(claims);
	}
	public String extractUsername(String token) {
		return extractClaim(token,Claims::getSubject);
	}
	public boolean isValid(String token,UserDetails user) {
		String username=extractUsername(token);
		return (username.equals(user.getUsername())) && !isTokenExpired(token);
	}
	private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
