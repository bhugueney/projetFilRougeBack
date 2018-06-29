package com.myIGCoach.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.CascadeType;
//import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity(name = "Recipe")
// this is the type of link between DB to reflect the heritage, here the choice
// is a join
@Inheritance(strategy = InheritanceType.JOINED)
public class Recipe extends Ingredient {
	// used because this class is serialisable to do the foreignkey and primary key
	// for QuantityRecipe class
	private static final long serialVersionUID = 1L;

	// a recipe contend a list of ingredients with their quantity
	@JsonManagedReference(value = "recipe")
	@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
	private List<QuantityRecipe> listOfIngredients = new ArrayList<>();

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
	 * @param listOfIngredients
	 *            the listOfIngredients to set
	 */
	public void setListOfIngredients(List<QuantityRecipe> listOfIngredients) {
		this.listOfIngredients = listOfIngredients;
	}

}
