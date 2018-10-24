package com.depromeet.clippingserver.post.domain;

import java.net.MalformedURLException;
import java.util.Optional;

import org.hibernate.validator.internal.constraintvalidators.hv.URLValidator;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.depromeet.clippingserver.category.domain.Category;
import com.depromeet.clippingserver.exception.CategoryNotFoundException;
import com.depromeet.clippingserver.exception.PostNotFoundException;
import com.depromeet.clippingserver.exception.WrongURLException;

@Service
@Transactional
public class PostService {
	@Autowired
	private PostRepository postRepository;

	public PostDto saveNewPost(PostDto postDto, Long userId) throws MalformedURLException {
		String url = postDto.getUrl();
		URLValidator urlValidator = new URLValidator();
		if(!urlValidator.isValid(url, null)) {
			throw new WrongURLException();
		}
		Connection conn = Jsoup.connect(url);
		postDto.addThumnailAndTitleAndSourceOf(url, conn);
		Post post = Post.builder().comment(postDto.getComment()).url(url).sourceOf(postDto.getSourceOf())
				.category(Category.builder().id(postDto.getCategory().getId()).build())
				.thumbnailImgLink(postDto.getThumnailLink()).title(postDto.getTitle()).userId(userId).build();
		Post re = postRepository.save(post);
		return PostDto.fromEntity(re);
	}

	public GetAllPostsResponse findAllPostsOrdered(Long userId, Pageable pageable) {
		Page<Post> post = postRepository.findByUserIdAndDeletedFalseOrderByUpdatedDateDesc(userId, pageable);
		GetAllPostsResponse re = GetAllPostsResponse.fromEntity(post.getContent());
		re.addPageInfo(post);
		return re;
	}

	public PostDto modifyPostCategoryId(Long postId, Long categoryId) {
		try {
			postRepository.updateCategoryId(postId, categoryId);
		} catch (Exception e) {
			throw new CategoryNotFoundException();
		}
		Optional<Post> post = postRepository.findById(postId);
		return PostDto.fromEntity(post.orElseThrow(PostNotFoundException::new));
	}

	public void deletePostOne(Long postId) {
		postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
		postRepository.updateDeletedTrue(postId);
	}
	
	public Post findOne(Long postId) {
		return postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
	}

	public GetAllPostsResponse searchPost(Long userId, String keyword, Pageable pageable) {
		Page<Post> post;
		if (keyword == null || keyword.equals("")) {
			post = postRepository.findByUserIdAndDeletedFalseOrderByUpdatedDateDesc(userId, pageable);
		} else {
			post = postRepository.findByUserIdAndTitleContainingAndCommentContainingAndDeletedFalse(userId, keyword,
					keyword, pageable);
			if (post.getTotalElements() == 0) {
				post = postRepository.findByUserIdAndTitleContainingOrCommentContainingAndDeletedFalse(userId, keyword,
						keyword, pageable);
			}
		}
		GetAllPostsResponse re = GetAllPostsResponse.fromEntity(post.getContent());
		re.addPageInfo(post);
		return re;
	}

	public GetAllPostsResponse findAllBookmarkPost(Long userId, Pageable pageable) {
		Page<Post> post = postRepository.findByUserIdAndDeletedFalseAndIsBookmarkTrueOrderByUpdatedDateDesc(userId, pageable);
		GetAllPostsResponse re = GetAllPostsResponse.fromEntity(post.getContent());
		re.addPageInfo(post);
		return re;
	}

	public PostDto updateBookmark(Long postId) {
		postRepository.updateBookmark(postId);
		Optional<Post> post = postRepository.findById(postId);
		return post.map(PostDto::fromEntity).orElseThrow(PostNotFoundException::new);
	}

}
