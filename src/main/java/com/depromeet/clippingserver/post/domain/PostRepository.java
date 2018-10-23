package com.depromeet.clippingserver.post.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findByUserIdAndDeletedFalseOrderByUpdatedDateDesc(Long userId);
	
	@Query("update Post p set p.category.id = :categoryId where p.id = :postId")
	@Modifying
	@Transactional
	void updateCategoryId(@Param("postId") Long postId, @Param("categoryId") Long categoryId);
	
	@Query("update Post p set p.deleted = true where p.id = :postId")
	@Modifying
	@Transactional
	void updateDeletedTrue(@Param("postId") Long postId);
}
