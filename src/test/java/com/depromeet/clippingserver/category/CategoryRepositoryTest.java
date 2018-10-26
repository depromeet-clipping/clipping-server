//package com.depromeet.clippingserver.category;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.Assert.assertEquals;
//
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import org.junit.FixMethodOrder;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.MethodSorters;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.depromeet.clippingserver.category.domain.Category;
//import com.depromeet.clippingserver.category.domain.CategoryDto;
//import com.depromeet.clippingserver.category.domain.CategoryRepository;
//import com.depromeet.clippingserver.category.domain.CategoryService;
//import com.depromeet.clippingserver.exception.CategoryNotFoundException;
//import com.depromeet.clippingserver.post.domain.GetAllPostsResponse;
//import com.depromeet.clippingserver.post.domain.Post;
//import com.depromeet.clippingserver.post.domain.PostRepository;
//import com.depromeet.clippingserver.user.domain.User;
//
//@RunWith(SpringRunner.class)
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//@SpringBootTest
//@ActiveProfiles("test")
//public class CategoryRepositoryTest {
//
//	@Autowired
//	private CategoryRepository categoryRepository;
//
//	@Autowired
//	private CategoryService categoryService;
//
//	@Autowired
//	private PostRepository postRepository;
//
//	final Long USER_ID = 1L;
//
//	@Test
//	public void a_testfindMaxOrderNoByUserIdReturnZero() {
//		Integer findMaxOrderNo = categoryRepository.findMaxOrderNoByUserId(1L).orElse(0);
//		assertEquals(true, findMaxOrderNo.equals(0));
//	}
//
//	@Test
//	public void b_testUpdateOrderNo() {
//		// given
//		ArrayList<Category> categories = new ArrayList<Category>();
//		for (int i = 1; i <= 10; i++) {
//			Category category = Category.builder().name("연예기사").orderNo(i).user(User.builder().id(USER_ID).build())
//					.build();
//			categories.add(category);
//		}
//		categoryRepository.saveAll(categories);
//
//		// when
//		Long[] categoryId = { 9L, 8L, 7L, 6L, 5L, 1L, 2L, 3L, 4L, 10L };
//		
//		List<Category> categoryLs = new ArrayList<>();
//		for(int i = 0; i < categoryId.length; i++) {
//			categoryLs.add(Category.builder().id(categoryId[i]).orderNo(i).build() );
//		}
//		categoryLs.forEach(category -> {
//			categoryRepository.updateOrderNoById(category.getOrderNo(), category.getId());
//		});
//
//		Integer findMaxOrderNo = categoryRepository.findMaxOrderNoByUserId(USER_ID).orElse(0);
//
//		categories = categoryRepository.findByUserIdAndDeletedFalseOrderByOrderNoAsc(USER_ID);
//		List<Category> tmp = categoryLs.stream().sorted(Comparator.comparing(Category::getOrderNo))
//				.collect(Collectors.toList());
//
//		// then
//		for (int i = 0; i < categories.size(); i++) {
//			assertThat(categories.get(i).getId()).isEqualTo(tmp.get(i).getId());
//			assertThat(categories.get(i).getOrderNo()).isEqualTo(tmp.get(i).getOrderNo());
//		}
//
//		assertEquals("orderNo의 최대값이 일치하지 않습니다", true, findMaxOrderNo.equals(9));
//		assertEquals("2번째 정렬된 카테고리가 제대로 정렬되지 않았습니다.", true, categories.get(1).getId().equals(8L));
//	}
//
//	@Test
//	public void c_testCategorySave() {
//		// given
//		Integer findMaxOrderNo = categoryRepository.findMaxOrderNoByUserId(1L).orElse(0);
//		Category category = Category.builder().name("연예기사").orderNo(findMaxOrderNo + 1)
//				.user(User.builder().id(USER_ID).build()).build();
//
//		// when
//		Category obj = categoryRepository.save(category);
//		Long findUserId = categoryRepository.findById(obj.getId()).get().getUser().getId();
//
//		// then
//		assertEquals(USER_ID, findUserId);
//		assertEquals(findMaxOrderNo + 1, category.getOrderNo());
//	}
//
//	@Test
//	public void d_testfindMaxOrderNoByUserIdReturn1000() {
//		// given
//		Category category = Category.builder().name("연예기사").orderNo(1000).user(User.builder().id(USER_ID).build())
//				.build();
//		// when
//		categoryRepository.save(category);
//
//		Integer findMaxOrderNo = categoryRepository.findMaxOrderNoByUserId(1L).orElse(0);
//		assertEquals(true, findMaxOrderNo.equals(1000));
//	}
//
//	@Test
//	public void e_testFindParticularPosts() {
//		// given
//		ArrayList<Post> posts = new ArrayList<>();
//		for (int i = 0; i < 10; i++) {
//			posts.add(Post.builder().title("title").url("naver.com").comment("한국").sourceOf("naver").userId(USER_ID)
//					.build());
//		}
//		Category category = Category.builder().name("연예기사").orderNo(1).user(User.builder().id(USER_ID).build()).build();
//		posts.forEach(post -> category.addPost(post));
//		categoryRepository.save(category);
//
//		List<Post> testPosts = postRepository
//				.findByUserIdAndDeletedFalseOrderByUpdatedDateDesc(USER_ID, PageRequest.of(0, 100)).stream()
//				.filter(post -> {
//					Optional<Category> opt = Optional.ofNullable(post.getCategory());
//					if (opt.isPresent()) {
//						return opt.get().getId().equals(1L);
//					} else {
//						return false;
//					}
//				}).sorted(Comparator.comparing(Post::getUpdatedDate).reversed()).collect(Collectors.toList());
//
//		// when
//		GetAllPostsResponse postDtos = categoryService.findParticularPosts(1L, USER_ID, PageRequest.of(0, 100));
//
//		// then
//		for (int i = 0; i < testPosts.size(); i++) {
//			assertEquals(testPosts.get(i).getId(), postDtos.getPosts().get(i).getId());
//		}
//	}
//
//	@Test
//	public void f_testUpdateTitle() {
//		// given
//		String testname = "연예기사";
//		Category testEntity = Category.builder().name(testname).orderNo(1000).user(User.builder().id(USER_ID).build())
//				.build();
//		categoryRepository.save(testEntity);
//		
//		// when
//		String name = "비리 유치원 명단 공개 ";
//		CategoryDto category = categoryService.updateName(name, 1L);
//		assertEquals(name, category.getName());
//		assertEquals(false, testname.equals(name));
//	}
//	
//	@Test(expected=CategoryNotFoundException.class)
//	public void g_testExpectedUpdateTitle() {
//		// given
//		String name = "비리 유치원 명단 공개 ";
//		// when-then
//		categoryService.updateName(name, 99L);
//	}
//}
