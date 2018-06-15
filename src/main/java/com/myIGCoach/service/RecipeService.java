package com.myIGCoach.service;

import java.util.List;

import javax.inject.Named;

import org.springframework.stereotype.Service;

import com.myIGCoach.models.Recipe;

@Named
@Service
public interface RecipeService {
	public List<Recipe> findAll();
	public Recipe create(Recipe r);

}
