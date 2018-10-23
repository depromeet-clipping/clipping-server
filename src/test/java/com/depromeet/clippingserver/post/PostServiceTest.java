package com.depromeet.clippingserver.post;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.depromeet.clippingserver.category.domain.CategoryDto;
import com.depromeet.clippingserver.post.domain.PostDto;
import com.depromeet.clippingserver.post.domain.PostService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostServiceTest {
	Long postId = 1L;
	
	@Autowired
	private PostService postService;

	@Test
	public void testSavePostService() {
		//given
		String url = "http://www.hani.co.kr/arti/society/society_general/866836.html?_fr=mt1";
		CategoryDto category = CategoryDto.builder().id(1L).build();
		String comment = "좀 있다 화장실에서 볼 것";
		PostDto postDto = PostDto.builder().url(url).category(category).comment(comment).build();
		postDto = postService.saveNewPost(postDto, 1L);
		
		String sourceOf = postDto.getSourceOf();
		String title = postDto.getTitle();
		String thumbnailImgLink = postDto.getThumnailLink();
		
		assertEquals("운영비로 술·골프…사립유치원 뺨친 ‘민간 요양원 비리’ : 사회일반 : 사회 : 뉴스 : 한겨레", title);
		assertEquals("www.hani.co.kr", sourceOf);
		assertEquals("http://img.hani.co.kr/imgdb/resize/2018/1022/00502597_20181022.JPG", thumbnailImgLink);
	}
	
	@Test
	public void testModifyPostCategoryId() {
		Long categoryId = 2L;
		PostDto postDto = postService.modifyPostCategoryId(postId, categoryId);
		assertEquals(postId, postDto.getId());
	}
	
	@Test
	public void testdeletePostOne() {
		postService.deletePostOne(postId);
	}
}
