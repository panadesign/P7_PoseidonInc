package com.nnk.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig{
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/*", "/app/login").permitAll()
				.antMatchers("/user/**", "/app/secure/**", "/admin/**").hasAuthority("ADMIN")
				.anyRequest().authenticated()
				.and()
				.formLogin()
				.loginPage("/app/login")
				.defaultSuccessUrl("/bidList/list", true)
				.failureUrl("/app/login?error=true")
				.usernameParameter("username")
				.passwordParameter("password")
				.permitAll()
				.and()
				.oauth2Login()
				.loginPage("/app/login")
				.defaultSuccessUrl("/bidList/list", true)
				.and()
				.logout()
				.logoutSuccessUrl("/")
				.logoutRequestMatcher(new AntPathRequestMatcher("/app-logout"))
				.permitAll()
				.invalidateHttpSession(true)
				.deleteCookies("JSESSIONID")
				.and()
				.exceptionHandling()
				.accessDeniedPage("/403");
				
		return http.build();
	}
	
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring()
				.antMatchers("/css/**", "/img/**")
				.antMatchers("/h2-console/**");
	}

}
