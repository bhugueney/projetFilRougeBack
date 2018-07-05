package com.myIGCoach.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
// this is the type of link between DB to reflect the heritage, here the choice
// is a join
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "ingredients")
public class Ingredient implements Serializable {
	// used because this class is serialisable to do the foreignkey and primary key
	// for QuantityRecipe class
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
	@ManyToOne(fetch = FetchType.EAGER)
	//@JsonBackReference(value = "categoryIngredient")
	@JsonManagedReference(value = "categoryIngredient")
	@JoinColumn(name = "fk_category", foreignKey = @ForeignKey(name = "fk_category"), nullable = false)
	private Category category;

	// data of ingredient
	@Column(name = "energy")
	private Double energy;
	@Column(name = "water")
	private Double water;
	@Column(name = "protein")
	private Double protein;
	@Column(name = "glucid")
	private Double glucid;
	@Column(name = "lipid")
	private Double lipid;
	@Column(name = "sugar")
	private Double sugar;
	@Column(name = "amidon")
	private Double amidon;
	@Column(name = "fiber")
	private Double fiber;
	@Column(name = "unsaturedFattyAcides")
	private Double unsaturedFattyAcides;
	@Column(name = "monoUnsaturedFattyAcides")
	private Double monoUnsaturedFattyAcides;
	@Column(name = "polyUnsaturedFattyAcides")
	private Double polyUnsaturedFattyAcides;
	@Column(name = "salt")
	private Double salt;
	@Column(name = "glycemicIndex")
	private Double glycemicIndex;
	@Column(name = "glycemicLoad")
	private Double glycemicLoad;

	// comment of owner of ingredient
	@Column(name = "comment", nullable = true)
	private String comment;

	// owner of ingredient since object user
	@ManyToOne(fetch = FetchType.EAGER)
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
	@JsonIgnore
	private List<QuantityRecipe> listOfRecipes = new ArrayList<>();

	/*************************
	 * Constructors
	 *************************/
	public Ingredient() {
		super();
	}

	/**
	 * 
	 * @param name
	 * @param category
	 * @param energy
	 * @param water
	 * @param protein
	 * @param glucid
	 * @param lipid
	 * @param sugar
	 * @param amidon
	 * @param fiber
	 * @param unsaturedFattyAcides
	 * @param monoUnsaturedFattyAcides
	 * @param polyUnsaturedFattyAcides
	 * @param salt
	 * @param glycemicIndex
	 * @param glycemicLoad
	 * @param owner
	 * @param active
	 */
	public Ingredient(String name, Category category, Double energy, Double water, Double protein, Double glucid,
			Double lipid, Double sugar, Double amidon, Double fiber, Double unsaturedFattyAcides,
			Double monoUnsaturedFattyAcides, Double polyUnsaturedFattyAcides, Double salt, Double glycemicIndex,
			Double glycemicLoad, User owner, boolean active) {
		super();
		this.name = name;
		this.category = category;
		this.energy = energy;
		this.water = water;
		this.protein = protein;
		this.glucid = glucid;
		this.lipid = lipid;
		this.sugar = sugar;
		this.amidon = amidon;
		this.fiber = fiber;
		this.unsaturedFattyAcides = unsaturedFattyAcides;
		this.monoUnsaturedFattyAcides = monoUnsaturedFattyAcides;
		this.polyUnsaturedFattyAcides = polyUnsaturedFattyAcides;
		this.salt = salt;
		this.glycemicIndex = glycemicIndex;
		this.glycemicLoad = glycemicLoad;
		this.owner = owner;
		this.active = active;
	}

	/**
	 * 
	 * @param updatingData
	 */
	public void updateWithIngredientAttributes(Ingredient updatingData) {
		this.name = updatingData.name;
		this.category = updatingData.category;
		this.energy = updatingData.energy;
		this.water = updatingData.water;
		this.protein = updatingData.protein;
		this.glucid = updatingData.glucid;
		this.lipid = updatingData.lipid;
		this.sugar = updatingData.sugar;
		this.amidon = updatingData.amidon;
		this.fiber = updatingData.fiber;
		this.unsaturedFattyAcides = updatingData.unsaturedFattyAcides;
		this.monoUnsaturedFattyAcides = updatingData.monoUnsaturedFattyAcides;
		this.polyUnsaturedFattyAcides = updatingData.polyUnsaturedFattyAcides;
		this.salt = updatingData.salt;
		this.glycemicIndex = updatingData.glycemicIndex;
		this.glycemicLoad = updatingData.glycemicLoad;
		this.active = updatingData.active;
	}

