package com.myIGCoach.service;

import javax.inject.Named;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.myIGCoach.models.User;

@Service
@Named
public interface UserService {
	// to save a new user
	public User create(User u);

	// to have details about user
	public ResponseEntity<User> read(Long userId);

	// to update user's information (firstName, lastName or email)
	public String update(Long userId, User u);

	// to remove the user
	public String delete(Long u, Long userId);
	
	// to get a user by his email
	public ResponseEntity<User> findByEmail(String email);
	
	// to authenticate a user by his email and password
	public ResponseEntity<User> authenticate(String base64EncodedjsonToken);
		
}
