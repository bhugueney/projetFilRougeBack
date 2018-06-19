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

import com.myIGCoach.models.Meal;
import com.myIGCoach.service.MealService;

@RestController
@RequestMapping("/meal")
public class MealController {
	@Inject
	private MealService mealService;
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Meal> findAll() {
		return mealService.findAll();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ResponseBody
	public ResponseEntity<Meal> findById(@PathVariable("id") Long id) {
		return mealService.read(id);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Meal create(@RequestBody Meal m) {
		return mealService.create(m);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public String update(@PathVariable("id") Long id, @RequestBody Meal m) {
		return mealService.update(m, id);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		return mealService.delete(id);
	}

}
