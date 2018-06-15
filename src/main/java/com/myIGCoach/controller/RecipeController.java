package com.myIGCoach.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
		System.out.println(r.toString());
		return recipeService.create(r);
	}

}
