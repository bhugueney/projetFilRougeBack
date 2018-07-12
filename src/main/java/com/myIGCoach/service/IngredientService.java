package com.myIGCoach.service;

import java.util.List;

import javax.inject.Named;

import org.springframework.http.ResponseEntity;

import com.myIGCoach.models.Ingredient;

/***************************************************
 ***************************************************
 * TODO method to list only users's ingredients
 * TODO method to list ingredients since a cat
 ***************************************************
 ***************************************************/

@Named
public interface IngredientService {
	// method to save an ingredient
	public Ingredient create(Ingredient i, Long userId);

	// to list basics ingredients and ingredients of user
	public List<Ingredient> findAll(Long id);
	
	// TODO method to list only users's ingredients

	// TODO method to list ingredients since a category

	// method to display details of an ingredient
	public ResponseEntity<Ingredient> read(Long id, Long userId);

	// method to modify an ingredient if user is the owner of it
	public ResponseEntity<Ingredient> update(Long id, Ingredient resource, Long userId);

	// method to delete an ingredient if user is the owner of it
	public String delete(Long id, Long userId);

}
