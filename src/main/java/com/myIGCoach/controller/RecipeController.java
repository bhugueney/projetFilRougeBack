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
import com.myIGCoach.models.Recipe;
import com.myIGCoach.service.RecipeService;

/***************************************************
 ***************************************************
 * // TODO SPRING SECURITY ON PATH AND ON METHODS
 ***************************************************
 ***************************************************/

@RestController
@RequestMapping("/recipes")
public class RecipeController {
	@Inject
	RecipeService recipeService;

	/**
	 * method to create a recipe
	 * 
	 * @param userId:
	 *            user id
	 * @param r:
	 *            recipe informations
	 * @return recipe created or null if KO or internet server error
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Recipe> create(@RequestBody Recipe r, @RequestParam("userId") Long userId) {
		return recipeService.create(r, userId);
	}

	/**
	 * method to list all recipes about user
	 * 
	 * @param userId:
	 *            user id
	 * @return list of recipe or an empty list if it's KO or internet server error
	 */
	@RequestMapping(method = RequestMethod.GET)
	public List<Recipe> findAll(@RequestParam("userId") Long userId) {
		return recipeService.findAll(userId);
	}

	/**
	 * method to return details about a recipe
	 * 
	 * @param id:
	 *            recipe id concerned
	 * @param userId:
	 *            user id do request
	 * @return the informations about it or null if it's KO or internet server error
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Recipe> read(@PathVariable("id") Long id, @RequestParam("userId") Long userId) {
		return recipeService.read(id, userId);
	}

	
	
	
	/**
	 * method to update a recipe
	 * 
	 * @param id:
	 *            recipe id
	 * @param resource:
	 *            recipe with new informations
	 * @param userId:
	 *            user id do request
	 * @return string about the result
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Recipe> update(@PathVariable("id") Long id, @RequestBody Recipe resource,
			@RequestParam("userId") Long userId) {
		return recipeService.update(resource, id, userId);
	}

	
	
	
	
	/**
	 * method to delete a recipe
	 * 
	 * @param id:
	 *            recipe id
	 * @param userId:
	 *            user id do request
	 * @return string about the result
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public String delete(@PathVariable("id") Long id, @RequestParam("userId") Long userId) {
		return recipeService.delete(id, userId);
	}

}
