package com.myIGCoach.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
//import javax.persistence.CascadeType;
//import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

//@MappedSuperclass
@Entity(name = "Recipe")
@PrimaryKeyJoinColumn(name="id")
@Inheritance(strategy = InheritanceType.JOINED)
//@Table(name = "recipes")
public class Recipe extends Ingredient {
	
	
	/*@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingredient")
	@SequenceGenerator(name = "ingredient", sequenceName = "ingredient_seq", allocationSize = 1)
	@Column(name = "ID")
	private Long id;*/
	
	// a recipe contend a list of ingredients with their quantity
	//@Embedded
	@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<QuantityRecipe> listOfIngredients = new ArrayList<>();
	
	// link between a recipe and a meal
		@OneToMany(mappedBy = "recipe")
		private List<Meal> listOfMeals = new ArrayList<>();
		
		@Override
		public String toString() {
			return "PTDR";
		}
		
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
