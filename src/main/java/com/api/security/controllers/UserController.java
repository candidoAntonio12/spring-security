package com.api.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.security.dto.UserDto;
import com.api.security.models.UserModel;
import com.api.security.service.UserService;

@RestController
@RequestMapping(path = "/usuario")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/cadastrar")
	public ResponseEntity<UserModel> createUser(@RequestBody UserDto userDto) {
		var user=new UserModel(null,userDto.email(),userDto.password(),userDto.username());
	
		return ResponseEntity.ok(userService.save(user));
	}
	
}
