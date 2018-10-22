package com.depromeet.clippingserver.post.domain;

import java.time.LocalDateTime;
import java.util.Optional;

import com.depromeet.clippingserver.category.domain.Category;
import com.depromeet.clippingserver.category.domain.CategoryDto;

import lombok.Builder;
import lombok.Getter;

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
    
    private CategoryDto category;

    public static PostDto fromEntity(Post post) {
	    Optional<Category> categoryopt = Optional.ofNullable(post.getCategory());
	    CategoryDto	categorydto = categoryopt.map(CategoryDto::fromEntity).orElse(null);
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .url(post.getUrl())
                .comment(post.getComment())
                .sourceOf(post.getSourceOf())
                .isBookmark(post.isBookmark())
                .category(categorydto)
                .createdDate(post.getCreatedDate())
                .updatedDate(post.getUpdatedDate())
                .build();
    }
}
