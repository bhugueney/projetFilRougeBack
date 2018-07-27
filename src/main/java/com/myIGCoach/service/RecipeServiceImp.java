package com.myIGCoach.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

	@PersistenceContext
	EntityManager entityManager;

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
		
		System.out.println("");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		System.out.println("------------- " + timestamp + "  Recipe Service Create -----------------------");
		System.out.println("Recipe received : " + r);
		
		
		// Getting user information to inject owner information in ingredient instance
		Optional<User> owner = this.userRepository.findById(userId);
		if (!owner.isPresent()) {
			System.err.println("RecipeService create : Error : user not found");
			return new ResponseEntity<Recipe>(HttpStatus.PRECONDITION_FAILED);
		}
		r.setOwner(owner.get());
		
		
		boolean check = checkList.checkRecipeInformation(r, userId);
		if (check) {			
			System.out.println("RecipeService create : checkRecipeInformation OK");
			try {
				final Recipe createdRecipe = recipeRepository.save(r);
				System.out.println("RecipeService create : recipe return by recipeRepository.save : " + createdRecipe);
				return new ResponseEntity<Recipe>(createdRecipe, HttpStatus.OK);
			} catch( Exception e) {
				System.err.println("RecipeService create : recipeRepository.save returned an error");
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
	public ResponseEntity<Recipe> update(Recipe resource, Long id, Long userId) {
		Optional<Recipe> r = recipeRepository.findByIdAndOwnerIdAndActiveIsTrue(id, userId);
		
		System.out.println("");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		System.out.println("------------- " + timestamp + "  Recipe Service Update -----------------------");
		System.out.println("Recipe received : " + resource);
		System.out.println("Id asked to update : " + id);
		System.out.println("UserId : " + userId);
		
		
		// check if a recipe has been found
		if (r.isPresent()) {
			// recipe found
			
			System.out.println("Recipe found in base : " + r.get());
			
			// check if founded id is equal than asked resource id
			Recipe foundRecipe = r.get();

			// Use of Spring Context entity Manager to detach foundRecipe
			entityManager.detach(foundRecipe);

			long foundRecipeId = foundRecipe.getId();
			long resourceId = resource.getId();

			if (foundRecipeId == resourceId) {
				// id found is equal than asked id
									
				// check if list of ingredients  contains ingredients
				if (!resource.getListOfIngredients().isEmpty()) {
					// list of ingredients contains ingredients		
					
					resource.setOwner(foundRecipe.getOwner()); // set the owner in the asked update because it is not gived by front for security reasons
					
					try {
						final Recipe updatedRecipe = recipeRepository.save(resource);
						
						System.out.println("RecipeService update: Update is ok, updated recipe = " + updatedRecipe);
						return new ResponseEntity<Recipe>(updatedRecipe, HttpStatus.OK);
						
						
					} catch( Exception e) {
						System.err.println("RecipeService update: recipeRepository.update returned an error");
						e.printStackTrace(System.err);
						return new ResponseEntity<Recipe>(HttpStatus.INTERNAL_SERVER_ERROR);
					}
					
				} else {
					// list of ingredients is empty !
					System.err.println("RecipeService update: Update impossible because this recipe doesnt contains ingredients.");
					return new ResponseEntity<Recipe>(HttpStatus.PRECONDITION_FAILED);
				}
							
				
			} else {
				// id found is different than asked id
				System.err.println("RecipeService update: Update impossible because recip id founded in base (" + foundRecipeId + ")  is diferent from asked update resource recip id (" + resourceId + ") !");
				return new ResponseEntity<Recipe>(HttpStatus.PRECONDITION_FAILED);
			}
			
		} else {
			// recipe not found
			System.err.println("RecipeService update: Update impossible because recipe id " + id + " not found for user id " + userId + ".");
			return ResponseEntity.notFound().build();
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
					return "{\"message\":\"Your recipe has been removed.\"}";
				} else {
					r.get().setActive(false);
					recipeRepository.save(r.get());
					return "{\"message\":\"Your recipe has been desactived.\"}";
				}
			}
		} else {
			return "{\"message\":\"Sorry, this recipe does not exists.\"}";
		}
	}

}
