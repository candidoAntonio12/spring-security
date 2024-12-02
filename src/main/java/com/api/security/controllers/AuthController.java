package com.api.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.security.dto.AuthDto;
import com.api.security.dto.LoginDto;
import com.api.security.service.AuthService;

@RestController
@RequestMapping("/login")
public class AuthController  {
	
	@Autowired
	private AuthService authService;
	
	@PostMapping
	public ResponseEntity<AuthDto> logar(@RequestBody LoginDto loginDto){
		String token=authService.login(loginDto);
		AuthDto authDto=new AuthDto(token);
	
		return ResponseEntity.ok(authDto);
	} 
	
	
	
}
