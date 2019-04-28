package com.depromeet.clippingserver.post.domain;

import com.depromeet.clippingserver.category.domain.Category;
import com.depromeet.clippingserver.category.domain.CategoryDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jsoup.Connection;
import org.jsoup.select.Elements;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

	@ApiModelProperty(hidden = true)
	private Long id;

	@ApiModelProperty(hidden = true)
	private String title;

	@NotNull
	@ApiModelProperty(example = "http://www.smaple.com", value = "신문기사 url을 입력해주세요.", required = true)
	private String url;

	private boolean isBookmark;

	@ApiModelProperty(example = "", hidden = true)
	@JsonIgnore
	private String comment;

	@ApiModelProperty(hidden = true)
	private String sourceOf;

	@ApiModelProperty(hidden = true)
	private String thumnailLink;


	@ApiModelProperty(hidden = true)
	private LocalDateTime createdDate;

	@ApiModelProperty(hidden = true)
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
			Elements el = conn.get().head().tagName("meta").getElementsByAttributeValue("property", "og:image");
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