	public boolean equals(Ingredient input) {
		boolean result = false;

		if (input == null) {
			return result;
		}

		result = ( Objects.equals(this.name, input.name));
		result =  result && (
				(this.category == null && input.category == null ) ||
				(Objects.equals(this.category.getId(), input.category.getId()))
				);
		
		result = result && (Objects.equals(this.energy, input.energy));
		result = result && (Objects.equals(this.water, input.water));
		result = result && (Objects.equals(this.protein, input.protein));
		result = result && (Objects.equals(this.glucid, input.glucid));
		result = result && (Objects.equals(this.glucid, input.glucid));
		result = result && (Objects.equals(this.lipid, input.lipid));
		result = result && (Objects.equals(this.sugar, input.sugar));
		result = result && (Objects.equals(this.amidon, input.amidon));
		result = result && (Objects.equals(this.fiber, input.fiber));
		result = result && (Objects.equals(this.unsaturedFattyAcides, input.unsaturedFattyAcides));
		result = result && (Objects.equals(this.monoUnsaturedFattyAcides, input.monoUnsaturedFattyAcides));
		result = result && (Objects.equals(this.polyUnsaturedFattyAcides, input.polyUnsaturedFattyAcides));
		result = result && (Objects.equals(this.salt, input.salt));
		result = result && (Objects.equals(this.glycemicIndex, input.glycemicIndex));
		result = result && (Objects.equals(this.glycemicLoad, input.glycemicLoad));
		result = result && (Objects.equals(this.active, input.active));
		
		return result;
		
	}
	
	

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
	public Double getEnergy() {
		return energy;
	}

	/**
	 * @param energy
	 *            the energy to set
	 */
	public void setEnergy(Double energy) {
		this.energy = energy;
	}

	/**
	 * @return the water
	 */
	public Double getWater() {
		return water;
	}

	/**
	 * @param water
	 *            the water to set
	 */
	public void setWater(Double water) {
		this.water = water;
	}

	/**
	 * @return the protein
	 */
	public Double getProtein() {
		return protein;
	}

	/**
	 * @param protein
	 *            the protein to set
	 */
	public void setProtein(Double protein) {
		this.protein = protein;
	}

	/**
	 * @return the glucid
	 */
	public Double getGlucid() {
		return glucid;
	}

	/**
	 * @param glucid
	 *            the glucid to set
	 */
	public void setGlucid(Double glucid) {
		this.glucid = glucid;
	}

	/**
	 * @return the lipid
	 */
	public Double getLipid() {
		return lipid;
	}

	/**
	 * @param lipid
	 *            the lipid to set
	 */
	public void setLipid(Double lipid) {
		this.lipid = lipid;
	}

	/**
	 * @return the sugar
	 */
	public Double getSugar() {
		return sugar;
	}

	/**
	 * @param sugar
	 *            the sugar to set
	 */
	public void setSugar(Double sugar) {
		this.sugar = sugar;
	}

	/**
	 * @return the amidon
	 */
	public Double getAmidon() {
		return amidon;
	}

	/**
	 * @param amidon
	 *            the amidon to set
	 */
	public void setAmidon(Double amidon) {
		this.amidon = amidon;
	}

	/**
	 * @return the fiber
	 */
	public Double getFiber() {
		return fiber;
	}

	/**
	 * @param fiber
	 *            the fiber to set
	 */
	public void setFiber(Double fiber) {
		this.fiber = fiber;
	}

	/**
	 * @return the unsaturedFattyAcides
	 */
	public Double getUnsaturedFattyAcides() {
		return unsaturedFattyAcides;
	}

	/**
	 * @param unsaturedFattyAcides
	 *            the unsaturedFattyAcides to set
	 */
	public void setUnsaturedFattyAcides(Double unsaturedFattyAcides) {
		this.unsaturedFattyAcides = unsaturedFattyAcides;
	}

	/**
	 * @return the monoUnsaturedFattyAcides
	 */
	public Double getMonoUnsaturedFattyAcides() {
		return monoUnsaturedFattyAcides;
	}

	/**
	 * @param monoUnsaturedFattyAcides
	 *            the monoUnsaturedFattyAcides to set
	 */
	public void setMonoUnsaturedFattyAcides(Double monoUnsaturedFattyAcides) {
		this.monoUnsaturedFattyAcides = monoUnsaturedFattyAcides;
	}

	/**
	 * @return the polyUnsaturedFattyAcides
	 */
	public Double getPolyUnsaturedFattyAcides() {
		return polyUnsaturedFattyAcides;
	}

	/**
	 * @param polyUnsaturedFattyAcides
	 *            the polyUnsaturedFattyAcides to set
	 */
	public void setPolyUnsaturedFattyAcides(Double polyUnsaturedFattyAcides) {
		this.polyUnsaturedFattyAcides = polyUnsaturedFattyAcides;
	}

	/**
	 * @return the salt
	 */
	public Double getSalt() {
		return salt;
	}

	/**
	 * @param salt
	 *            the salt to set
	 */
	public void setSalt(Double salt) {
		this.salt = salt;
	}

	/**
	 * @return the glycemicIndex
	 */
	public Double getGlycemicIndex() {
		return glycemicIndex;
	}

	/**
	 * @param glycemicIndex
	 *            the glycemicIndex to set
	 */
	public void setGlycemicIndex(Double glycemicIndex) {
		this.glycemicIndex = glycemicIndex;
	}

	/**
	 * @return the glycemicLoad
	 */
	public Double getGlycemicLoad() {
		return glycemicLoad;
	}

	/**
	 * @param glycemicLoad
	 *            the glycemicLoad to set
	 */
	public void setGlycemicLoad(Double glycemicLoad) {
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
