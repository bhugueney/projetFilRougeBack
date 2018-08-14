package com.myIGCoach.service;

import java.util.List;

import javax.inject.Named;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.myIGCoach.models.Recipe;

@Named
@Service
public interface RecipeService {
	// method to save a new recipe
	public ResponseEntity<Recipe> create(Recipe r, Long userId);

	// method to list all Recipe of user
	public List<Recipe> findAll(Long userId);

	// method to display details of a recipe
	public ResponseEntity<Recipe> read(Long id, Long userId);

	// method to update a recipe only if user is recipe's owner
	public ResponseEntity<Recipe> update(Recipe r, Long id, Long userId);

	// method to remove a recipe only if user is recipe's owner
	public String delete(Long id, Long userId);

}
