package com.depromeet.clippingserver.user.domain;

import com.depromeet.clippingserver.post.domain.Category;
import com.depromeet.clippingserver.post.domain.Post;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String deviceKey;

    @OneToMany
    private List<Category> categories;

    @OneToMany(mappedBy = "userId")
    private List<Post> posts;
}
