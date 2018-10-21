package com.depromeet.clippingserver.category.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.depromeet.clippingserver.category.domain.CategoryRepository;

@RestController
public class CategoryController {
	@Autowired
	private CategoryRepository categoryRepository;
	
	@GetMapping(path="/categories")
	public ResponseEntity<String> getCategories(){
		return null;
	}
	
	@PostMapping(path="/categories")
	public ResponseEntity<String> createCategory(){
		return null;
	}
	
	@PutMapping(path="/categories")
	public ResponseEntity<String> updateCategory(){
		return null;
	}
	
	@DeleteMapping(path="/categories")
	public ResponseEntity<String> deleteCategory(){
		return null;
	}
}
