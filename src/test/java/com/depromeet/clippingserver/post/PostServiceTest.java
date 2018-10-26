package com.depromeet.clippingserver.post;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.depromeet.clippingserver.category.domain.Category;
import com.depromeet.clippingserver.category.domain.CategoryDto;
import com.depromeet.clippingserver.category.domain.CategoryRepository;
import com.depromeet.clippingserver.post.domain.GetAllPostsResponse;
import com.depromeet.clippingserver.post.domain.PostDto;
import com.depromeet.clippingserver.post.domain.PostService;

@RunWith(SpringRunner.class)
@SpringBootTest @ActiveProfiles("test")
@Transactional
public class PostServiceTest {
	Long postId = 1L;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Test
	public void testSavePostService() {
		//given
		categoryRepository.save(Category.builder().name("한국").build());
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
		Category cateogry = categoryRepository.save(Category.builder().name("한국").build());
		PostDto postDto = postService.modifyPostCategoryId(postId, cateogry.getId());
		assertEquals(postId, postDto.getId());
	}
	
	@Test
	public void testSearchPost() {
		PageRequest pageReq = PageRequest.of(0, 5);
		Long userId = 1L;
		String keyword = "";
		GetAllPostsResponse posts = postService.searchPost(userId, keyword, pageReq);
		assertEquals(5, posts.getPosts().size());
	}
	
	@Test
	public void testdeletePostOne() {
		postService.deletePostOne(postId);
	}
	
	@Test
	public void testUpdateIsBookmark() {
		Long postId = 1L;
		PostDto postDto = postService.updateBookmark(postId);
		
		assertEquals(true, postDto.isBookmark());
	}
	
	@Test
	public void testGetAllBookmarkPost() {
		Long userId = 1L;
		GetAllPostsResponse re = postService.findAllBookmarkPost(userId, PageRequest.of(0, 20));
		
		re.getPosts().forEach(post -> {
			assertEquals(post.isBookmark(), true);
		});
	}
}
