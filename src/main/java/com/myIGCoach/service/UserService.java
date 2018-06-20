package com.myIGCoach.service;

import java.util.List;

import javax.inject.Named;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.myIGCoach.models.User;

@Service
@Named
public interface UserService {
	public List<User> findAll();
	public User create(User u);
	public ResponseEntity<User> read(Long id);
	public String update(User u, Long id);
	public String delete(Long id);
	public User findByFirstName(String name);

}
