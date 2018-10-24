package com.depromeet.clippingserver.post.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.depromeet.clippingserver.category.domain.CategoryDto;
import com.depromeet.clippingserver.exception.UserNotFoundException;
import com.depromeet.clippingserver.post.domain.GetAllPostsResponse;
import com.depromeet.clippingserver.post.domain.PostDto;
import com.depromeet.clippingserver.post.domain.PostService;
import com.depromeet.clippingserver.user.domain.UserRepository;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import springfox.documentation.annotations.ApiIgnore;

@RestController
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private UserRepository userRepository;

	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)"),
			@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page.") })
	@GetMapping("/posts")
	public GetAllPostsResponse getAllPosts(@RequestHeader(value = "UserID") Long userId, String keyword,
			@ApiIgnore Pageable pageable) throws Exception {
		userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
		return postService.searchPost(userId, keyword, pageable);
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)"),
			@ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page.") })
	@GetMapping("/bookmark")
	public GetAllPostsResponse getBookmarkPost(@RequestHeader(value = "UserID") Long userId,
			@ApiIgnore Pageable pageable) {
		userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
		return postService.findAllBookmarkPost(userId, pageable);
	}

	@PostMapping("/posts")
	public ResponseEntity<PostDto> saveNewPost(@RequestHeader(value = "UserID") Long userId,
			@RequestBody PostDto postDto) {
		userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
		postDto = postService.saveNewPost(postDto, userId);
		return ResponseEntity.ok().body(postDto);
	}

	@PutMapping("/post/{postId}")
	public ResponseEntity<PostDto> modifyPostCategory(@PathVariable(name = "postId") Long postId,
			@RequestBody CategoryDto category) {
		PostDto re = postService.modifyPostCategoryId(postId, category.getId());
		return ResponseEntity.ok().body(re);
	}

	@PutMapping("/post/{postId}/bookmark")
	public ResponseEntity<PostDto> bookmarkPost(@PathVariable(name = "postId") Long postId) {
		PostDto re = postService.updateBookmark(postId);
		return ResponseEntity.ok().body(re);
	}

	@DeleteMapping("/post/{postId}")
	public ResponseEntity<String> deletePost(@PathVariable(name = "postId") Long postId) {
		postService.deletePostOne(postId);
		return ResponseEntity.ok().body("delete success!");
	}
}
