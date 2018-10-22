package com.depromeet.clippingserver.category.domain;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.depromeet.clippingserver.exception.CategoryNotFoundException;
import com.depromeet.clippingserver.user.domain.User;

@Service @Transactional
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	public List<CategoryDto> findValidAndOrderedCategory(Long userId) {
		return categoryRepository.findByUserIdAndDeletedFalseOrderByOrderNoAsc(userId).stream()
				.map(CategoryDto::fromEntity).collect(Collectors.toList());
	}

	public CategoryDto saveNewCategory(CategoryDto dto, Long userId) {
		Integer maxOrderNo = categoryRepository.findMaxOrderNoByUserId(userId).orElse(0);
		Category category = Category.builder()
									.name(dto.getName())
									.orderNo(maxOrderNo + 1)
									.user(User.builder().id(userId).build())
									.build();
		category = categoryRepository.save(category);
		return CategoryDto.fromEntity(category);
	}

	public List<CategoryDto> updateOrderNo(Long userId, List<CategoryDto> category) {
		category.forEach( dto ->
				 categoryRepository.updateOrderNoById(dto.getOrderNo(), dto.getId()) 
		);
		return this.findValidAndOrderedCategory(userId);
	}

	public void updateDeletedTrue(Long categoryId) {
		categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
		categoryRepository.updateDeletedTrue(categoryId);
	}
}
