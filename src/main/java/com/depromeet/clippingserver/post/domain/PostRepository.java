package com.depromeet.clippingserver.post.domain;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PostRepository extends JpaRepository<Post, Long> {
	Page<Post> findByUserIdAndDeletedFalseOrderByUpdatedDateDesc(Long userId, Pageable pageable);
	
	Page<Post> findByUserIdAndTitleContainingIgnoreCaseAndCommentContainingIgnoreCaseAndDeletedFalse(Long userId, String title, String comment, Pageable pageable);

	Page<Post> findByUserIdAndTitleContainingIgnoreCaseOrCommentContainingIgnoreCaseAndDeletedFalse(Long userId, String title, String comment, Pageable pageable);
	
	Page<Post> findByUserIdAndDeletedFalseAndIsBookmarkTrueOrderByUpdatedDateDesc(Long userId, Pageable pageable);

	List<Post> findByUserIdAndCommentContainingAndDeletedFalse(Long userId, String comment);

	@Query("update Post p set p.category.id = :categoryId where p.id = :postId")
	@Modifying
	@Transactional
	void updateCategoryId(@Param("postId") Long postId, @Param("categoryId") Long categoryId);
	
	@Query("update Post p set p.deleted = true where p.id = :postId")
	@Modifying
	@Transactional
	void updateDeletedTrue(@Param("postId") Long postId);
	
	@Query("update Post p set p.isBookmark = case p.isBookmark when true then false else true end where p.id = :postId")
	@Modifying
	@Transactional
	void updateBookmark(@Param("postId") Long postId);

}
