package com.blogifyr.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.blogifyr.config.CustomUserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenHelper {

	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
		
	///private String secret = "jwtTokenKey";
	 private Key secret = Keys.secretKeyFor(SignatureAlgorithm.HS256); 
	
	//retrieve username from jwt token
	
	public String getUsernameFromToken(String token) {
		
		return getClaimFromToken(token,Claims::getSubject);
	}
	
	public Date getExpirationDateFromToken(String token) {

		return getClaimFromToken(token,Claims::getExpiration);

	}
	
	public<T> T getClaimFromToken(String token, Function<Claims,T> claimsResolver) {
		
		final Claims claims = getAllClaimsFromToken(token);
		
		return claimsResolver.apply(claims);
	}
	
	//for reterieving any information from token we will need the secret 
	 
	private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
	  //check if token is expired
	  private Boolean isTokenExpired(String token) {

	  final Date expiration = getExpirationDateFromToken(token);
	  return expiration.before(new Date());
		  
	  }
	  //generate token for user
	  public String generateToken(UserDetails userDetails) {

		  Map<String,Object> claims = new HashMap<>();
          return doGenerateToken(claims,userDetails.getUsername());    
	  }
	  
	  private String doGenerateToken(Map<String,Object> claims,String subject) {
		  
		  return Jwts.builder()
				  .setClaims(claims)
				  .setSubject(subject)
				  .setIssuedAt(new Date(System.currentTimeMillis()))
				  .setExpiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY *1000))
				  .signWith(SignatureAlgorithm.HS256,secret)
				  .compact();
	  }

	  //validate token
	  public Boolean validteToken(String token,UserDetails userDetails ) {
		  
		  final String username = getUsernameFromToken(token);
		  return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
	  }
}
