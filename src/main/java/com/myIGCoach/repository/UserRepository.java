package com.myIGCoach.repository;

import java.util.Optional;

import javax.inject.Named;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myIGCoach.models.User;
import java.lang.String;
import java.util.List;

@Repository
@Named
public interface UserRepository extends JpaRepository<User, Long> {
	// method to find a user since email
	public Optional<User> findByEmail(String email);
		
	// method to find a user since a role
	public List<User> findByRole(String role);

	// method to get a user by id
	public Optional<User> findById(Long id);

}
