package com.abpoint.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

import com.abpoint.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService userDetailsService() {
		return new CustomUserDetailsService();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests().antMatchers("/public/**", "/api/**").permitAll()
				// Allow access to the static resources
				.antMatchers("/css/**", "/js/**", "/images/**").authenticated()
				.antMatchers("/maintenanceDashboard", "/maintenance-entry", "/saveExtraCharges", "/add-extra-charges",
						"/grid-view","/unapproved-payments","/maintenance-charges","/getAllExtraCharges","/saveEditedChanges")
				.permitAll()// .hasAnyRole("USER", "ADMIN")
				.antMatchers("/saveExtraCharges").permitAll().antMatchers("/admin/**").hasRole("ADMIN").anyRequest()
				.authenticated().and().formLogin().loginPage("/login").defaultSuccessUrl("/index", true)
				.failureUrl("/login?error=true").permitAll().and().logout().permitAll().and().headers().frameOptions()
				.sameOrigin() // Allow framing from the same origin
				.and().authorizeRequests();

	}

	/*
	 * http .authorizeRequests() .antMatchers("/login").permitAll()
	 * .antMatchers("/admin/**").hasRole("ADMIN") // Restrict access to ADMIN role
	 * .anyRequest().authenticated() .and() .formLogin() .loginPage("/login")
	 * .defaultSuccessUrl("/index", true) .failureUrl("/login?error=true")
	 * .permitAll() .and() .logout() .logoutUrl("/logout") .permitAll() .and()
	 * .headers() .frameOptions().sameOrigin(); // Allow framing of the site from
	 * the same origin }
	 */

}
