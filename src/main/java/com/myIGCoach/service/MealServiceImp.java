package com.myIGCoach.service;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.myIGCoach.models.Meal;
import com.myIGCoach.repository.MealRepository;

@Service
@Named
public class MealServiceImp implements MealService {
	@Inject
	private MealRepository mealRepository;

	@Override
	public List<Meal> findAll() {
		return mealRepository.findAll();
	}

	@Override
	public Meal create(Meal m) {
		return mealRepository.save(m);
	}

	@Override
	public ResponseEntity<Meal> read(Long id) {
		Optional<Meal> m = mealRepository.findById(id);
		return m.isPresent() ? ResponseEntity.ok().body(m.get()) : ResponseEntity.notFound().build();
	}

	@Override
	public String update(Meal m, Long id) {
		Optional<Meal> meal = mealRepository.findById(id);
		if(meal.get().getId() == id) {
			mealRepository.save(m);
			return "Update is ok";
		} else {
			return "Update impossible because this meal does not exist.";
		}
	}

	@Override
	public String delete(Long id) {
		mealRepository.deleteById(id);
		return "Your meal has been deleted.";
	}

}
