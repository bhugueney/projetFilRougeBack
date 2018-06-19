package com.myIGCoach.repository;

import javax.inject.Named;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myIGCoach.models.Category;

@Repository
@Named
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
