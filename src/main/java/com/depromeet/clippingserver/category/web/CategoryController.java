package com.depromeet.clippingserver.category.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.depromeet.clippingserver.category.domain.CategoryDto;
import com.depromeet.clippingserver.category.domain.CategoryService;

@RestController
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@GetMapping(path = "/categories")
	public ResponseEntity<List<CategoryDto>> getCategories(@RequestHeader(value = "UserId") Long userId) {
		List<CategoryDto> re = categoryService.findValidAndOrderedCategory(userId);
		return ResponseEntity.ok().body(re);
	}

	@PostMapping(path = "/categories")
	public ResponseEntity<CategoryDto> createCategory(@RequestHeader(value = "UserId") Long userId, @RequestBody CategoryDto category) {
		CategoryDto re = categoryService.saveNewCategory(category, userId);
		return ResponseEntity.status(HttpStatus.CREATED).body(re);
	}

	@PutMapping(path = "/categories")
	public ResponseEntity<List<CategoryDto>> updateCategory(@RequestHeader(value = "UserId") Long userId,@RequestBody List<CategoryDto> category) {
		category = categoryService.updateOrderNo(userId, category);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(category);
	}

	@DeleteMapping(path = "/categories/{categoryId}")
	public ResponseEntity<String> deleteCategory(@RequestHeader(value = "UserId") Long userId,@PathVariable(name="categoryId") Long categoryId) {
		categoryService.updateDeletedTrue(categoryId);
		return ResponseEntity.status(HttpStatus.OK).body("delete success!");
	}
}
