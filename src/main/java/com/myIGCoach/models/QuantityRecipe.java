package com.myIGCoach.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.sym.Name;


/**
 * this entity save in DB the relation between a recipe (or meal)
 * and his ingredients with their quantity
 */
@Entity
//@Embeddable
@Table(name = "quantityIngredients")
public class QuantityRecipe implements Serializable{
	private static final long serialVersionUID = 1L;
	
	// id of an element 
	/*@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quantity")
	@SequenceGenerator(name = "quantity", sequenceName = "quantity_seq", allocationSize = 1)
	@Column(name = "ID")
	private Long id;*/
	// link to the id of recipe since object Recipe
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_recipe", nullable = false, foreignKey = @ForeignKey(name = "fk_recipe"))
	@JsonBackReference
	private Recipe recipe;
	// link to the ingredients since object Ingredient
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_ingredient", nullable = false, foreignKey = @ForeignKey(name = "fk_ingredient"))
	//@JsonBackReference(value = "ingredient")
	private Ingredient ingredient;
	// quantity of ingredient
	@Column(name = "quantity", nullable = false)
	private double quantity;
	
	
	
	/************************
	 * GETTERS AND SETTERS
	 ***********************/
	
	/**
	 * @return the id
	 *//*
	public Long getId() {
		return id;
	}
	*//**
	 * @param id the id to set
	 *//*
	public void setId(Long id) {
		this.id = id;
	}*/
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
	/**
	 * @return the ingredient
	 */
	public Ingredient getIngredient() {
		return ingredient;
	}
	/**
	 * @param ingredient the ingredient to set
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
	 * @param quantity the quantity to set
	 */
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

}
