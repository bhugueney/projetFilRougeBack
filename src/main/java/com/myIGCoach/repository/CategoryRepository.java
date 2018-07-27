package com.myIGCoach.repository;

import java.util.List;
import java.util.Optional;

import javax.inject.Named;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myIGCoach.models.Category;

@Repository
@Named
public interface CategoryRepository extends JpaRepository<Category, Long> {
	// to find one or many categories by name
	public List<Category> findByName(String name);
	
	// Method to find a named category with given parent category
	public List<Category> findByNameAndParent(String name, Category parent);
	
	// method to find children of category
	public Optional<List<Category>> findByParentId(Long parentId);

	// method to get all categories without children
	public List<Category> findAllByListOfChildrenIsNullOrderByName();

}
