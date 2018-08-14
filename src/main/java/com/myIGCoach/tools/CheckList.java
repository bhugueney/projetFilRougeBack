package com.myIGCoach.tools;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import com.myIGCoach.models.Category;
import com.myIGCoach.models.Ingredient;
import com.myIGCoach.models.Meal;
import com.myIGCoach.models.Recipe;
import com.myIGCoach.models.User;
import com.myIGCoach.repository.CategoryRepository;
import com.myIGCoach.repository.IngredientRepository;
import com.myIGCoach.repository.UserRepository;

/**
 * this class serve to make controls on data
 * 
 * @author Frederick
 *
 */
@Named
public class CheckList {
	@Inject
	private IngredientRepository ingredientRepository;
	@Inject
	private UserRepository userRepository;
	@Inject
	private CategoryRepository categoryRepository;

	/**
	 * method to check that user exists since his email
	 * 
	 * @param u:
	 *            a user informations
	 * @return boolean
	 */
	public boolean checkUserExists(User u) {
		if (userRepository.findByEmail(u.getEmail()).isPresent()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * method to check the informations sending since a user before to be saved
	 * 
	 * @param u:
	 *            a user
	 * @return boolean
	 */
	public boolean checkUserInformations(User u) {
		if (u.getFirstName() != null && u.getFirstName() != "" && u.getLastName() != null && u.getLastName() != ""
				&& u.getEmail() != null && u.getEmail() != "" && u.getPassword() != null && u.getPassword() != "") {
			// TODO REGEX CONTROLLER ON FIRSTNAME, LASTNAME, EMAIL and maybe password
			return true;
		} else {
			return false;
		}
	}

	/**
	 * method to check if the creation of the user is possible or not (because user
	 * is used)
	 * 
	 * @param u:
	 *            a user
	 * @return boolean
	 */
	public boolean checkNewUser(User u) {
		if (!checkUserExists(u) && checkUserInformations(u)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * method to check that the user have the ROLE_ADMIN
	 * 
	 * @param userId:
	 *            user id do the request
	 * @return boolean
	 */
	public boolean checkUserAdmin(Long userId) {
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent() && user.get().getRole().equals("ROLE_ADMIN")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * method to check that ingredient exists since his id and his name and owner id
	 * 
	 * @param i:
	 *            an ingredient
	 * @return boolean
	 */
	public boolean checkIngredientExists(Ingredient i) {
		Optional<Ingredient> ingredient = null;
		if (i.getId() != null) {
			ingredient = ingredientRepository.findById(i.getId());

		}
		if (i.getName() != "" && i.getName() != null) {
			List<Ingredient> ingredients = ingredientRepository.findByName(i.getName());
			for (Ingredient ing : ingredients) {
				if (ing.getName().equals(i.getName())) {
					ingredient = ingredientRepository.findById(ing.getId());
				}
			}
		}
		if (ingredient != null && i.getOwner() != null && ingredient.isPresent()
				&& ingredient.get().getOwner().getId() == i.getOwner().getId()) {
			System.out.println("checkIngredientExists : " + i + " return True !");
			return true;
		} else {
			System.out.println("checkIngredientExists : " + i + " return False!");
			return false;
		}
	}

	/**
	 * method to check the informations sending since an ingredient before to be
	 * saved
	 * 
	 * @param i:
	 *            an ingredient
	 * @param id:
	 *            user id do the request
	 * @return boolean
	 */
	public boolean checkIngredientInformations(Ingredient i, Long id) {
		boolean result = false;
		if (i.getName() != null && i.getName() != "") {
			// TODO REGEX CONTROLLER ON STRING NAME
			if (i.getCategory() != null) {
				if (i.getCategory().getId() != null) {
					if (checkCategoryExists(categoryRepository.findById(i.getCategory().getId()).get())) {
						// TODO SECURITY CONTROL ON COMMENT
						// TODO SECURITY CONTROL ON IMAGE
						// TODO SECURITY CONTROL ON DATAS (not necessary with the type double)
						result = true;
						System.out.println("checkIngredientInformations : name and category OK");
						
					} else {
						System.err.println("Error checkIngredientInformations : return false because Category asked not found in database");
					}
					
				} else {
					System.err.println("Error checkIngredientInformations : return false because Category.Id is null");
				}
				
			} else {
				System.err.println("Error checkIngredientInformations : return false because Category is null");
			}
			
		} else {
			System.err.println("checkIngredientInformations : return false because no  name provided");
		}
			
		return result;
	}

	
	/**
	 * method to check if the creation of the ingredient is possible or not (because
	 * name is used)
	 * 
	 * @param i:
	 *            an ingredient
	 * @param id:
	 *            user id do the request
	 * @return boolean
	 */
	public boolean checkNewIngredient(Ingredient i, Long id) {
		if (!checkIngredientExists(i)) {
			System.out.println("checkNewIngredient : controle inexistance OK");
			if(checkIngredientInformations(i, id)) {
				System.out.println("checkNewIngredient : checkIngredientInformations is OK");
				return true;
			} else {
				System.err.println("checkNewIngredient : checkIngredientInformations is NOK");
				return false;
			}
			
		} else {
			System.err.println("Erreur sur checkNewIngredient : checkIngredientExists : retrun true" + i);
			return false;
		}
	}

	/**
	 * method to check that category exists since his id and his name
	 * 
	 * @param c:
	 *            a category
	 * @return boolean
	 */
	public boolean checkCategoryExists(Category c) {
		if (c.getId() != null && categoryRepository.existsById(c.getId())
				&& categoryRepository.findById(c.getId()).get().getName() == c.getName()) {
			return true;
		} else if (c.getName() != null && !categoryRepository.findByName(c.getName()).isEmpty()) {
			for (Category cat : categoryRepository.findByName(c.getName())) {
				if (cat.getParent() == null && c.getParent() == null) {
					return true;
				}
				if (cat.getParent() != null && c.getParent() != null
						&& cat.getParent().getId() == c.getParent().getId()) {
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}

	/**
	 * method to check the informations sending since a category before to be saved
	 * 
	 * @param c:
	 *            a category
	 * @return boolean
	 */
	public boolean checkCategoryInformations(Category c) {
		boolean result = false;
		if (c.getName() != null && c.getName() != "") {
			// TODO REGEX CONTROLLER ON STRING NAME
			if (c.getParent() != null) {
				Category parent = categoryRepository.findById(c.getParent().getId()).get();
				if (checkCategoryExists(parent)) {
					result = true;
				}
			} else {
				result = true;
			}
		}
		return result;
	}

	/**
	 * method to check if the creation of the category is possible or not (because
	 * name is used)
	 * 
	 * @param c:
	 *            a category
	 * @return boolean
	 */
	public boolean checkNewCategory(Category c) {
		if (!checkCategoryExists(c) && checkCategoryInformations(c)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * method to check the informations sending since a recipe before to be saved
	 * 
	 * @param r:
	 *            a recipe
	 * @param userId:
	 *            user id do the request
	 * @return boolean
	 */
	public boolean checkRecipeInformation(Recipe r, Long userId) {
		boolean result = false;
		if (checkNewIngredient(r, userId)) {
			System.out.println("checkRecipeInformation : check NewIngredient is OK");
			System.out.println("checkRecipeInformation : nb of ingredients = " + r.getListOfIngredients().size());
			if(!r.getListOfIngredients().isEmpty()) {
				System.out.println("checkRecipeInformation : test on ingredients list not empty is OK");
				// TODO control listOfIngredients contents
				result = true;
			} else {
				System.err.println("Erreur sur checkRecipeInformation : test on ingredients list not empty is bad");
				result = false;
			}
		} else {
			System.err.println("Erreur sur checkRecipeInformation : check NewIngredient is bad");
			result = false;
		}
		return result;
	}

	/**
	 * method to check the informations sending since a new meal before to be saved
	 * 
	 * @param m:
	 *            a new meal
	 * @param userId:
	 *            user id do the request
	 * @return boolean
	 */
	public boolean checkNewMealInformation(Meal m, Long userId) {
		if (checkRecipeInformation(m, userId) && m.getMealType() != null && m.getDate() != null) {
			// TODO ENUM control on MealType
			// TODO date control on format and good value
			return true;
		} else {
			return false;
		}
	}

	/**
	 * method to check the informations sending since a meal that want to update,
	 * before to be saved
	 * 
	 * @param m:
	 *            meal that we want to update
	 * @param userId:
	 *            user id do the request
	 * @return boolean
	 */
	public boolean checkMealUpdateInformations(Meal m, Long userId) {
		Optional<Ingredient> i = ingredientRepository.findById(m.getId());
		if (i.isPresent() && i.get().getOwner().getId() == userId && i.get().getOwner().getId() == m.getOwner().getId()
				&& checkIngredientInformations(m, userId) && m.getMealType() != null && m.getDate() != null) {
			return true;
		} else {
			return false;
		}
	}

}
