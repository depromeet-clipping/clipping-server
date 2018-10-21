package com.depromeet.clippingserver.post.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostDto {

    private Long id;
    private String title;
    private String url;
    private String comment;
    private String sourceOf;
    private boolean isBookmark;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public static PostDto fromEntity(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .url(post.getUrl())
                .comment(post.getComment())
                .sourceOf(post.getSourceOf())
                .isBookmark(post.isBookmark())
                .createdDate(post.getCreatedDate())
                .updatedDate(post.getUpdatedDate())
                .build();
    }
}
