package com.example.dm.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.dm.repository.RoleRepository;
import com.example.dm.repository.UserRepository;

import model.Role;
import model.User;

@Controller
@RequestMapping(value = "/auth")
public class LoginController {

	@Autowired
	UserRepository pur;

	@Autowired
	RoleRepository proleRepo;
	

	@RequestMapping(value = "loginPage", method = RequestMethod.GET)
	public String loginPage() {
		return "login";
	}

	@RequestMapping(value = "indexPage", method = RequestMethod.GET)
	public String pocetna(HttpServletRequest req) {
		String currentUserName = "";
		
		// Uzimamo username iz autentikacije koja je kreirana pri login-u
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    currentUserName = authentication.getName();
		}
		
		User u = pur.findByUsername(currentUserName);
		
		req.getSession().setAttribute("korisnik", u);
		return "index";
	}

	@RequestMapping(value = "registerPage", method = RequestMethod.GET)
	public String newUser() {
		return "register";
	}

	@RequestMapping(value = "registerUser", method = RequestMethod.POST)
	public String saveUser(String ime, String prez, String uname, String pass, String date, String pol) throws Exception{
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);  
		Role role = proleRepo.findById(1).get();
		User u = new User();
		
	
		String pasw = passwordEncoder.encode(pass);
		passwordEncoder.encode(pasw);
		u.setPassword(passwordEncoder.encode(pass));
		u.setUsername(uname);
		u.setFirstName(ime);
		u.setLastName(prez);
		u.setBirthday(date1);
		u.setGender(pol);
		u.addRole(role);
		role.addUser(u);

		pur.saveAndFlush(u);
		proleRepo.saveAndFlush(role);
		
		return "login";
	}

}
