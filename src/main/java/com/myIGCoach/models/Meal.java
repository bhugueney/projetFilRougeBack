package com.myIGCoach.models;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;

@Entity(name = "meals")
//@Inheritance(strategy = InheritanceType.JOINED)
//@Table(name = "meals")
public class Meal extends Recipe {
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

}
