package com.api.security.configuracao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.api.security.filter.JwtAutethicationFilter;
import com.api.security.service.UsuarioService;

@Configuration
@EnableWebSecurity
public class SecurityFilter {
	@Autowired
	private UsuarioService usuarioService;
	private JwtAutethicationFilter autethicationFilter;
	

	
	@Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		autethicationFilter=new JwtAutethicationFilter(usuarioService);
		http.csrf(csrf -> csrf.csrfTokenRepository(new CookieCsrfTokenRepository()))
			.authorizeHttpRequests(t -> t.anyRequest().authenticated())
			.addFilterBefore(autethicationFilter, UsernamePasswordAuthenticationFilter.class)
			.sessionManagement(t -> t.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		return http.build();
		
	}
	@Bean 
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	
}
