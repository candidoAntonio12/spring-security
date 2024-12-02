package com.api.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.api.security.dto.LoginDto;
import com.api.security.providers.JwtProvider;

@Service
public class AuthServiceImpl implements AuthService{
	
	@Autowired
	private JwtProvider jwtProvider;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Override
	public String login(LoginDto login){

		Authentication authenticationToken = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				login.email(),
				login.password()
        ));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      
        return  jwtProvider.createToken(authenticationToken);

		
		
	}

}
