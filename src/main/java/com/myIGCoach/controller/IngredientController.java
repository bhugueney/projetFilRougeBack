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

import com.myIGCoach.models.Ingredient;
import com.myIGCoach.service.IngredientService;


@RestController
@RequestMapping("/ingredient")
public class IngredientController {
	@Inject
	IngredientService ingredientService;
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Ingredient create(@RequestBody Ingredient i) {
		return ingredientService.create(i);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Ingredient> findAll() {
		return ingredientService.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Ingredient> read(@PathVariable("id") Long id) {
		return ingredientService.read(id);
	}
	
	// TODO management of update to respect the history
	/*
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public String update(@PathVariable("id") Long id, @RequestBody Ingredient resource) {
		return ingredientService.update(resource, id);
	}
	
	*/
	// TODO management of delete to respect the history
	/*
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") Long id) {
		return ingredientService.delete(id);
	}
	*/
}
