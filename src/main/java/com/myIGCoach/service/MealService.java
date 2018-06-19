package com.myIGCoach.service;

import java.util.List;

import javax.inject.Named;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.myIGCoach.models.Meal;

@Service
@Named
public interface MealService {
	public List<Meal> findAll();
	public Meal create(Meal m);
	public ResponseEntity<Meal> read(Long id);
	public String update(Meal m, Long id);
	public String delete(Long id);

}
