package com.depromeet.clippingserver.post.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.depromeet.clippingserver.post.domain.GetAllPostsResponse;
import com.depromeet.clippingserver.post.domain.PostRepository;

@RestController("/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping
    public GetAllPostsResponse getAllPosts() {
        return GetAllPostsResponse.fromEntity(postRepository.findAll());
    }
}
