package com.myIGCoach.controller;

import java.util.List;


import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.myIGCoach.models.Ingredient;
import com.myIGCoach.service.IngredientService;


/***************************************************
 ***************************************************
// TODO SPRING SECURITY ON PATH AND ON METHODS 
// TODO method to list only users's ingredients
// TODO method to list ingredients since a category
 ***************************************************
***************************************************/

@RestController
public class IngredientController {
	@Inject
	IngredientService ingredientService;
	
	/**
	 * Request POST to save an ingredient
	 * 
	 * @param i:
	 *            an ingredient
	 * @return this ingredient if it's OK or null it's KO or Internet server error
	 *         if there is a problem to save
	 */
	@RequestMapping(value = "/ingredient", method = RequestMethod.POST)
	@ResponseBody
	public Ingredient create(@RequestBody Ingredient i) {
		return ingredientService.create(i);
	}

	/**
	 * Request simple GET to list the ingredients
	 * 
	 * @param id:
	 *            this is the user id to do the request
	 * @return the global list that contains basics ingredients & user's ingredients
	 */
	@RequestMapping(value = "/ingredients", method = RequestMethod.GET)
	@ResponseBody
	public List<Ingredient> findAll(@RequestParam("userId") Long id) {
		return ingredientService.findAll(id);
	}

	/**
	 * Request GET with a variable id of the ingredient and take a parameter userId
	 * 
	 * @param id
	 *            : variable to designate the id of ingredient we must return
	 * @param userId
	 *            : contains the id of user who do the request
	 * @return the details of ingredient
	 */
	@RequestMapping(value = "ingredient/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Ingredient> read(@PathVariable("id") Long id, @RequestParam("userId") Long userId) {
		return ingredientService.read(id, userId);
	}

	/**
	 * Request PUT with a variable id of ingredient and take a parameter userId.
	 * This request to modify an ingredient that already exists
	 * 
	 * @param id
	 *            : variable to designate the id of ingredient we must modify
	 * @param resource
	 *            : the new details of ingredient
	 * @param userId
	 *            : contains the id of user who do the request
	 * @return this ingredient if it's OK or null it's KO or Internet server error
	 *         if there is a problem to save
	 */
	@RequestMapping(value = "ingredient/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Ingredient update(@PathVariable("id") Long id, @RequestBody Ingredient resource,
			@RequestParam("userId") Long userId) {
		return ingredientService.update(id, resource, userId);
	}

	/**
	 * Request DELETE with a variable id of ingredient and take a parameter userId.
	 * This request to remove an ingredient
	 * 
	 * @param id:
	 *            variable to designate the id of ingredient we must remove
	 * @param userId:
	 *            contains the id of user who do the request
	 * @return a string that inform about result of this request
	 */
	@RequestMapping(value = "/ingredient/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public String delete(@PathVariable("id") Long id, @RequestParam("userId") Long userId) {
		return ingredientService.delete(id, userId);
	}

}
