package com.depromeet.clippingserver.post;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.depromeet.clippingserver.post.domain.Category;
import com.depromeet.clippingserver.post.domain.CategoryRepository;
import com.depromeet.clippingserver.user.domain.User;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class CategoryRepositoryTest {

	@Autowired
	private CategoryRepository categoryRepository;

	final Long USER_ID = 1L;

	@Test
	public void a_testfindMaxOrderNoByUserIdReturnZero() {
		Integer findMaxOrderNo = categoryRepository.findMaxOrderNoByUserId(1L).orElse(0);
		assertEquals(true, findMaxOrderNo.equals(0));
	}

	@Test
	public void b_testUpdateOrderNo() {
		// given
		ArrayList<Category> categories = new ArrayList<Category>();
		for (int i = 1; i <= 10; i++) {
			Category category = Category.builder().name("연예기사").orderNo(i).user(User.builder().id(USER_ID).build())
					.build();
			categories.add(category);
		}
		categoryRepository.saveAll(categories);

		// when
		Category[] categoryArr = { Category.builder().id(2L).orderNo(1).build(),
				Category.builder().id(3L).orderNo(2).build(), Category.builder().id(5L).orderNo(5).build(),
				Category.builder().id(1L).orderNo(8).build(), Category.builder().id(7L).orderNo(3).build(),
				Category.builder().id(4L).orderNo(4).build(), Category.builder().id(8L).orderNo(11).build(),
				Category.builder().id(6L).orderNo(6).build(), Category.builder().id(9L).orderNo(7).build(),
				Category.builder().id(10L).orderNo(10).build() };

		Arrays.stream(categoryArr).forEach(category -> {
			categoryRepository.updateOrderNoById(category.getOrderNo(), category.getId());
		});

		Integer findMaxOrderNo = categoryRepository.findMaxOrderNoByUserId(USER_ID).orElse(0);

		categories = categoryRepository.findByUserIdOrderByOrderNoAsc(USER_ID);
		List<Category> tmp = Arrays.stream(categoryArr).sorted(Comparator.comparing(Category::getOrderNo))
				.collect(Collectors.toList());

		// then
		for (int i = 0; i < categories.size(); i++) {
			assertThat(categories.get(i).getId()).isEqualTo(tmp.get(i).getId());
			assertThat(categories.get(i).getOrderNo()).isEqualTo(tmp.get(i).getOrderNo());
		}

		assertEquals("orderNo의 최대값이 일치하지 않습니다", true, findMaxOrderNo.equals(11));
		assertEquals("2번째 정렬된 카테고리가 제대로 정렬되지 않았습니다.", true, categories.get(1).getId().equals(3L));
	}

	@Test
	public void c_testCategorySave() {
		// given
		Integer findMaxOrderNo = categoryRepository.findMaxOrderNoByUserId(1L).orElse(0);
		Category category = Category.builder().name("연예기사").orderNo(findMaxOrderNo + 1)
				.user(User.builder().id(USER_ID).build()).build();

		// when
		Category obj = categoryRepository.save(category);
		Long findUserId = categoryRepository.findById(obj.getId()).get().getUser().getId();

		// then
		assertEquals(USER_ID, findUserId);
		assertEquals(findMaxOrderNo + 1, category.getOrderNo());
	}

	@Test
	public void d_testfindMaxOrderNoByUserIdReturn1000() {
		// given
		Category category = Category.builder().name("연예기사").orderNo(1000).user(User.builder().id(USER_ID).build())
				.build();
		// when
		categoryRepository.save(category);

		Integer findMaxOrderNo = categoryRepository.findMaxOrderNoByUserId(1L).orElse(0);
		assertEquals(true, findMaxOrderNo.equals(1000));
	}
}
