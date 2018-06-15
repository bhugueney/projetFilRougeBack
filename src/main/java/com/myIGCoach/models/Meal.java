package com.myIGCoach.models;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "meals")
public class Meal extends Recipe {
	// id of meal
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "meal")
	@SequenceGenerator(name = "meal", sequenceName = "meal_seq", allocationSize = 1)
	@Column(name = "ID")
	private Long id;
	// type of meal since MealType class
	@Column(name = "mealType")
	private MealType mealType;
	// date of meal
	@Column(name = "date")
	private Date date;
	// link with details of preparation that corresponds to a recipe which corresponds at an ingredient
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_ingredient")
	private Recipe recipe;
	
	/************************
	 * GETTERS AND SETTERS
	 ***********************/

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the mealType
	 */
	public MealType getMealType() {
		return mealType;
	}

	/**
	 * @param mealType the mealType to set
	 */
	public void setMealType(MealType mealType) {
		this.mealType = mealType;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the recipe
	 */
	public Recipe getRecipe() {
		return recipe;
	}

	/**
	 * @param recipe the recipe to set
	 */
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	
}
