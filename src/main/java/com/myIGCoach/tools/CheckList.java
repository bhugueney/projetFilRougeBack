package com.myIGCoach.tools;

import com.myIGCoach.models.Ingredient;

/**
 * this class serve to make controls on data
 * @author Frederick
 *
 */
public abstract class CheckList {
	/**
	 * this method check the minimal elements to save an ingredient
	 * 
	 * @param i:
	 *            an ingredient
	 * @return true if it's OK or false
	 */
	public static boolean checkIngredient(Ingredient i) {
		boolean result = false;
		if (i.getName() != null && i.getName() != "") {
			// TODO REGEX CONTROLLER ON STRING NAME
			if (i.getCategory() != null) {
				// TODO SECURITY CONTROL ON CATEGORY
				if (i.getOwner() != null) {
					// TODO SECURITY CONTROL ON OWNER
					// TODO SECURITY CONTROL ON COMMENT
					// TODO SECURITY CONTROL ON IMAGE
					// TODO SECURITY CONTROL ON DATAS (not necessary with the type double)
					result = true;
				}
			}
		}
		return result;
	}

}
