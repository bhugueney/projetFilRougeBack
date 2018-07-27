package com.myIGCoach.service;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.myIGCoach.models.Category;
import com.myIGCoach.repository.CategoryRepository;
import com.myIGCoach.tools.CheckList;

@Service
@Named
public class CategoryServiceImp implements CategoryService {
	@Inject
	private CategoryRepository categoryRepository;
	@Inject
	private CheckList checkList;

	/**
	 * method to create a category only if user have ROLE_ADMIN
	 * 
	 * @param c:
	 *            a category
	 * @param userId:
	 *            user id do the request return category created or null or internet
	 *            server error
	 */
	@Override
	public Category create(Category c, Long userId) {
		if (checkList.checkUserAdmin(userId)) {
			boolean check = checkList.checkNewCategory(c);
			if (check) {
				return categoryRepository.save(c);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * method to list all categories return all categories or an empty list or
	 * internet server error
	 */
	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	/**
	 * method to read the informations about a category
	 * 
	 * @param id:
	 *            category id return the informations about it
	 */
	@Override
	public ResponseEntity<Category> read(Long id) {
		Optional<Category> c = categoryRepository.findById(id);
		return c.isPresent() ? ResponseEntity.ok().body(c.get()) : ResponseEntity.notFound().build();
	}

	/**
	 * method to list children of category
	 * 
	 * @param id:
	 *            parent category id
	 */
	@Override
	public ResponseEntity<List<Category>> readChildren(Long parentId) {
		Optional<List<Category>> list = categoryRepository.findByParentId(parentId);
		return list.isPresent() ? ResponseEntity.ok().body(list.get()) : ResponseEntity.notFound().build();
	}

	/**
	 * method to update a category only if user have a ROLE_ADMIN
	 * 
	 * @param c:
	 *            category with new informations
	 * @param id:
	 *            category id
	 * @param userId:
	 *            user id do the request return string about the result
	 */
	@Override
	public String update(Category c, Long id, Long userId) {
		if (checkList.checkUserAdmin(userId)) {
			Optional<Category> cat = categoryRepository.findById(id);
			if (cat.isPresent() && cat.get().getId() == c.getId()) {
				if (checkList.checkCategoryInformations(c)) {
					categoryRepository.save(c);
					return "Update is ok.";
				} else {
					return "Sorry, this update is not possible.";
				}
			} else {
				return "Update impossible because this category does not exist.";
			}
		} else {
			return "Sorry, you are not authorised to update this element.";
		}
	}

	/**
	 * method to delete a category only if user have a ROLE_ADMIN
	 * 
	 * @param id:
	 *            category id
	 * @param userId:
	 *            user id do the request return a string about the result
	 */
	@Override
	public String delete(Long id, Long userId) {
		if (checkList.checkUserAdmin(userId)) {
			Optional<Category> c = categoryRepository.findById(id);
			if (c.isPresent() && c.get().getListOfChildren().isEmpty() && c.get().getListOfIngredient().isEmpty()) {
				categoryRepository.deleteById(id);
				return "Your category has been deleted.";
			} else {
				return "Sorry, this element can not be remove. Some elements are joined it.";
			}
		} else {
			return "Sorry, you are not authorised to remove this element.";
		}
	}

	/**
	 * method to get category without child.
	 * @return List of categories without child
	 */
	@Override
	public List<Category> readLeafCategories(){
		return categoryRepository.findAllByListOfChildrenIsNullOrderByName();
	}

}
