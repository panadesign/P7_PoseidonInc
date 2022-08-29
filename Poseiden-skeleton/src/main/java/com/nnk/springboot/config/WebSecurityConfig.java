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
import org.springframework.web.context.WebApplicationContext;

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
				.antMatchers("/user/**", "/app/secure/article-details").hasAuthority("ADMIN")
				.antMatchers("/*", "/403").permitAll()
				.anyRequest().authenticated()
				.and()
				.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/bidList/list", true)
				.failureUrl("/login?error=true")
				.usernameParameter("username")
				.passwordParameter("password")
				.permitAll()
				.and()
				.oauth2Login()
				.loginPage("/login")
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
