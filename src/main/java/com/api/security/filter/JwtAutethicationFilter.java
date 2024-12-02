package com.api.security.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.api.security.providers.JwtProvider;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAutethicationFilter extends OncePerRequestFilter{

	public final static String HEADER_PREFIX="Bearer ";
	public final static String HEADER_NAME="Authorization";
	public String TOKEN="Authorization";
	
	private UserDetailsService userDetailsService;
    private JwtProvider jwtProvider;
	
	public  JwtAutethicationFilter(UserDetailsService userDetailsService,JwtProvider jwtProvider) {
		this.userDetailsService=userDetailsService;
		this.jwtProvider=jwtProvider;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
	String TOKEN=getToken(request);
	if(TOKEN!=null ) {
		System.out.println("meu iokem "+TOKEN);	
		if(jwtProvider.validateToken(TOKEN)) {
			var user=userDetailsService.loadUserByUsername(jwtProvider.getUserame(TOKEN));
			UsernamePasswordAuthenticationToken authentication=
					new UsernamePasswordAuthenticationToken(user,null , user.getAuthorities());
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			   String uri = request.getRequestURI();
			   String method = request.getMethod();
			   System.out.println("Processing request: {} {}"+ method + uri);
			}
		}
	
		
		filterChain.doFilter(request, response);
	}
	
	private String getToken(HttpServletRequest request) {
		var header =request.getHeader(HEADER_NAME);
		if(header!=null && header.startsWith(HEADER_PREFIX)) {
			return header.replaceAll(HEADER_PREFIX,"");
			
		}
		return null;
	}
	
	

	
	
	
	

}
