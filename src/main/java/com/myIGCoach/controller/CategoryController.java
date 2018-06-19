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

import com.myIGCoach.models.Category;
import com.myIGCoach.service.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
	@Inject
	private CategoryService categoryService;
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<Category> findAll() {
		return categoryService.findAll();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ResponseBody
	public ResponseEntity<Category> read(@PathVariable("id") Long id) {
		return categoryService.read(id);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Category create(@RequestBody Category c) {
		return categoryService.create(c);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	@ResponseBody
	public String update(@PathVariable("id") Long id, @RequestBody Category c) {
		return categoryService.update(c, id);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		return categoryService.delete(id);
	}

}
