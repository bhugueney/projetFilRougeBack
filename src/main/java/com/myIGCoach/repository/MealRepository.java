package com.myIGCoach.repository;

import javax.inject.Named;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myIGCoach.models.Meal;

@Repository
@Named
public interface MealRepository extends JpaRepository<Meal, Long>{

}
