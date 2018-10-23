package com.depromeet.clippingserver.post.domain;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.depromeet.clippingserver.category.domain.Category;

@Service
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
		postRepository.save(post);
		return postDto;
	}

}
