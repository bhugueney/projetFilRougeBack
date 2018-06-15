package com.myIGCoach.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;

import com.myIGCoach.models.Recipe;
import com.myIGCoach.repository.RecipeRepository;

@Named
@Service
public class RecipeServiceImp implements RecipeService {
	@Inject
	RecipeRepository recipeRepository;
	
	public List<Recipe> findAll() {
		return recipeRepository.findAll();
	}
	
	public Recipe create(Recipe r) {
		return recipeRepository.save(r);
	}

}
