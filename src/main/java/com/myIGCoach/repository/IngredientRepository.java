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
	// to find list of ingredients with owner Id and if the ingredient active on
	public List<Ingredient> findByOwnerIdAndActiveIsTrue(Long id);

	// to find an ingredient by id and owner id and active on
	public Optional<Ingredient> findByIdAndOwnerIdAndActiveIsTrue(Long id, Long userId);

}
