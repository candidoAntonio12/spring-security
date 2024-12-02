package com.api.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.api.security.filter.JwtAutethicationFilter;
import com.api.security.providers.JwtProvider;
import com.api.security.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityFilter {
	@Autowired
	private UserService usuarioService;
	private JwtAutethicationFilter autethicationFilter;
	private JwtProvider jwtProvider=new JwtProvider();

	
	@Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		autethicationFilter=new JwtAutethicationFilter(usuarioService,jwtProvider);
		
		http.csrf(csrf ->csrf.disable())
			.cors(t -> corsConfigurationSource())
			.authorizeHttpRequests(t ->{ t.requestMatchers("/login").permitAll();
										t.requestMatchers(HttpMethod.OPTIONS,"/**").permitAll();
										t.anyRequest().authenticated();})
			.httpBasic(Customizer.withDefaults());
			
			http.addFilterBefore(autethicationFilter, UsernamePasswordAuthenticationFilter.class);
			
			http.sessionManagement(t -> t.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		return http.build();
		
	}
	@Bean 
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.addAllowedOriginPattern("*");
	    configuration.addAllowedMethod("*"); 
	    configuration.addAllowedHeader("*");
	    configuration.setAllowCredentials(true);
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	}
		

}
