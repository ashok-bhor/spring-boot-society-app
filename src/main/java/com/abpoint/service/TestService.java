package com.abpoint.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class TestService {
	@Autowired
	private static PasswordEncoder passwordEncoder;

	
	public static void testPasswordMatching() {
	    String rawPassword = "ashokadmin";
	    String encodedPassword = "$2a$10$DowJonesIndex1234567890qwertzuiopasdfghjklyxcvbnm/";
	    boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
	    System.out.println("Password matches: " + matches);
	}
	
	public static void main(String[] args) {
		testPasswordMatching();
	}

}
