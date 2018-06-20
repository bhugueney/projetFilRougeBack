package com.myIGCoach.service;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.http.ResponseEntity;

import com.myIGCoach.models.Ingredient;
import com.myIGCoach.models.User;
import com.myIGCoach.repository.IngredientRepository;
import com.myIGCoach.tools.CheckList;

@Named
public class IngredientServiceImp implements IngredientService {
	@Inject
	IngredientRepository ingredientRepository;
	@Inject
	UserService userService;

	/**
	 * this method make check before to save a new ingredient
	 */
	@Override
	public Ingredient create(Ingredient i) {
		boolean check = CheckList.checkIngredient(i);
		if (check) {
			return ingredientRepository.save(i);
		} else {
			return null;
		}
	}

	/**
	 * this method list the basics ingredients (admin's ingredient) and add the
	 * user's ingredient that do request. Result : the global list of ingredients
	 * that user can be use
	 */
	@Override
	public List<Ingredient> findAll(Long id) {
		User admin = userService.findByFirstName("ADMIN");
		List<Ingredient> list = ingredientRepository.findByOwnerIdAndActiveIsTrue(admin.getId());
		list.addAll(ingredientRepository.findByOwnerIdAndActiveIsTrue(id));
		return list;
	}

	/**
	 * this method control that the user is the owner of ingredient or if it's a
	 * basic ingredient. If it's OK and the ingredient is active to display, we done
	 * the details of it
	 */
	@Override
	public ResponseEntity<Ingredient> read(Long id, Long userId) {
		Optional<Ingredient> i = ingredientRepository.findByIdAndOwnerIdAndActiveIsTrue(id, userId);
		if (!i.isPresent()) {
			User admin = userService.findByFirstName("ADMIN");
			i = ingredientRepository.findByIdAndOwnerIdAndActiveIsTrue(id, admin.getId());
		}
		return i.isPresent() ? ResponseEntity.ok().body(i.get()) : ResponseEntity.notFound().build();
	}

	/**
	 * this method control that the user is the owner of ingredient before to make
	 * the change. And check the new data of this ingredient before to save
	 */
	@Override
	public Ingredient update(Long id, Ingredient resource, Long userId) {
		Optional<Ingredient> i = ingredientRepository.findByIdAndOwnerIdAndActiveIsTrue(id, userId);
		if (i.get().getId() == id && i.get().getOwner().getId() == userId) {
			boolean check = CheckList.checkIngredient(resource);
			if (check) {
				return ingredientRepository.save(resource);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * this method control that the user is the owner of ingredient before to remove it.
	 * If the ingredient is used in a recipe, it's just active off.
	 * if it's not used, it's removed definitely 
	 */
	@Override
	public String delete(Long id, Long userId) {
		Optional<Ingredient> i = ingredientRepository.findByIdAndOwnerIdAndActiveIsTrue(id, userId);
		if (i.get().getId() == id && i.get().getOwner().getId() == userId) {
			if (i.get().getListOfRecipes().isEmpty()) {
				ingredientRepository.deleteById(id);
				return "Your ingredient has been removed.";
			} else {
				i.get().setActive(false);
				ingredientRepository.save(i.get());
				return "Your ingredient has been disconnected";
			}
		} else {
			return "You couldn't remove this ingredient.";
		}
	}

}
