package com.myIGCoach.repository;

import java.util.List;
import java.util.Optional;

import javax.inject.Named;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myIGCoach.models.Meal;

@Repository
@Named
public interface MealRepository extends JpaRepository<Meal, Long> {
	// method to list meals since Owner id and if there are active
	public List<Meal> findByOwnerIdAndActiveIsTrue(Long userId);

	// method to find a meal and check that the user is the owner of it, and if
	// the meal is active
	public Optional<Meal> findByIdAndOwnerIdAndActiveIsTrue(Long id, Long userId);

	// method to find a meal and check that the user is the owner of it
	public Optional<Meal> findByIdAndOwnerId(Long id, Long userId);

}
