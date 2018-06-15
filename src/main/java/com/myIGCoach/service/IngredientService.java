package com.myIGCoach.service;

import java.util.List;

import javax.inject.Named;

import org.springframework.http.ResponseEntity;

import com.myIGCoach.models.Ingredient;

@Named
public interface IngredientService {
	public Ingredient create(Ingredient i);
	public List<Ingredient> findAll();
	public ResponseEntity<Ingredient> read(Long id);
	public String update(Ingredient resource, Long id);
	public String delete(Long id);

}
