package com.depromeet.clippingserver.category.domain;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.depromeet.clippingserver.post.domain.Post;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	public ArrayList<Category> findByUserIdOrderByOrderNoAsc(Long id);

	public ArrayList<Category> findByUserId(Long id);

	public ArrayList<Category> findByUserIdAndDeletedFalseOrderByOrderNoAsc(Long id);

	@Query("select max(c.orderNo) from Category c where c.user.id = :userId")
	public Optional<Integer> findMaxOrderNoByUserId(@Param("userId") Long userId);

	@Query("select p from Category c inner join c.posts p where c.id = :id and c.user.id = :userId and p.deleted = false order by p.updatedDate DESC")
	public Page<Post> findPostsByIdAndUserId(@Param("id") long id, @Param("userId") Long userId,Pageable pageable);

	@Modifying
	@Transactional
	@Query("update Category c set c.orderNo = :orderNo where c.id = :id")
	public void updateOrderNoById(@Param("orderNo") int orderNo, @Param("id") Long id);

	@Modifying
	@Transactional
	@Query("update Category c set c.deleted = true where c.id = :id")
	public void updateDeletedTrue(@Param("id") Long id);

}
