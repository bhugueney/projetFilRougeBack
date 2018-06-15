package com.myIGCoach.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "categories")
public class Category {
	// id of category
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category")
	@SequenceGenerator(name = "category", sequenceName = "category_seq", allocationSize = 1)
	private Long id;
	
	// name of category
	@Column(name = "NAME")
	private String name;
	
	/****************************************************************************************************
	// parent is an other object Category and it can have one parent BUT it can be parent of more child
	 ***************************************************************************************************/
	// this is the id of parent of this category and it's saved in DB with a column 'fk_parent'
	@ManyToOne(cascade = (CascadeType.ALL))
	@JoinColumn(name = "fk_parent")
	private Category parent;
	// this is the link of parent with child, this relation exist in Object but in DB it's the link with column 'fk_parent' 
	@OneToMany(mappedBy = "parent")
	private List<Category> listOfChilds = new ArrayList<>();
	
	// this is the relation Object between category and ingredient
	@OneToMany(mappedBy = "category")
	private List<Ingredient> listOfIngredient = new ArrayList<>();
	
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the parent
	 */
	public Category getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(Category parent) {
		this.parent = parent;
	}

	/**
	 * @return the listOfChilds
	 */
	public List<Category> getListOfChilds() {
		return listOfChilds;
	}

	/**
	 * @param listOfChilds the listOfChilds to set
	 */
	public void setListOfChilds(List<Category> listOfChilds) {
		this.listOfChilds = listOfChilds;
	}

	/**
	 * @return the listOfIngredient
	 */
	public List<Ingredient> getListOfIngredient() {
		return listOfIngredient;
	}

	/**
	 * @param listOfIngredient the listOfIngredient to set
	 */
	public void setListOfIngredient(List<Ingredient> listOfIngredient) {
		this.listOfIngredient = listOfIngredient;
	}
	
}
