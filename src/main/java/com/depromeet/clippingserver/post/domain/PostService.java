package com.depromeet.clippingserver.post.domain;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.depromeet.clippingserver.category.domain.Category;
import com.depromeet.clippingserver.category.domain.CategoryRepository;
import com.depromeet.clippingserver.exception.CategoryNotFoundException;
import com.depromeet.clippingserver.exception.PostNotFoundException;
import com.depromeet.clippingserver.exception.WrongURLException;

@Service
@Transactional
public class PostService {
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;

	public PostDto saveNewPost(PostDto postDto, Long userId) {
		String url = getValidUrl(postDto.getUrl());
		Connection conn = null;
		try {
			conn = Jsoup.connect(url);
			postDto.addThumnailAndTitleAndSourceOf(url, conn);
		} catch (Exception e) {
			System.out.println(url);
			System.out.println(e.getMessage());
			throw new WrongURLException();
		}
		Post post = Post.builder().comment(postDto.getComment()).url(url).sourceOf(postDto.getSourceOf())
				.category(Category.builder().id(postDto.getCategory().getId()).build())
				.thumbnailImgLink(postDto.getThumnailLink()).title(postDto.getTitle()).userId(userId).build();
		Post re = postRepository.save(post);
		re.addCategory(categoryRepository.findById(re.getCategory().getId()).get());
		return PostDto.fromEntity(re);
	}

	private String getValidUrl(String url) {
		if(url == null) {throw new WrongURLException();}
		Pattern r = Pattern.compile("^((http)|(https)):[\\/]{2}");
		Matcher m = r.matcher(url);
		if (!m.find()) {
			if (url.contains(".")) {
				url = "http://" + url;
			}
		}
		return url;
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
			post = postRepository.findByUserIdAndTitleContainingIgnoreCaseAndCommentContainingIgnoreCaseAndDeletedFalse(userId, keyword,
					keyword, pageable);
			if (post.getTotalElements() == 0) {
				post = postRepository.findByUserIdAndTitleContainingIgnoreCaseOrCommentContainingIgnoreCaseAndDeletedFalse(userId, keyword,
						keyword, pageable);
			}
		}
		GetAllPostsResponse re = GetAllPostsResponse.fromEntity(post.getContent());
		re.addPageInfo(post);
		return re;
	}

	public GetAllPostsResponse findAllBookmarkPost(Long userId, Pageable pageable) {
		Page<Post> post = postRepository.findByUserIdAndDeletedFalseAndIsBookmarkTrueOrderByUpdatedDateDesc(userId,
				pageable);
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
