package com.myIGCoach.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.http.ResponseEntity;

import com.myIGCoach.models.Ingredient;
import com.myIGCoach.models.User;
import com.myIGCoach.repository.IngredientRepository;
import com.myIGCoach.repository.MealRepository;
import com.myIGCoach.repository.RecipeRepository;
import com.myIGCoach.repository.UserRepository;
import com.myIGCoach.tools.CheckList;

/*********************************************************************
 *********************************************************************
 * TODO the exception extract when user is an admin in findAll method
 * TODO the exception extract when user is an admin in read method
 *********************************************************************
 ********************************************************************/

@Named
public class IngredientServiceImp implements IngredientService {
	@Inject
	private IngredientRepository ingredientRepository;
	@Inject
	private RecipeRepository recipeRepository;
	@Inject
	private RecipeService recipeService;
	@Inject
	private MealRepository mealRepository;
	@Inject
	private MealService mealService;
	@Inject
	private UserRepository userRepository;
	@Inject
	private CheckList checkList;

	/**
	 * method to create an ingredient
	 * 
	 * @param i:
	 *            an ingredient
	 * @param userId:
	 *            user id do the request return ingredient created or null or
	 *            internet server error
	 */
	@Override
	public Ingredient create(Ingredient i, Long userId) {
		boolean check = checkList.checkNewIngredient(i, userId);
		if (check) {
			return ingredientRepository.save(i);
		} else {
			return null;
		}
	}

	/**
	 * method to list basics ingredients and ingredients of user
	 * 
	 * @param id:
	 *            user id do the request return list of ingredients or null or
	 *            internet server error
	 */
	@Override
	public List<Ingredient> findAll(Long id) {
		// TODO the exception extract when user is an admin
		List<Ingredient> list = new ArrayList<>();
		if (checkList.checkUserAdmin(id)) {
			list = ingredientRepository.findAll();
		} else {
			List<User> admin = userRepository.findByRole("ROLE_ADMIN");
			for (User user : admin) {
				list.addAll(ingredientRepository.findByOwnerIdAndActiveIsTrue(user.getId()));
			}
			list.addAll(ingredientRepository.findByOwnerIdAndActiveIsTrue(id));
		}
		return list;
	}

	/**
	 * method to read informations about a meal
	 * 
	 * @param id:
	 *            ingredient id
	 * @param userId:
	 *            user id do the request return the informations about it
	 */
	@Override
	public ResponseEntity<Ingredient> read(Long id, Long userId) {
		// TODO the exception extract when user is an admin
		Optional<Ingredient> i = null;
		if (checkList.checkUserAdmin(userId)) {
			i = ingredientRepository.findByIdAndActiveIsTrue(id);
		} else {
			i = ingredientRepository.findByIdAndOwnerIdAndActiveIsTrue(id, userId);
		}
		if (!i.isPresent()) {
			List<User> admin = userRepository.findByRole("ROLE_ADMIN");
			for (User user : admin) {
				i = ingredientRepository.findByIdAndOwnerIdAndActiveIsTrue(id, user.getId());
				if (i.isPresent()) {
					return ResponseEntity.ok().body(i.get());
				}
			}
		}
		return i.isPresent() ? ResponseEntity.ok().body(i.get()) : ResponseEntity.notFound().build();
	}

	/**
	 * method to update an ingredient if user is owner of it
	 * 
	 * @param id:
	 *            ingredient id
	 * @param resource:
	 *            ingredient with new informations
	 * @param userId:
	 *            user id do the request return ingredient updated or null or
	 *            internet server error
	 */
	@Override
	public Ingredient update(Long id, Ingredient resource, Long userId) {
		if (resource.getOwner() == null) {
			return null;
		}
		Optional<Ingredient> i = ingredientRepository.findByIdAndOwnerIdAndActiveIsTrue(id, userId);
		if (i.isPresent() && i.get().getId() == resource.getId()
				&& i.get().getOwner().getId() == resource.getOwner().getId()) {
			boolean check = checkList.checkIngredientInformations(resource, userId);
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
	 * method to delete an ingredient if user is owner of it
	 * 
	 * @param id:
	 *            ingredient id
	 * @param userId:
	 *            user id do the request return string about the result
	 */
	@Override
	public String delete(Long id, Long userId) {
		Optional<Ingredient> i = ingredientRepository.findByIdAndOwnerId(id, userId);
		if (i.isPresent()) {
			if (mealRepository.findById(i.get().getId()).isPresent()) {
				return mealService.delete(id, userId);
			} else if (recipeRepository.findById(i.get().getId()).isPresent()) {
				return recipeService.delete(id, userId);
			} else {
				if (i.get().getListOfRecipes().isEmpty()) {
					ingredientRepository.deleteById(id);
					return "Your ingredient has been removed.";
				} else {
					i.get().setActive(false);
					ingredientRepository.save(i.get());
					return "Your ingredient has been disconnected";
				}
			}
		} else {
			return "You couldn't remove this ingredient.";
		}
	}

}
