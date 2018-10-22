package com.depromeet.clippingserver.post;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.depromeet.clippingserver.category.domain.Category;
import com.depromeet.clippingserver.post.domain.Post;
import com.depromeet.clippingserver.post.domain.PostRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostServiceTest {
	@Autowired
	private PostRepository postRepository;
	
	@Test
	public void testsavePostService() {
		//given
		String url = "http://www.hani.co.kr/arti/society/society_general/866836.html?_fr=mt1";
		Category category = Category.builder().id(1L).build();
		String comment = "좀 있다 화장실에서 볼 것";
		String sourceOf = getSourceOf(url);
		String title = getTitle(url);
		String thumbnailImgLink = getThumnailLink(url);
		
		assertEquals("운영비로 술·골프…사립유치원 뺨친 ‘민간 요양원 비리’ : 사회일반 : 사회 : 뉴스 : 한겨레", title);
		assertEquals("www.hani.co.kr", sourceOf);
		assertEquals("http://img.hani.co.kr/imgdb/resize/2018/1022/00502597_20181022.JPG", thumbnailImgLink);
		
		Post post = Post.builder().url(url).comment(comment).category(category).sourceOf(sourceOf).title(title).build();
		postRepository.save(post);
	}

	private String getThumnailLink(String url) {
		try {
			Element el = Jsoup.connect(url).get().head().tagName("meta").getElementsByAttributeValue("property", "og:image").get(0);
			String ThumnailLink = el.attr("content");
			return ThumnailLink;
		} catch (IOException e) {
			return null;
		}
	}

	private String getTitle(String url) {
		try {
			return Jsoup.connect(url).get().title();
		} catch (IOException e) {
			return null;
		}
	}

	private String getSourceOf(String url) {
		if(url == null) return "";
		url = url.replaceAll("(http://)|(https://)", "");
		String[] tmp = url.split("/");
		return tmp[0];
	}
	

}
