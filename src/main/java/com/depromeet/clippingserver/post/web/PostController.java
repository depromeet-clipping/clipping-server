package com.depromeet.clippingserver.post.web;

import com.depromeet.clippingserver.post.domain.GetAllPostResponse;
import com.depromeet.clippingserver.post.domain.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping
    public GetAllPostResponse getAllPosts() {
        return GetAllPostResponse.fromEntity(postRepository.findAll());
    }
}
