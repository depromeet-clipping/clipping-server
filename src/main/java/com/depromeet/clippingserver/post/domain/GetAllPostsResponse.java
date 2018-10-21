package com.depromeet.clippingserver.post.domain;

import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class GetAllPostsResponse {

    private List<PostDto> posts;

    public static GetAllPostsResponse fromEntity(List<Post> posts) {
        return GetAllPostsResponse.builder()
                .posts(posts.stream()
                        .map(post -> PostDto.fromEntity(post))
                        .collect(Collectors.toList()))
                .build();
    }
}
