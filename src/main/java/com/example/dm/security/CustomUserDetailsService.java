package com.example.dm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.dm.repository.UserRepository;

import model.*;

// Klasa predstavlja servis za dobavljanje podatka za autentifikaciju korisnika
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService{
 
    
    @Autowired
    private UserRepository korisnikRepository;  
    @Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = korisnikRepository.findByUsername(username);
		
		UserDetailsImpl userDetails = new UserDetailsImpl();
		userDetails.setUsername(user.getUsername());
		userDetails.setPassword(user.getPassword());
		userDetails.setRoles(user.getRoles());
		
		return userDetails;
		
    }
 
     
 


	
     
}
