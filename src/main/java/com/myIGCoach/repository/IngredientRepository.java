package com.myIGCoach.repository;

import java.util.List;
import java.util.Optional;

import javax.inject.Named;

import org.springframework.stereotype.Repository;

import com.myIGCoach.models.Ingredient;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
@Named
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
	// method to list ingredients with owner Id and if ingredients are active
	public List<Ingredient> findByOwnerIdAndActiveIsTrue(Long id);

	// method to find an ingredient and check that user is the owner of it and it's
	// active
	public Optional<Ingredient> findByIdAndOwnerIdAndActiveIsTrue(Long id, Long userId);

	// method to find an ingredient and check that user is the owner of it
	public Optional<Ingredient> findByIdAndOwnerId(Long id, Long userId);

	// method to find an ingredient without control on Owner, only if there is
	// active
	public Optional<Ingredient> findByIdAndActiveIsTrue(Long id);

	// method to find one or many ingredients by name
	public List<Ingredient> findByName(String name);

}
