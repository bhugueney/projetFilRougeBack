package com.myIGCoach.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // this is the type of link between DB to reflect the heritage, here the
												// choice is a join
@Table(name = "ingredients")
public class Ingredient implements Serializable {
	private static final long serialVersionUID = 1L;

	// id of ingredient
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingredient")
	@SequenceGenerator(name = "ingredient", sequenceName = "ingredient_seq", allocationSize = 1)
	private Long id;
	// name of ingredient
	@Column(name = "NAME", nullable = false)
	private String name;
	// url image of ingredient
	@Column(name = "IMAGE")
	private String urlImage;
	// category of ingredient
	// this is a relation with object category, in DB this link is saved in column
	// fk_category
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference(value = "categoryIngredient")
	@JoinColumn(name = "fk_category", foreignKey = @ForeignKey(name = "fk_category"), nullable = false)
	private Category category;

	// data of ingredient
	@Column(name = "energy")
	private double energy;
	@Column(name = "water")
	private double water;
	@Column(name = "protein")
	private double protein;
	@Column(name = "glucid")
	private double glucid;
	@Column(name = "lipid")
	private double lipid;
	@Column(name = "sugar")
	private double sugar;
	@Column(name = "amidon")
	private double amidon;
	@Column(name = "fiber")
	private double fiber;
	@Column(name = "unsaturedFattyAcides")
	private double unsaturedFattyAcides;
	@Column(name = "monoUnsaturedFattyAcides")
	private double monoUnsaturedFattyAcides;
	@Column(name = "polyUnsaturedFattyAcides")
	private double polyUnsaturedFattyAcides;
	@Column(name = "salt")
	private double salt;
	@Column(name = "glycemicIndex")
	private double glycemicIndex;
	@Column(name = "glycemicLoad")
	private double glycemicLoad;

	// comment of owner of ingredient
	@Column(name = "comment", nullable = true)
	private String comment;

	// owner of ingredient since object user
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_user", foreignKey = @ForeignKey(name = "fk_user"), nullable = false)
	@JsonBackReference(value = "ownerIngredient")
	private User owner;

	// this attribute is to define if the ingredient is active or not
	// default value true, but if it's false means that ingredient is not display
	// but it can be use in a past recipe
	@Column(name = "active", nullable = false)
	private boolean active = true;

	// this is the relation Object between Recipe who content some ingredients with
	// quantity, this link is done by object QuantityRecipe
	@OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL)
	private List<QuantityRecipe> listOfRecipes = new ArrayList<>();

	/************************
	 * GETTERS AND SETTERS
	 ***********************/

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active
	 *            the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * @return the listOfRecipes
	 */
	public List<QuantityRecipe> getListOfRecipes() {
		return listOfRecipes;
	}

	/**
	 * @param listOfRecipes
	 *            the listOfRecipes to set
	 */
	public void setListOfRecipes(List<QuantityRecipe> listOfRecipes) {
		this.listOfRecipes = listOfRecipes;
	}

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
	 * @return the urlImage
	 */
	public String getUrlImage() {
		return urlImage;
	}

	/**
	 * @param urlImage
	 *            the urlImage to set
	 */
	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	/**
	 * @return the energy
	 */
	public double getEnergy() {
		return energy;
	}

	/**
	 * @param energy
	 *            the energy to set
	 */
	public void setEnergy(double energy) {
		this.energy = energy;
	}

	/**
	 * @return the water
	 */
	public double getWater() {
		return water;
	}

	/**
	 * @param water
	 *            the water to set
	 */
	public void setWater(double water) {
		this.water = water;
	}

	/**
	 * @return the protein
	 */
	public double getProtein() {
		return protein;
	}

	/**
	 * @param protein
	 *            the protein to set
	 */
	public void setProtein(double protein) {
		this.protein = protein;
	}

	/**
	 * @return the glucid
	 */
	public double getGlucid() {
		return glucid;
	}

	/**
	 * @param glucid
	 *            the glucid to set
	 */
	public void setGlucid(double glucid) {
		this.glucid = glucid;
	}

	/**
	 * @return the lipid
	 */
	public double getLipid() {
		return lipid;
	}

	/**
	 * @param lipid
	 *            the lipid to set
	 */
	public void setLipid(double lipid) {
		this.lipid = lipid;
	}

	/**
	 * @return the sugar
	 */
	public double getSugar() {
		return sugar;
	}

	/**
	 * @param sugar
	 *            the sugar to set
	 */
	public void setSugar(double sugar) {
		this.sugar = sugar;
	}

	/**
	 * @return the amidon
	 */
	public double getAmidon() {
		return amidon;
	}

	/**
	 * @param amidon
	 *            the amidon to set
	 */
	public void setAmidon(double amidon) {
		this.amidon = amidon;
	}

	/**
	 * @return the fiber
	 */
	public double getFiber() {
		return fiber;
	}

	/**
	 * @param fiber
	 *            the fiber to set
	 */
	public void setFiber(double fiber) {
		this.fiber = fiber;
	}

	/**
	 * @return the unsaturedFattyAcides
	 */
	public double getUnsaturedFattyAcides() {
		return unsaturedFattyAcides;
	}

	/**
	 * @param unsaturedFattyAcides
	 *            the unsaturedFattyAcides to set
	 */
	public void setUnsaturedFattyAcides(double unsaturedFattyAcides) {
		this.unsaturedFattyAcides = unsaturedFattyAcides;
	}

	/**
	 * @return the monoUnsaturedFattyAcides
	 */
	public double getMonoUnsaturedFattyAcides() {
		return monoUnsaturedFattyAcides;
	}

	/**
	 * @param monoUnsaturedFattyAcides
	 *            the monoUnsaturedFattyAcides to set
	 */
	public void setMonoUnsaturedFattyAcides(double monoUnsaturedFattyAcides) {
		this.monoUnsaturedFattyAcides = monoUnsaturedFattyAcides;
	}

	/**
	 * @return the polyUnsaturedFattyAcides
	 */
	public double getPolyUnsaturedFattyAcides() {
		return polyUnsaturedFattyAcides;
	}

	/**
	 * @param polyUnsaturedFattyAcides
	 *            the polyUnsaturedFattyAcides to set
	 */
	public void setPolyUnsaturedFattyAcides(double polyUnsaturedFattyAcides) {
		this.polyUnsaturedFattyAcides = polyUnsaturedFattyAcides;
	}

	/**
	 * @return the salt
	 */
	public double getSalt() {
		return salt;
	}

	/**
	 * @param salt
	 *            the salt to set
	 */
	public void setSalt(double salt) {
		this.salt = salt;
	}

	/**
	 * @return the glycemicIndex
	 */
	public double getGlycemicIndex() {
		return glycemicIndex;
	}

	/**
	 * @param glycemicIndex
	 *            the glycemicIndex to set
	 */
	public void setGlycemicIndex(double glycemicIndex) {
		this.glycemicIndex = glycemicIndex;
	}

	/**
	 * @return the glycemicLoad
	 */
	public double getGlycemicLoad() {
		return glycemicLoad;
	}

	/**
	 * @param glycemicLoad
	 *            the glycemicLoad to set
	 */
	public void setGlycemicLoad(double glycemicLoad) {
		this.glycemicLoad = glycemicLoad;
	}

	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment
	 *            the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the owner
	 */
	public User getOwner() {
		return owner;
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public void setOwner(User owner) {
		this.owner = owner;
	}

}
