package com.depromeet.clippingserver.post.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findByUserIdOrderByUpdatedDateDesc(Long userId);
}
