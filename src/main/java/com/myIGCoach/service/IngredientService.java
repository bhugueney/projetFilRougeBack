package com.myIGCoach.service;

import java.util.List;

import javax.inject.Named;

import org.springframework.http.ResponseEntity;

import com.myIGCoach.models.Ingredient;

@Named
public interface IngredientService {
	public Ingredient create(Ingredient i); // to save an ingredient
	public List<Ingredient> findAll(Long id); // to list ingredients globally 
	// TODO method to list only users's ingredients
	// TODO method to list ingredients since a category
	public ResponseEntity<Ingredient> read(Long id, Long userId); // to display details of an ingredient
	public Ingredient update(Long id, Ingredient resource, Long userId); // to modify an ingredient
	public String delete(Long id, Long userId); // to delete an ingredient

}
