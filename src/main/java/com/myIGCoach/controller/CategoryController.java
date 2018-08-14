package com.myIGCoach.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.myIGCoach.models.Category;
import com.myIGCoach.service.CategoryService;

/**********************************************************************
 **********************************************************************
 * TODO SPRING SECURITY ON PATH AND ON METHODS
 **********************************************************************
 **********************************************************************/

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Inject
    private CategoryService categoryService;

    // TODO SECURITY CONTROL TO RESTRICT THIS AT ROLE_ADMIN

    /**
     * method to save a new category, only authorised at ROLE_ADMIN
     *
     * @param c:      new category
     * @param userId: user id do the request
     * @return this category if it's OK or null it's not, or an Internet server
     * error
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Category create(@RequestBody Category c, @RequestParam("userId") Long userId) {
        return categoryService.create(c, userId);
    }

    /**
     * method to list all categories
     * @param categoryType: Optional indicating if all categories have to be sent or just categories without children
     *
     * @return all categories
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Category> findAll(@RequestParam(name = "type", defaultValue = "") String categoryType) {
        if (categoryType.equals("LEAF")) {
            return categoryService.readLeafCategories();
        } else {
            return categoryService.findAll();
        }
    }

    /**
     * method to see details about a category
     *
     * @param id: category id
     * @return informations about this category
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    @ResponseBody
    public ResponseEntity<Category> read(@PathVariable("id") Long id) {
        return categoryService.read(id);
    }

    /**
     * method to list children of one category
     *
     * @param parentId: parent category id
     * @return informations about this children
     */
    @RequestMapping(method = RequestMethod.GET, value = "children/{parentId}")
    @ResponseBody
    public ResponseEntity<List<Category>> readChildren(@PathVariable("parentId") Long parentId) {
        return categoryService.readChildren(parentId);
    }

    /**
     * method to update a category only if user have a ROLE_ADMIN
     *
     * @param id:     category id
     * @param c:      category with new informations
     * @param userId: user id do the request
     * @return string about the result
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    @ResponseBody
    public String update(@PathVariable("id") Long id, @RequestBody Category c, @RequestParam("userId") Long userId) {
        return categoryService.update(c, id, userId);
    }

    /**
     * method to delete a category only if user have a ROLE_ADMIN
     *
     * @param id:     category id
     * @param userId: user id do the request
     * @return string about the result
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @ResponseBody
    public String delete(@PathVariable("id") Long id, @RequestParam("userId") Long userId) {
        return categoryService.delete(id, userId);
    }

}
