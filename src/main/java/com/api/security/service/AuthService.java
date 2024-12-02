package com.api.security.service;

import com.api.security.dto.LoginDto;

public interface AuthService {
	
	String login(LoginDto auth);
}
