package com.myIGCoach.service;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.myIGCoach.models.Category;
import com.myIGCoach.repository.CategoryRepository;

@Service
@Named
public class CategoryServiceImp implements CategoryService {
	@Inject
	private CategoryRepository categoryRepository;

	@Override
	public Category create(Category c) {
		return categoryRepository.save(c);
	}

	@Override
	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	@Override
	public ResponseEntity<Category> read(Long id) {
		Optional<Category> c = categoryRepository.findById(id);
		return c.isPresent()? ResponseEntity.ok().body(c.get()) : ResponseEntity.notFound().build();
	}

	@Override
	public String update(Category c, Long id) {
		Optional<Category> cat = categoryRepository.findById(id);
		if(cat.get().getId()==id) {
			categoryRepository.save(c);
			return "Update is ok.";
		} else {
			return "Update impossible because this category does not exist.";
		}
	}

	@Override
	public String delete(Long id) {
		categoryRepository.deleteById(id);
		return "Your category has been deleted.";
	}

}
