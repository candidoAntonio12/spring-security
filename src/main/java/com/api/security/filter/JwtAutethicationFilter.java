package com.api.security.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAutethicationFilter extends OncePerRequestFilter{

	public final static String HEADER_PREFIX="Bearer ";
	public final static String HEADER_NAME="Authorization";
	public String TOKEN="Authorization";
	
	private UserDetailsService userDetailsService;
	
	public  JwtAutethicationFilter(UserDetailsService userDetailsService) {
		this.userDetailsService=userDetailsService;	
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String TOKEN=getToken(request);
		
		
	}
	
	private String getToken(HttpServletRequest request) {
		var header =request.getHeader(HEADER_NAME);
		if(header!=null && header.startsWith(HEADER_PREFIX)) {
			return header.replaceAll(HEADER_PREFIX,"");
			
		}
		return null;
	}
	
	

	
	
	
	

}
