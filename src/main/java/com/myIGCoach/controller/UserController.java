package com.myIGCoach.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.myIGCoach.models.User;
import com.myIGCoach.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Inject
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<User> findAll() {
		return userService.findAll();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ResponseBody
	public ResponseEntity<User> read(@PathVariable("id") Long id) {
		return userService.read(id);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public User create(@RequestBody User u) {
		return userService.create(u);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	@ResponseBody
	public String update(@PathVariable("id") Long id, @RequestBody User u) {
		return userService.update(u, id);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	@ResponseBody
	public String delete(@PathVariable("id") Long id) {
		return userService.delete(id);
	}

}
