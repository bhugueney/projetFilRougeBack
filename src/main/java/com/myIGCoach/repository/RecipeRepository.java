package com.myIGCoach.repository;

import javax.inject.Named;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myIGCoach.models.Recipe;
import java.util.List;
import java.util.Optional;

@Repository
@Named
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
	// method to list recipes since Owner id and if there are active
	public List<Recipe> findByOwnerIdAndActiveIsTrue(Long userId);

	// method to find a recipe and check that the user is the owner of it, and if
	// the recipe is active
	public Optional<Recipe> findByIdAndOwnerIdAndActiveIsTrue(Long id, Long userId);

	// method to find a recipe and check that the user is the owner of it
	public Optional<Recipe> findByIdAndOwnerId(Long id, Long userId);

}
