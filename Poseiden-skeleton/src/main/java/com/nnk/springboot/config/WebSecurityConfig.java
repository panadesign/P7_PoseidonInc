package com.nnk.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//	@Override
//	public void configure(HttpSecurity http) throws Exception {
//		http
//				.authorizeRequests()
//				.antMatchers("/login", "/403", "/user/add").permitAll()
//				.anyRequest().authenticated()
//				.and()
//				.formLogin()
//				.loginPage("/")
//				.failureUrl("/login?error=true").permitAll()
//				.defaultSuccessUrl("/bidList/list", true)
//				.and()
//				.oauth2Login()
//				.loginPage("/")
//				.failureUrl("/login?error=true").permitAll()
//				.defaultSuccessUrl("/bidList/list", true)
//				.permitAll();
//	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring()
				.antMatchers("/css/**", "/img/**")
				.antMatchers("/h2-console/**");
	}

}
