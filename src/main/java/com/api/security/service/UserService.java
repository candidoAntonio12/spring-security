package com.api.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.security.models.UserModel;
import com.api.security.repository.UsuarioRepository;

@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private UsuarioRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.repository.findByEmail(username);
	}
	
	public UserModel save(UserModel user) {
		user.setPassword(encoder.encode(user.getPassword()));
		return this.repository.save(user);
		
	}


}
