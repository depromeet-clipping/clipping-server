package com.depromeet.clippingserver.post.domain;

import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class GetAllPostResponse {

    private List<PostDto> posts;

    public static GetAllPostResponse fromEntity(List<Post> posts) {
        return GetAllPostResponse.builder()
                .posts(posts.stream()
                        .map(post -> PostDto.fromEntity(post))
                        .collect(Collectors.toList()))
                .build();
    }
}
