package com.myIGCoach.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.CascadeType;
//import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import com.fasterxml.jackson.annotation.JsonManagedReference;

//@MappedSuperclass
@Entity(name = "Recipe")
//@PrimaryKeyJoinColumn(name="id")
@Inheritance(strategy = InheritanceType.JOINED)
//@Table(name = "recipes")
public class Recipe extends Ingredient {
	private static final long serialVersionUID = 1L;
	
	/*@Id
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_recipe", nullable = false, foreignKey = @ForeignKey(name = "id_recipe"))
	private Ingredient recipeInIngredient;*/

	// a recipe contend a list of ingredients with their quantity
	//@Embedded
	@JsonManagedReference
	@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<QuantityRecipe> listOfIngredients = new ArrayList<>();
	
	// link between a recipe and a meal
		@OneToMany(mappedBy = "recipe")
		private List<Meal> listOfMeals = new ArrayList<>();
		
		
		
		/************************
		 * GETTERS AND SETTERS
		 ***********************/

		/**
		 * @return the listOfIngredients
		 */
		public List<QuantityRecipe> getListOfIngredients() {
			return listOfIngredients;
		}

		/**
		 * @param listOfIngredients the listOfIngredients to set
		 */
		public void setListOfIngredients(List<QuantityRecipe> listOfIngredients) {
			this.listOfIngredients = listOfIngredients;
		}

		/**
		 * @return the listOfMeals
		 */
		public List<Meal> getListOfMeals() {
			return listOfMeals;
		}

		/**
		 * @param listOfMeals the listOfMeals to set
		 */
		public void setListOfMeals(List<Meal> listOfMeals) {
			this.listOfMeals = listOfMeals;
		}
		
}
