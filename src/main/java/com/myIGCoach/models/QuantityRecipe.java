package com.myIGCoach.models;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;


/**
 * this entity save in DB the relation between a recipe (or meal) and his
 * ingredients with their quantity
 */
@Entity
@Table(name = "quantityIngredients")
public class QuantityRecipe implements Serializable {
	// used because this class have primary key with foreignkey about Recipe and
	// Ingredient classes
	private static final long serialVersionUID = 1L;

	// Primay key
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quantityIngredient")
	@SequenceGenerator(name = "quantityIngredient", sequenceName = "quantity_ingredient_seq", allocationSize = 1)
	private Long id;

	// link to the id of recipe since object Recipe
	//@EmbeddedId
	@ManyToOne(fetch = FetchType.EAGER)  // type of relation with recipe entity
	@JoinColumn(name = "fk_recipe",  foreignKey = @ForeignKey(name = "fk_recipe"), nullable = false)
	@JsonBackReference(value = "recipe")  // to do the link with the recipe
	private Recipe recipe;

	// link to the ingredients since object Ingredient
	//@EmbeddedId
	@ManyToOne(fetch = FetchType.EAGER) // type of relation with ingredient entity to have information about ingredient
	@JoinColumn(name = "fk_ingredient", foreignKey = @ForeignKey(name = "fk_ingredient"), nullable = false)
	private Ingredient ingredient;

	// quantity of ingredient
	@Column(name = "quantity", nullable = false)
	private double quantity;

	/************************
	 * GETTERS AND SETTERS
	 ***********************/

	/**
	 * @return the recipe
	 */
	public Recipe getRecipe() {
		return recipe;
	}

	/**
	 * @param recipe
	 *            the recipe to set
	 */
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	/**
	 * @return the ingredient
	 */
	public Ingredient getIngredient() {
		return ingredient;
	}

	/**
	 * @param ingredient
	 *            the ingredient to set
	 */
	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	/**
	 * @return the quantity
	 */
	public double getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	// Needed for logs
	public String toString() {
		String result = "";
		result += "QuantityRecipe[";
		
		if ( this.getRecipe() != null ) {
			result += "recipe='" + this.getRecipe().getId() + "', ";
		} else {
			result += "recipe=NULL, ";
		}
		
		if ( this.getIngredient() != null) {
			result += "ingredient='" + this.getIngredient().getId() + "',  ";
		} else {
			result += "ingredient=NULL,  ";
		}
		
		result += "quantity=" + this.getQuantity();
		result += "]";
		return result;
	}
}
