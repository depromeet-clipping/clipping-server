package com.depromeet.clippingserver.category;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.depromeet.clippingserver.category.domain.CategoryDto;
import com.depromeet.clippingserver.category.domain.CategoryService;
import com.depromeet.clippingserver.category.web.CategoryController;

@RunWith(SpringRunner.class)
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {
	final Long USER_ID = 1L;
	final String TEST_END_POINT = "/categories";
	
	@Autowired
	private MockMvc mvc;

	@MockBean
	CategoryService categoryService;
	List<CategoryDto> categories = new ArrayList<>();

	@Before
	public void beforeTest() {
		categories.add(CategoryDto.builder().id(1L).name("스페인어 독학").orderNo(1).postCount(10).build());
		categories.add(CategoryDto.builder().id(2L).name("웹개발").orderNo(2).postCount(2).build());
	}
	
	@Test
	public void testGetCategories() throws Exception {
		//given 
		given(categoryService.findValidAndOrderedCategory(USER_ID)).willReturn(categories);
		
		//then
		mvc.perform(get(TEST_END_POINT).header("UserId", USER_ID))
										.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
										.andExpect(jsonPath("$[0].id").value(categories.get(0).getId()))
										.andExpect(jsonPath("$[0].name").value(categories.get(0).getName()))
										.andExpect(jsonPath("$[0].orderNo").value(categories.get(0).getOrderNo()))
										.andExpect(jsonPath("$[0].postCount").value(categories.get(0).getPostCount()))
										.andExpect(status().isOk())
										.andDo(print());
	}
	
//	@Test
//	public void testcreateCategory() throws Exception {
//		when(categoryService.saveNewCategory(categories.get(0), USER_ID)).thenReturn(categories.get(0));
//		
//		mvc.perform(post(TEST_END_POINT).accept(MediaType.APPLICATION_JSON_UTF8_VALUE).contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).header("UserId", USER_ID))
//										.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//										.andExpect(jsonPath("$[0].id").value(categories.get(0).getId()))
//										.andExpect(jsonPath("$[0].name").value(categories.get(0).getName()))
//										.andExpect(jsonPath("$[0].orderNo").value(categories.get(0).getOrderNo()))
//										.andExpect(jsonPath("$[0].postCount").value(categories.get(0).getPostCount()))
//										.andExpect(status().isCreated())
//										.andReturn();
//	}

}
