package com.myIGCoach.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.myIGCoach.models.Ingredient;
import com.myIGCoach.models.QuantityRecipe;
import com.myIGCoach.models.Recipe;
import com.myIGCoach.models.User;
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
@Service
public class RecipeServiceImp implements RecipeService {
	@Inject
	private RecipeRepository recipeRepository;
	@Inject
	private IngredientService ingredientService;
	@Inject
	private MealRepository mealRepository;
	@Inject
	private MealService mealService;
	@Inject
	private UserRepository userRepository;
	@Inject
	private CheckList checkList;
	

	/**
	 * method to create a recipe
	 * 
	 * @param r:
	 *            a recipe
	 * @param userId:
	 *            user id do the request
	 * @return recipe created or null or internet server error
	 */
	@Override
	public ResponseEntity<Recipe> create(Recipe r, Long userId) {
		
		// Getting user information to inject owner information in ingredient instance
		Optional<User> owner = this.userRepository.findById(userId);
		if (!owner.isPresent()) {
			return new ResponseEntity<Recipe>(HttpStatus.PRECONDITION_FAILED);
		}

		r.setOwner(owner.get());

		
		System.out.println("");
		System.out.println("------------- Recipe Service Create -----------------------");
		System.out.println("Recipe received : " + r);
		
		boolean check = checkList.checkRecipeInformation(r, userId);
		if (check) {			
			System.out.println("RecipeService create : checkRecipeInformation OK");
			try {
				final Recipe createdRecipe = recipeRepository.save(r);
				System.out.println("RecipeService create : recipe return by repo save : " + createdRecipe);
				return new ResponseEntity<Recipe>(createdRecipe, HttpStatus.OK);
			} catch( Exception e) {
				e.printStackTrace(System.err);
				return new ResponseEntity<Recipe>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			System.err.println("RecipeService create : checkRecipeInformation NOK");
			return new ResponseEntity<Recipe>(HttpStatus.PRECONDITION_FAILED);
		}
	}

	
	/**
	 * method to list recipes of user
	 * 
	 * @param userId:
	 *            user id do the request return a recipes list
	 * @return a recipes list or an empty list or internet server error
	 */
	@Override
	public List<Recipe> findAll(Long userId) {
		// TODO the exception extract when user is an admin
		return recipeRepository.findByOwnerIdAndActiveIsTrue(userId);
	}

	/**
	 * method to read the informations of a recipe
	 * 
	 * @param id:
	 *            recipe id
	 * @param userId:
	 *            user id do the request
	 * @return the informations about it
	 */
	@Override
	public ResponseEntity<Recipe> read(Long id, Long userId) {
		// TODO the exception extract when user is an admin
		Optional<Recipe> r = recipeRepository.findByIdAndOwnerIdAndActiveIsTrue(id, userId);
		return r.isPresent() ? ResponseEntity.ok().body(r.get()) : ResponseEntity.notFound().build();
	}

	/**
	 * method to update a recipe
	 * 
	 * @param resource:
	 *            recipe with new informations
	 * @param id:
	 *            recipe id to update
	 * @param userId:
	 *            user id do the request return string about the result
	 */
	@Override
	public String update(Recipe resource, Long id, Long userId) {
		Optional<Recipe> r = recipeRepository.findByIdAndOwnerIdAndActiveIsTrue(id, userId);
		if (r.isPresent() && r.get().getId() == resource.getId() && resource.getOwner().getId() == userId
				&& !resource.getListOfIngredients().isEmpty()) {
			recipeRepository.save(resource);
			return "Update is ok";
		} else {
			return "Update impossible because this recipe does not exist.";
		}
	}

	/**
	 * method to delete a recipe if user is owner of it
	 * 
	 * @param id:
	 *            recipe id
	 * @param userId:
	 *            user id do the request return string about the result
	 */
	@Override
	public String delete(Long id, Long userId) {
		Optional<Recipe> r = recipeRepository.findByIdAndOwnerId(id, userId);
		if (r.isPresent()) {
			if (mealRepository.findById(r.get().getId()).isPresent()) {
				return mealService.delete(id, userId);
			} else {
				if (r.get().getListOfRecipes().isEmpty()) {
					List<Ingredient> list = new ArrayList<>();
					for (QuantityRecipe i : r.get().getListOfIngredients()) {
						if (!i.getIngredient().isActive()) {
							list.add(i.getIngredient());
						}
					}
					recipeRepository.delete(r.get());
					for (Ingredient i : list) {
						ingredientService.delete(i.getId(), userId);
					}
					return "Your recipe has been removed.";
				} else {
					r.get().setActive(false);
					recipeRepository.save(r.get());
					return "Your recipe has been desactived.";
				}
			}
		} else {
			return "Sorry, this recipe does not exists.";
		}
	}

}
