package com.myIGCoach.service;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.myIGCoach.models.Ingredient;
import com.myIGCoach.models.Recipe;
import com.myIGCoach.models.User;
import com.myIGCoach.repository.UserRepository;
import com.myIGCoach.tools.CheckList;

/***************************************************
 ***************************************************
 * TODO exception deleting of a user by an admin
 * TODO some constraints when user to delete is an admin
 ***************************************************
 ***************************************************/

@Service
@Named
public class UserServiceImp implements UserService {
	@Inject
	private UserRepository userRepository;
	@Inject
	private IngredientService ingredientService;
	@Inject
	private RecipeService recipeService;
	@Inject
	private CheckList checkList;

	/**
	 * method to save a user
	 * 
	 * @param u:
	 *            user u return user created or null or internet server error
	 */
	@Override
	public User create(User u) {
		if (checkList.checkNewUser(u)) {
			u.setPassword(Integer.toString(u.getPassword().hashCode()));
			return userRepository.save(u);
		} else {
			return null;
		}
	}

	/**
	 * method to read informations about a user
	 * 
	 * @param id:
	 *            user id return user informations
	 */
	@Override
	public ResponseEntity<User> read(Long id) {
		Optional<User> u = userRepository.findById(id);
		return u.isPresent() ? ResponseEntity.ok().body(u.get()) : ResponseEntity.notFound().build();
	}

	/**
	 * method to update informations (firstName & lastName) about a user
	 * 
	 * @param u:
	 *            user u
	 * @param userId:
	 *            user id do the request return string about the result
	 */
	@Override
	public String update(Long userId, User u) {
		Optional<User> user = userRepository.findById(userId);
		if (user.get().getId() == u.getId()) {
			boolean check = checkList.checkUserInformations(u);
			if (check) {
				u.setPassword(user.get().getPassword());
				u.setEmail(user.get().getEmail());
				userRepository.save(u);
				return "Update is ok";
			} else {
				return "Update is not done, user's informations seems not corect";
			}
		} else {
			return "Update impossible because this user does not exist.";
		}
	}

	/**
	 * method to delete a user and all elements that he've created
	 * 
	 * @param u:
	 *            user id to delete
	 * @param userId:
	 *            user id do the request return string about the result
	 */
	@Override
	public String delete(Long u, Long userId) {
		// TODO exception deleting of a user by an admin
		// TODO some constraints when user to delete is an admin
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent() && userRepository.findById(u).get().getEmail() == user.get().getEmail()) {
			List<Recipe> list2 = recipeService.findAll(u);
			for (Ingredient i : list2) {
				recipeService.delete(i.getId(), u);
			}
			List<Ingredient> list1 = ingredientService.findAll(u);
			for (Ingredient i : list1) {
				ingredientService.delete(i.getId(), u);
			}
			userRepository.deleteById(u);
			return "Your user, and his elements has been removed.";
		} else {
			return "Sorry, we can not remove your user.";
		}
	}

}
