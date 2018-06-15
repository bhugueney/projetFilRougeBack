package com.myIGCoach.service;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.http.ResponseEntity;
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
	
	public ResponseEntity<Recipe> read(Long id) {
		Optional<Recipe> r = recipeRepository.findById(id);
		return r.isPresent() ? ResponseEntity.ok().body(r.get()) : ResponseEntity.notFound().build();
	}
	
	public String update(Recipe resource, Long id) {
		Optional<Recipe> r = recipeRepository.findById(id);
		if(r.get().getId() == id) {
			recipeRepository.save(resource);
			return "Update is ok";
		}else {
			return "Update impossible because this recipe does not exist.";
		}
	}

}
