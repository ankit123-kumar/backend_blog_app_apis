package com.blogifyr.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.blogifyr.security.JWTAuthenticationEntryPoint;
import com.blogifyr.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

	public static final String[] PUBLIC_URLS ={
			"/api/auth/**",
	        "/v3/api-docs/**",
	        "/v2/api-docs/**",
	        "swagger-resources/**",
	        "swagger-ui/**",
	        "/web-jars/**"
	};
	@Autowired
	private JWTAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	/*
	 * @Bean public UserDetailsService getUserDetailsService() {
	 * 
	 * return new UserDetailsServiceImpl(); }
	 */
   
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
 public DaoAuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(this.userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		
		return daoAuthenticationProvider;
	}

 @Bean
 public DaoAuthenticationProvider authenticationManagerBean() throws Exception {
     return this.authenticationProvider();
 }
 
 @Bean
 public AuthenticationManager authenticationManager(AuthenticationConfiguration builder)throws Exception {
	 
	 return builder.getAuthenticationManager();
 }
 @Bean
 public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    
	 http
	 .csrf(csrf->csrf.disable())
	 .authorizeHttpRequests(t -> t .requestMatchers(PUBLIC_URLS).permitAll())
	 .authorizeHttpRequests(t->t .requestMatchers("/v3/api-docs").permitAll())
	 .authorizeHttpRequests(t -> t .requestMatchers(HttpMethod.GET).permitAll().anyRequest().authenticated())
     
	 .exceptionHandling(ex->ex.authenticationEntryPoint(this.jwtAuthenticationEntryPoint))
	 
	 .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
	 
 
	  http.authenticationProvider(authenticationProvider());
      
	  http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	 return http.build();
	 
	
 }

 
   
   
}
