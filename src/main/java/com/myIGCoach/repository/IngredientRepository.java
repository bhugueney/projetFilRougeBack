package com.myIGCoach.repository;

import java.util.List;
import java.util.Optional;

import javax.inject.Named;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.myIGCoach.models.Ingredient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

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

	public List<Ingredient> findByNameContainingAndActiveIsTrueAndOwnerEmail(String name, String ownerEMail);

	// method to find an ingredient by name and owner email
	public List<Ingredient> findByNameAndActiveIsTrueAndOwnerEmail(String name, String ownerEMail);

	// method to delete ingredient without Glycemic Index
	@Modifying
	@Transactional
	@Query("delete from Ingredient i where i.glycemicIndex is null")
	public void deleteIngredientWithoutGlycemicIndex();

	
	// method to find list of active ingredients since a category
	public Optional<List<Ingredient>> findByCategoryIdAndActiveIsTrue(Long catId);

	
	// method to find list of active ingredients since a category and Owner
	public Optional<List<Ingredient>> findByCategoryIdAndOwnerIdAndActiveIsTrue(Long catId, Long id);

}
