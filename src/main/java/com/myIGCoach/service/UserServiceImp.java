package com.myIGCoach.service;

import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.myIGCoach.models.Ingredient;
import com.myIGCoach.models.Recipe;
import com.myIGCoach.models.User;
import com.myIGCoach.repository.UserRepository;
import com.myIGCoach.tools.AuthenticationUtil;
import com.myIGCoach.tools.CheckList;
import com.myIGCoach.tools.authenticationToken;

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
	@Inject
	private AuthenticationUtil authenticationUtil;

	/**
	 * method to save a user
	 * 
	 * @param u: user u return user created or null or internet server error
	 */
	@Override
	public User create(User u) {
		if (checkList.checkNewUser(u)) {
			System.out.println("new User : " + u.getEmail());
			u.setPassword(Integer.toString(u.getPassword().hashCode()));
			return userRepository.save(u);
		} else {
			System.out.println(u.getEmail() + " is already existing.");
			return null;
		}
	}

	/**
	 * method to read informations about a user
	 * 
	 * @param id: user id return user informations
	 */
	@Override
	public ResponseEntity<User> read(Long id) {
		Optional<User> u = userRepository.findById(id);
		return u.isPresent() ? ResponseEntity.ok().body(u.get()) : ResponseEntity.notFound().build();
	}

	/**
	 * method to update informations (firstName & lastName) about a user
	 * 
	 * @param u: user u
	 * @param userId: user id do the request return string about the result
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
	 * @param u: user id to delete
	 * @param userId: user id do the request return string about the result
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

	/**
	 * method to read informations about a user
	 * 
	 * @param email: user email return user informations
	 */
	@Override
	public ResponseEntity<User> findByEmail(String email) {
		// System.out.println("findByEmail email =" + email);
		Optional<User> u = userRepository.findByEmail(email);
		// System.out.println("user found : " + u);
		return u.isPresent() ? ResponseEntity.ok().body(u.get()) : ResponseEntity.notFound().build();
	}

	/**
	 * method to do authenticate a user by his email and password
	 * 
	 * @param email: user email
	 * 
	 *        return user informations
	 */
	public ResponseEntity<User> authenticate(String base64EncodedjsonToken) {
		ResponseEntity<User> resultResponse;
		Gson g = new Gson();

		System.out.println("authenticate base64EncodedjsonToken = " + base64EncodedjsonToken);

		try {
			// decode base64EncodedjsonToken
			String jsonToken = new String(Base64.getDecoder().decode(base64EncodedjsonToken));
			System.out.println("authenticate json token = " + jsonToken);

			try {
				// convert jsonToken string to authenticationToken object
				authenticationToken authtoken = g.fromJson(jsonToken, authenticationToken.class);
				System.out.println("authenticate authtoken = " + authtoken);

				// System.out.println("authenticate email =" + email);
				Optional<User> u = userRepository.findByEmail(authtoken.getEmail());

				if (u.isPresent()) {
					User responseUser = u.get();
					System.out.println("user found : " + responseUser.getEmail() + " " + responseUser.getPassword());

					// If user found
					String salt = authtoken.getEmail(); // TODO a remplacer plus tard par un sel stocké dans le user et
														// calculé à la création du user.
					try {
						// First decode password received (password should be send by front in base64
						// encoded format
						// String decodedPassword = new String(Base64.getDecoder().decode(authtoken.getPassword()));
						// System.out.println("authenticate decodedPassword =" + decodedPassword);

						// Verify authentication by recompute secure password and compare with stored
						// password
						String secureUserPassword = authenticationUtil.generateSecurePassword(authtoken.getPassword(), salt);
						System.out.println("password generated : " + secureUserPassword);
						System.out.println("password stored    : " + responseUser.getPassword());

						if (secureUserPassword != null
								&& secureUserPassword.equalsIgnoreCase(responseUser.getPassword())) {
							// if secure password is equals to stored password
							// authentication is succeed, hide sensitive information before send response
							System.out.println("Authentication succeed");
							responseUser.setPassword(""); // Hide password in response
							responseUser.setIngredients(null); // Hide ingredients in response
							resultResponse = ResponseEntity.ok().body(responseUser);
						} else {
							// authentication fail response with 401 (unauthorized status)
							System.out.println("Authentication fail");
							resultResponse = ResponseEntity.status(401).build();
						}

					} catch (InvalidKeySpecException e) {
						// an error occur when computing secure password
						e.printStackTrace();
						resultResponse = ResponseEntity.status(500).build();
					}
				} else {
					// If user not found
					resultResponse = ResponseEntity.notFound().build();
				}
			} catch (Exception e) {
				// an error occur when decoding json token
				e.printStackTrace();
				resultResponse = ResponseEntity.status(500).build();
			}
		} catch (Exception e) {
			// an error occur when decoding base64EncodedjsonToken
			e.printStackTrace();
			resultResponse = ResponseEntity.status(500).build();
		}

		return resultResponse;
	}

}
