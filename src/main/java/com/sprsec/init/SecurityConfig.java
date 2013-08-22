package com.sprsec.init;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.sprsec.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Override
	protected void registerAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.userDetailsService(customUserDetailsService)
			.authorizeRequests()
			.antMatchers("/sec/moderation.html").hasRole("MODERATOR")
			.antMatchers("/admin/**").hasRole("ADMIN")
			.and()
			.formLogin()
			.loginPage("/user-login.html")
			.defaultSuccessUrl("/success-login.html")
			.failureUrl("/error-login.html")
			.permitAll()
			.and()
			.logout()
			.logoutSuccessUrl("/index.html");
	}

}
