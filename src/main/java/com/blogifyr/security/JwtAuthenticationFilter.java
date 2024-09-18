package com.blogifyr.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenHelper helper;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
	
		// 1. get token
		 String requestToken = request.getHeader("Authorization");
		System.out.println(requestToken);
		
		String username = null;
		String token = null;
		
		if(requestToken!=null && requestToken.startsWith("Bearer"))
		{
			 
			token = requestToken.substring(7);
			 try {
				  
				 username = this.helper.getUsernameFromToken(token);
					
			     }
			 
			 catch (IllegalArgumentException e) {
				 System.out.println("unable to get jwt token");
			    }
			 
			 catch(ExpiredJwtException e) {
				 System.out.println("token is expired");
				 }
			 
			 catch(MalformedJwtException e) {		
				 System.out.println("invalid jwt");
			     }
		
			 catch (Exception e) {
		            System.out.println("Unable to get JWT token: " + e.getMessage());
		        }
		}
		
		
		else {
			System.out.println("jwt token does not start with Bearer");
		}
		
		//2 validate token ,once we get the token now validate
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) 
		{

			
			 UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			if(token!=null && this.helper.validteToken(token,userDetails ))
			{
			   //sahi chal rha h
				//authenticate krana h
				
				
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}else {
             
				System.out.println("invalid jwt token");
			}
		}
		else {
			System.out.println("username is  null or context not null");
		   
		}
		filterChain.doFilter(request, response);
		}

}
