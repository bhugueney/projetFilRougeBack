package com.myIGCoach.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.myIGCoach.models.Recipe;
import com.myIGCoach.service.RecipeService;

@RestController
@RequestMapping("/recipe")
public class RecipeController {
	@Inject
	RecipeService recipeService;
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Recipe> findAll() {
		return recipeService.findAll();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public Recipe create(@RequestBody Recipe r) {
		return recipeService.create(r);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Recipe> read(@PathVariable("id") Long id) {
		return recipeService.read(id);
	}
	
	// TODO management of update to respect the history
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public String update(@PathVariable("id") Long id, @RequestBody Recipe resource) {
		return recipeService.update(resource, id);
	}
	
	
	// TODO management of delete to respect the history
	/*
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") Long id) {
		return ingredientService.delete(id);
	}
	*/

}
