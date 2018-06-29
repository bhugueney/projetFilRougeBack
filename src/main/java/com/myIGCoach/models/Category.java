package com.myIGCoach.models;

import java.util.ArrayList;
import java.util.List;

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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
	 * // parent is an other object Category and it can have one parent BUT it can
	 * be parent of more child
	 ***************************************************************************************************/
	// this is the id of parent of this category and it's saved in DB with a column
	// 'fk_parent'
	@ManyToOne
	@JoinColumn(name = "fk_parent")
	@JsonBackReference(value = "parentId")
	private Category parent;

	// this is the link of parent with child, this relation exist in Object but in
	// DB it's the link with column 'fk_parent'.
	// the result is the list of children of this category
	@OneToMany(mappedBy = "parent")
	@JsonManagedReference(value = "parentId")
	private List<Category> listOfChildren = new ArrayList<>();

	// this is the relation Object between category and ingredient
	// the result is the list of ingredients contained in this category
	@OneToMany(mappedBy = "category")
	@JsonManagedReference(value = "categoryIngredient")
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
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the listOfChildren
	 */
	public List<Category> getListOfChildren() {
		return listOfChildren;
	}

	/**
	 * @param listOfChildren
	 *            the listOfChildren to set
	 */
	public void setListOfChildren(List<Category> listOfChildren) {
		this.listOfChildren = listOfChildren;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
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
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(Category parent) {
		this.parent = parent;
	}

	/**
	 * @return the listOfIngredient
	 */
	public List<Ingredient> getListOfIngredient() {
		return listOfIngredient;
	}

	/**
	 * @param listOfIngredient
	 *            the listOfIngredient to set
	 */
	public void setListOfIngredient(List<Ingredient> listOfIngredient) {
		this.listOfIngredient = listOfIngredient;
	}

}
