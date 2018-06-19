package com.myIGCoach.service;

import java.util.List;

import javax.inject.Named;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.myIGCoach.models.Category;

@Service
@Named
public interface CategoryService {
	public Category create(Category c);
	public List<Category> findAll();
	public ResponseEntity<Category> read(Long id);
	public String update(Category c, Long id);
	public String delete(Long id);

}
