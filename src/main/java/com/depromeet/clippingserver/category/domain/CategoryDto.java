package com.depromeet.clippingserver.category.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter 
@AllArgsConstructor @NoArgsConstructor
@Builder
public class CategoryDto {
	private Long id;
	private String name;
	private int orderNo;
	private boolean deleted;
	private int postCount;

	public static CategoryDto fromEntity(Category category) {
		return CategoryDto.builder().id(category.getId())
									.name(category.getName())
									.orderNo(category.getOrderNo())
									.postCount(category.getPosts().size())
									.deleted(category.isDeleted())
									.build();
	}
}
