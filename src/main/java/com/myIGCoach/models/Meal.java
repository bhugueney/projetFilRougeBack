package com.myIGCoach.models;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "meals")
public class Meal extends Recipe {
	// used because this class is serialisable to do the foreignkey (via recipe
	// class) and primary key for QuantityRecipe class
	private static final long serialVersionUID = 1L;

	// type of meal since MealType class
	@Column(name = "mealType")
	private MealType mealType;

	// date of meal
	@Column(name = "date")
	private Date date;

	/************************
	 * GETTERS AND SETTERS
	 ***********************/

	/**
	 * @return the mealType
	 */
	public MealType getMealType() {
		return mealType;
	}

	/**
	 * @param mealType
	 *            the mealType to set
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
	 * @param date
	 *            the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

}
