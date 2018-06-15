package com.myIGCoach.service;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.http.ResponseEntity;

import com.myIGCoach.models.Ingredient;
import com.myIGCoach.repository.IngredientRepository;

@Named
public class IngredientServiceImp implements IngredientService {
	@Inject
	IngredientRepository ingredientRepository;

	public Ingredient create(Ingredient i) {
		return ingredientRepository.save(i);
	}

	public List<Ingredient> findAll() {
		return ingredientRepository.findAll();
	}

	public ResponseEntity<Ingredient> read(Long id) {
		Optional<Ingredient> i = ingredientRepository.findById(id);
		return i.isPresent() ? ResponseEntity.ok().body(i.get()) : ResponseEntity.notFound().build();
	}
	
	public String update(Ingredient resource, Long id) {
		Optional<Ingredient> i = ingredientRepository.findById(id);
		if(i.get().getId() == id) {
			ingredientRepository.save(resource);
			return "Update is ok";
		}else {
			return "Update impossible because this ingredient does not exist.";
		}
	}
	
	public String delete(Long id) {
		ingredientRepository.deleteById(id);
		return "Your ingredient has been deleted.";
	}

}
