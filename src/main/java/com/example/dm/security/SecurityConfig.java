package com.example.dm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	@Qualifier("customUserDetailsService")
	UserDetailsService userDetailsService; // Dobavljac autentifikacionih podataka korisnika

	// Podesavanja upravljac autentifikacijom korisnika
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Autentifikacija pomocu podataka korisnika i enkodera kriptovanog passworda
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());

	}

	// Bezbedonosna podesavanja HTTP zahteva
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()	//	zahtevi moraju biti autorizovani
				// moze biti vise .antMatchers(), redosled je bitan specificniji idu prvi
				.antMatchers("/auth/loginPage","/auth/registerPage","/auth/registerUser").permitAll()	// dozvoljeni linkovi
				.antMatchers("/Pocetna/izvestajAdmin","/Crud/kontrolna","/KontrolnaTabla.jsp").hasRole("ADMIN")
				.anyRequest().authenticated()	// za sve ostale linkove korisnik mora biti autentifikovan
			.and()
				.formLogin()
				.loginPage("/auth/loginPage")				// URL nase login stranice
				.loginProcessingUrl("/login")				// URL koji ce pozvati default Spring login() metodu
		        .defaultSuccessUrl("/auth/indexPage", true) // URL za preusmeravanje uspesnog login-a
		        .failureForwardUrl("/auth/loginPage")		// URL za preusmeravanje neuspesnog login-a
		    .and()
		        .logout()
		        .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // URL koji ce pozvati default Spring logout() metodu
		        .logoutSuccessUrl("/auth/loginPage")
		        .deleteCookies("JSESSIONID")
		        .invalidateHttpSession(true) 
			.and()
				.csrf()	// zastita od CSRF napada
			.and()
				.exceptionHandling().accessDeniedPage("/access_denied.html");	// URL za preusm. korisniku nije dozvoljen pristup stranici	
	}
}
