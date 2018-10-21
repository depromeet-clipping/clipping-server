package com.depromeet.clippingserver.post.domain;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	@Query("select max(c.orderNo) from Category c where c.user.id = :userId")
	public Optional<Integer> findMaxOrderNoByUserId(@Param("userId") Long userId);
	
	@Modifying
	@Transactional
	@Query("update Category c set c.orderNo = :orderNo where c.id = :id")
	public void updateOrderNoById( @Param("orderNo") int orderNo, @Param("id") Long id);

	public ArrayList<Category> findByUserIdOrderByOrderNoAsc(Long id);

	public ArrayList<Category> findByUserId(Long id);
}
