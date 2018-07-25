package com.myIGCoach.controller;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.myIGCoach.models.User;
import com.myIGCoach.service.UserService;

/***************************************************
 ***************************************************
 * TODO SPRING SECURITY ON PATH AND ON METHODS
 * TODO correction about method delete (meal including meal, recipe including recipe)
 * TODO a control about method to delete when we want to delete an admin
 * TODO service to change email
 * TODO service to change password
 * TODO service to list all users for admin only
 ***************************************************
 ***************************************************/

@RestController
@RequestMapping("/users")
public class UserController {
	@Inject
	private UserService userService;

	/**
	 * method to create a user (default role = ROLE_USER)
	 * 
	 * @param u:
	 *            a user
	 * @return the user created or null if KO or internet server error
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public User create(@RequestBody User u) {
		return userService.create(u);
	}

	/**
	 * method to see details about user
	 * 
	 * @param id:
	 *            user id
	 * @return details about user or null or internet server error
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<User> read(@RequestParam("userId") Long userId) {
		return userService.read(userId);
	}

	
	/**
	 * method to update the details of user (firstName, LastName)
	 * 
	 * @param id:
	 *            user id
	 * @param u:
	 *            user with new informations
	 * @return string about the result
	 */
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseBody
	public String update(@RequestParam("userId") Long userId, @RequestBody User u) {
		return userService.update(userId, u);
	}

	/**
	 * method to delete a user
	 * 
	 * @param id:
	 *            user id
	 * @return string about the result
	 */
	@RequestMapping(value = "/{user}", method = RequestMethod.DELETE)
	@ResponseBody
	public String delete(@PathVariable("user") Long u, @RequestParam("userId") Long userId) {
		return userService.delete(u, userId);
	}

	/**
	 * method to authenticate an user by email and hashpassword
	 * 
	 * @param email:
	 *            user email
	 *            
	 * @param password:
	 *            user password base64 encoded
	 *            
	 * @return details about user or null or internet server error
	 */
	@RequestMapping(value = "/authenticate", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<User> authenticate(@RequestParam("authToken") String authToken) {
		System.out.println("user authenticate " + authToken );
		return userService.authenticate(authToken);
	}
	
}
