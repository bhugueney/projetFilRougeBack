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

import com.myIGCoach.models.Meal;
import com.myIGCoach.service.MealService;

/***************************************************
 ***************************************************
 * // TODO SPRING SECURITY ON PATH AND ON METHODS
 ***************************************************
 ***************************************************/

@RestController
@RequestMapping("/meals")
public class MealController {
	@Inject
	private MealService mealService;

	/**
	 * method to create a meal
	 * 
	 * @param m:
	 *            meal informations
	 * @param userId:
	 *            user id do the request
	 * @return meal created or null if it's KO or internet server error
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Meal create(@RequestBody Meal m, @RequestParam("userId") Long userId) {
		return mealService.create(m, userId);
	}

	/**
	 * method to list meals of user
	 * 
	 * @param userId:
	 *            user id do the request
	 * @return a list or an empty list if it's KO or internet server error
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Meal> findAll(@RequestParam("userId") Long userId) {
		return mealService.findAll(userId);
	}

	/**
	 * method to see details about a meal
	 * 
	 * @param id:
	 *            meal id
	 * @param userId:
	 *            user id do the request
	 * @return the informations or null if it's KO or internet server error
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ResponseBody
	public ResponseEntity<Meal> findById(@PathVariable("id") Long id, @RequestParam("userId") Long userId) {
		return mealService.read(id, userId);
	}

	/**
	 * method to update a meal
	 * 
	 * @param id:
	 *            meal id
	 * @param m:
	 *            meal with new informations
	 * @param userId:
	 *            user id do the request
	 * @return string about the result
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public String update(@PathVariable("id") Long id, @RequestBody Meal m, @RequestParam("userId") Long userId) {
		return mealService.update(m, id, userId);
	}

	/**
	 * method to delete a meal
	 * 
	 * @param id:
	 *            meal id
	 * @param userId:
	 *            user id do the request
	 * @return string about the result
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public String delete(@PathVariable("id") Long id, @RequestParam("userId") Long userId) {
		return mealService.delete(id, userId);
	}

}
