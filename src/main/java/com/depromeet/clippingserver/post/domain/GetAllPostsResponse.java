package com.depromeet.clippingserver.post.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;

@Builder@Getter
public class GetAllPostsResponse {
    private List<PostDto> posts;
    private int currentPageNum;
    private int totalPageSize;
    private boolean hasNextPage;
    private boolean hasPriviousPage;
    private boolean isLastPage;
    private boolean isFirstPage;
    
    public static GetAllPostsResponse fromEntity(List<Post> posts) {
        return GetAllPostsResponse.builder()
                .posts(posts.stream()
                        .map(PostDto::fromEntity)
                        .collect(Collectors.toList()))
                .build();
    }

	public void addPageInfo(Page<Post> post) {
		this.currentPageNum = post.getNumber() + 1;
		this.totalPageSize = post.getTotalPages();
		this.hasNextPage = post.hasNext();
		this.hasPriviousPage = post.hasPrevious();
		this.isLastPage = post.isLast();
		this.isFirstPage = post.isFirst();
	}
}
