package com.depromeet.clippingserver;

import com.depromeet.clippingserver.post.domain.Post;
import com.depromeet.clippingserver.post.domain.PostRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest @ActiveProfiles("test")
public class ClippingServerApplicationTests {

    @Autowired
    private PostRepository postRepository;

    @Test
    public void contextLoads() {
    }

    @Test
    public void jpaAuditing() {
        LocalDateTime now = LocalDateTime.now();

        Post post = postRepository.save(Post.builder()
                .title("제목")
                .url("URL")
                .personalTitle("")
                .build());

        Assert.assertTrue(post.getCreatedDate().isAfter(now));
        Assert.assertTrue(post.getUpdatedDate().isAfter(now));
    }
}
