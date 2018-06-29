package com.myIGCoach.service;

import java.util.List;

import javax.inject.Named;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.myIGCoach.models.Category;

/***************************************************
 ***************************************************
 * // TODO service to list only category of level 1
 ***************************************************
 ***************************************************/

@Service
@Named
public interface CategoryService {
	// method to save a new category (only ROLE_ADMIN)
	public Category create(Category c, Long userId);

	// method to find all categories
	public List<Category> findAll();

	// TODO service to list only category of level 1

	// method to find the informations about a specifically category
	public ResponseEntity<Category> read(Long id);

	// method to update a category (only ROLE_ADMIN)
	public String update(Category c, Long id, Long userId);

	// method to delete a category (only ROLE_ADMIN)
	public String delete(Long id, Long userId);

}
