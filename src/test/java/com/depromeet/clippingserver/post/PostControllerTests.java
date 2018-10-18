package com.depromeet.clippingserver.post;

import com.depromeet.clippingserver.post.domain.Post;
import com.depromeet.clippingserver.post.domain.PostRepository;
import com.depromeet.clippingserver.post.web.PostController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PostController.class)
public class PostControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PostRepository postRepository;

    private static final String GET_POST_ENDPOINT = "/posts";

    @Test
    public void getPost() throws Exception {
        // given
        List<Post> posts = new ArrayList<>();

        final String title1 = "Clipping Server Github";
        final String url1 = "https://github.com/depromeet-clipping/clipping-server";
        posts.add(Post.builder()
                .title(title1)
                .url(url1)
                .build());

        final String title2 = "Depromeet Github";
        final String url2 = "https://github.com/depromeet";
        posts.add(Post.builder()
                .title(title2)
                .url(url2)
                .build());

        given(postRepository.findAll()).willReturn(posts);

        // when-then
        mvc.perform(get(GET_POST_ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(posts.size())))
                .andExpect(jsonPath("$[0].title").value(title1))
                .andExpect(jsonPath("$[0].url").value(url1))
                .andExpect(jsonPath("$[1].title").value(title2))
                .andExpect(jsonPath("$[1].url").value(url2))
                .andDo(print());
    }
}
