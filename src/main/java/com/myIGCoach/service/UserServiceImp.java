package com.myIGCoach.service;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.myIGCoach.models.User;
import com.myIGCoach.repository.UserRepository;

@Service
@Named
public class UserServiceImp implements UserService {
	@Inject
	private UserRepository userRepository;

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User create(User u) {
		return userRepository.save(u);
	}

	@Override
	public ResponseEntity<User> read(Long id) {
		Optional<User> u = userRepository.findById(id);
		return u.isPresent() ? ResponseEntity.ok().body(u.get()) : ResponseEntity.notFound().build();
	}

	@Override
	public String update(User u, Long id) {
		Optional<User> user = userRepository.findById(id);
		if(user.get().getId() == id) {
			userRepository.save(u);
			return "Update is ok";
		} else {
			return "Update impossible because this user does not exist.";
		}
	}

	@Override
	public String delete(Long id) {
		userRepository.deleteById(id);
		return "Your user has been deleted.";
	}

	@Override
	public User findByFirstName(String name) {
		return userRepository.findByFirstName(name);
	}

}
