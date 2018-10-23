package com.depromeet.clippingserver.post.domain;

import java.util.Optional;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.depromeet.clippingserver.category.domain.Category;
import com.depromeet.clippingserver.exception.CategoryNotFoundException;
import com.depromeet.clippingserver.exception.PostNotFoundException;

@Service @Transactional
public class PostService {
	@Autowired
	private PostRepository postRepository;

	public PostDto saveNewPost(PostDto postDto, Long userId) {
		String url = postDto.getUrl();
		Connection conn = Jsoup.connect(url);
		postDto.addThumnailAndTitleAndSourceOf(url, conn);
		Post post = Post.builder().comment(postDto.getComment()).url(url).sourceOf(postDto.getSourceOf())
				.category(Category.builder().id(postDto.getCategory().getId()).build())
				.thumbnailImgLink(postDto.getThumnailLink()).title(postDto.getTitle()).userId(userId).build();
		Post re = postRepository.save(post);
		return PostDto.fromEntity(re);
	}

	public GetAllPostsResponse findAllPostsOrdered(Long userId) {
		return GetAllPostsResponse.fromEntity(postRepository.findByUserIdAndDeletedFalseOrderByUpdatedDateDesc(userId));
	}

	public PostDto modifyPostCategoryId(Long postId, Long categoryId) {
		try {
			postRepository.updateCategoryId(postId, categoryId);
		}catch (Exception e) {
			throw new CategoryNotFoundException();
		}
		Optional<Post> post = postRepository.findById(postId);
		return PostDto.fromEntity(post.orElseThrow(PostNotFoundException::new));
	}

	public void deletePostOne(Long postId) {
		postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
		postRepository.updateDeletedTrue(postId);
	}

}
