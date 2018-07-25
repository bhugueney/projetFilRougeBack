package com.myIGCoach.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.myIGCoach.models.Ingredient;
import com.myIGCoach.models.Meal;
import com.myIGCoach.models.QuantityRecipe;
import com.myIGCoach.repository.MealRepository;
import com.myIGCoach.tools.CheckList;

@Service
@Named
public class MealServiceImp implements MealService {
	@Inject
	private MealRepository mealRepository;
	@Inject
	private IngredientService ingredientService;
	@Inject
	private CheckList checkList;

	/**
	 * method to create a meal
	 * 
	 * @param m:
	 *            a meal
	 * @param userId:
	 *            user id do the request return meal created or null or internet
	 *            server error
	 */
	@Override
	public Meal create(Meal m, Long userId) {
		if (checkList.checkNewMealInformation(m, userId)) {
			return mealRepository.save(m);
		} else {
			return null;
		}
	}

	/**
	 * method to find meals of user
	 * 
	 * @param userId:
	 *            user id do the request return a meals list or an empty list or
	 *            internet server error
	 */
	@Override
	public List<Meal> findAll(Long userId) {
		// TODO the exception extract when user is an admin
		return mealRepository.findByOwnerIdAndActiveIsTrue(userId);
	}

	/**
	 * method to read informations about a meal
	 * 
	 * @param id:
	 *            meal id
	 * @param userId:
	 *            user id do the request return the informations about it
	 */
	@Override
	public ResponseEntity<Meal> read(Long id, Long userId) {
		// TODO the exception extract when user is an admin
		Optional<Meal> m = mealRepository.findByIdAndOwnerIdAndActiveIsTrue(id, userId);
		return m.isPresent() ? ResponseEntity.ok().body(m.get()) : ResponseEntity.notFound().build();
	}

	/**
	 * method to update a meal if user is owner of it
	 * 
	 * @param m:
	 *            meal with new informations
	 * @param id:
	 *            meal id
	 * @param userId:
	 *            user id do the request return string about the result
	 */
	@Override
	public String update(Meal m, Long id, Long userId) {
		Optional<Meal> meal = mealRepository.findByIdAndOwnerIdAndActiveIsTrue(id, userId);
		if (meal.isPresent() && meal.get().getId() == m.getId() && m.getOwner().getId() == userId
				&& m.getOwner().getId() == meal.get().getOwner().getId() && !m.getListOfIngredients().isEmpty()
				&& checkList.checkMealUpdateInformations(m, userId)) {

			mealRepository.save(m);
			return "Update is ok";
		} else {
			return "Update impossible because this meal does not exist.";
		}
	}

	/**
	 * method to delete a meal if user is owner of it
	 * 
	 * @param id:
	 *            meal id
	 * @param userId:
	 *            user id do the request return string about the result
	 */
	@Override
	public String delete(Long id, Long userId) {
		Optional<Meal> meal = mealRepository.findByIdAndOwnerId(id, userId);
		if (meal.isPresent()) {
			if (meal.get().getListOfRecipes().isEmpty()) {
				List<Ingredient> list = new ArrayList<>();
				for (QuantityRecipe i : meal.get().getListOfIngredients()) {
					if (!i.getIngredient().isActive()) {
						list.add(i.getIngredient());
					}
				}
				mealRepository.delete(meal.get());
				for (Ingredient i : list) {
					ingredientService.delete(i.getId(), userId);
				}
				return "Your meal has been removed.";
			} else {
				meal.get().setActive(false);
				mealRepository.save(meal.get());
				return "Your meal has been desactived.";
			}
		} else {
			return "Sorry, this meal does not exists.";
		}
	}

}
