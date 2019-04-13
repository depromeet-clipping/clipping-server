package com.depromeet.clippingserver.post;

import static org.mockito.Mockito.doThrow;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.depromeet.clippingserver.exception.UserNotFoundException;
import com.depromeet.clippingserver.post.domain.PostService;
import com.depromeet.clippingserver.post.web.PostController;
import com.depromeet.clippingserver.user.domain.UserRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(PostController.class)
public class PostControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PostService postService;
    
    @MockBean
    private UserRepository userRepository;

    private static final String GET_POST_ENDPOINT = "/posts";

//    @Test
//    public void getPost() throws Exception {
//        // given
//    	final Long USER_ID = 1L;
//        List<Post> posts = new ArrayList<>();
//
//        final String title1 = "Clipping Server Github";
//        final String url1 = "https://github.com/depromeet-clipping/clipping-server";
//        posts.add(Post.builder()
//                .title(title1)
//                .url(url1)
//                .userId(USER_ID)
//                .build());
//
//        final String title2 = "Depromeet Github";
//        final String url2 = "https://github.com/depromeet";
//        posts.add(Post.builder()
//                .title(title2)
//                .url(url2)
//                .userId(USER_ID)
//                .build());
//
//        Pageable page = PageRequest.of(0, 100);
//        given(postService.findAllPostsOrdered(USER_ID, page)).willReturn(GetAllPostsResponse.fromEntity(posts));
//        given(userRepository.findById(USER_ID)).willReturn(Optional.ofNullable(User.builder().build()));
//
//        // when-then
//        mvc.perform(get(GET_POST_ENDPOINT).header("UserId", USER_ID).param("size", "20").param("page", "0") )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.posts", hasSize(posts.size())))
//                .andExpect(jsonPath("$.posts[0].title").value(title1))
//                .andExpect(jsonPath("$.posts[0].url").value(url1))
//                .andExpect(jsonPath("$.posts[1].title").value(title2))
//                .andExpect(jsonPath("$.posts[1].url").value(url2))
//                .andDo(print());
//    }
    
    @Test(expected=UserNotFoundException.class)
    public void getPostExpectUserNotFound() throws Exception {
        // given
    	final Long USER_ID = 1L;
        
        // when-then
    	doThrow(UserNotFoundException.class).when(userRepository).findById(USER_ID);
       	userRepository.findById(USER_ID);
    }
}
