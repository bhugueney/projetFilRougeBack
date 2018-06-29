package com.myIGCoach.service;

import java.util.List;

import javax.inject.Named;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.myIGCoach.models.Meal;

@Service
@Named
public interface MealService {
	// method to save meal
	public Meal create(Meal m, Long userId);

	// method to list meals of user
	public List<Meal> findAll(Long userId);

	// method to display meal details if user is owner
	public ResponseEntity<Meal> read(Long id, Long userId);

	// method to update a meal if user is owner
	public String update(Meal m, Long id, Long userId);

	// method to remove a meal if user is owner
	public String delete(Long id, Long userId);

}
