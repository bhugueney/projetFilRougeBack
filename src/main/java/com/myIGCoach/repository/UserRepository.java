package com.myIGCoach.repository;

import javax.inject.Named;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myIGCoach.models.User;

@Repository
@Named
public interface UserRepository extends JpaRepository<User, Long>{
	public User findByFirstName(String name);

}
