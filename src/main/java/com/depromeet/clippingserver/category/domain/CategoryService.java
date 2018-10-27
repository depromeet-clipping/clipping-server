package com.depromeet.clippingserver.category.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.depromeet.clippingserver.exception.CategoryNotFoundException;
import com.depromeet.clippingserver.post.domain.GetAllPostsResponse;
import com.depromeet.clippingserver.post.domain.Post;
import com.depromeet.clippingserver.user.domain.User;

@Service
@Transactional
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	
	@PersistenceContext
	private EntityManager em;

	public List<CategoryDto> findValidAndOrderedCategory(Long userId) {
		return categoryRepository.findByUserIdAndDeletedFalseOrderByOrderNoAsc(userId).stream()
				.map(CategoryDto::fromEntity).collect(Collectors.toList());
	}

	public CategoryDto saveNewCategory(CategoryDto dto, Long userId) {
		Integer maxOrderNo = categoryRepository.findMaxOrderNoByUserId(userId).orElse(0);
		Category category = Category.builder().name(dto.getName()).orderNo(maxOrderNo + 1)
				.user(User.builder().id(userId).build()).build();
		category = categoryRepository.save(category);
		return CategoryDto.fromEntity(category);
	}

	public List<CategoryDto> updateOrderNo(Long userId, ArrayList<Long> categoryId) {
		if(categoryId.isEmpty()) {
			throw new CategoryNotFoundException();
		}
		
		categoryId.forEach(id -> {
			Optional<Category> obj = categoryRepository.findById(id);
			if(obj.get().isDeleted() == true) {
				throw new CategoryNotFoundException();
			}
			obj.orElseThrow(CategoryNotFoundException::new);
		});
		
		Stream<Category> allCategories = categoryRepository.findByUserIdAndDeletedFalseOrderByOrderNoAsc(userId).stream();
		List<Long> willDeleteIds = allCategories.map(obj -> obj.getId()).filter(id -> categoryId.stream().noneMatch(Predicate.isEqual(id))).collect(Collectors.toList());
		willDeleteIds.forEach(id -> categoryRepository.updateDeletedTrue(id));
		
		List<Category> category = new ArrayList<>();
		for(int i = 0; i < categoryId.size(); i++) {
			category.add(Category.builder().id(categoryId.get(i)).orderNo(i).build() );
		}
		category.forEach(dto -> categoryRepository.updateOrderNoById(dto.getOrderNo(), dto.getId()));
		em.clear();
		return this.findValidAndOrderedCategory(userId);
	}

	public void updateDeletedTrue(ArrayList<Long> categoryIds) {
		if(categoryIds.isEmpty()) {
			throw new CategoryNotFoundException();
		}
		categoryIds.forEach(id -> {
			categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
			categoryRepository.updateDeletedTrue(id);
		});
	}

	public GetAllPostsResponse findParticularPosts(long categoryId, Long userId, Pageable pageable) {
		categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
		Page<Post> posts = categoryRepository.findPostsByIdAndUserId(categoryId, userId, pageable);
		GetAllPostsResponse re = GetAllPostsResponse.fromEntity(posts.getContent());
		re.addPageInfo(posts);
		return re;
	}

	public CategoryDto updateName(String name, Long id) {
		categoryRepository.updateName(name, id);
		Optional<Category> category = categoryRepository.findById(id);
		return category.map(CategoryDto::fromEntity).orElseThrow(CategoryNotFoundException::new);
	}
}
