package com.myIGCoach.repository;

import javax.inject.Named;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myIGCoach.models.Recipe;

@Repository
@Named
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
	//public Recipe create(Recipe r);

}
