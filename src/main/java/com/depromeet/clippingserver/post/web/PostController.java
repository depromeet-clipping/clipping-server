package com.depromeet.clippingserver.post.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.depromeet.clippingserver.exception.UserNotFoundException;
import com.depromeet.clippingserver.post.domain.GetAllPostsResponse;
import com.depromeet.clippingserver.post.domain.PostRepository;
import com.depromeet.clippingserver.user.domain.UserRepository;

@RestController("/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;
    
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public GetAllPostsResponse getAllPosts(@RequestHeader(value="UserID") Long userId) throws Exception{
    	userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return GetAllPostsResponse.fromEntity(postRepository.findByUserId(userId));
    }
}
