package com.depromeet.clippingserver.post.domain;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

import org.jsoup.Connection;
import org.jsoup.nodes.Element;

import com.depromeet.clippingserver.category.domain.Category;
import com.depromeet.clippingserver.category.domain.CategoryDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

	private Long id;
	private String title;
	private String url;
	private String comment;
	private String sourceOf;
	private String thumnailLink;
	private boolean isBookmark;
	private LocalDateTime createdDate;
	private LocalDateTime updatedDate;

	private CategoryDto category;

	public static PostDto fromEntity(Post post) {
		Optional<Category> categoryopt = Optional.ofNullable(post.getCategory());
		CategoryDto categorydto = categoryopt.map(CategoryDto::fromEntity).orElse(null);
		return PostDto.builder().id(post.getId()).title(post.getTitle()).url(post.getUrl()).comment(post.getComment())
				.thumnailLink(post.getThumbnailImgLink())
				.sourceOf(post.getSourceOf()).isBookmark(post.isBookmark()).category(categorydto)
				.createdDate(post.getCreatedDate()).updatedDate(post.getUpdatedDate()).build();
	}

	private String getThumnailLink(Connection conn) {
		try {
			Element el = conn.get().head().tagName("meta").getElementsByAttributeValue("property", "og:image").get(0);
			String ThumnailLink = el.attr("content");
			return ThumnailLink;
		} catch (IOException e) {
			return null;
		}
	}

	private String getTitle(Connection conn) {
		try {
			return conn.get().title();
		} catch (IOException e) {
			return null;
		}
	}

	private String getSourceOf(String url) {
		if (url == null)
			return "";
		url = url.replaceAll("(http://)|(https://)", "");
		String[] tmp = url.split("/");
		return tmp[0];
	}

	public void addThumnailAndTitleAndSourceOf(String url, Connection conn) {
		this.title = getTitle(conn);
		this.thumnailLink = getThumnailLink(conn);
		this.sourceOf = getSourceOf(url);
	}
}
