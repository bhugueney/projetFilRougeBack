package com.myIGCoach.repository;

import javax.inject.Named;

import org.springframework.stereotype.Repository;

import com.myIGCoach.models.Ingredient;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
@Named
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

}
