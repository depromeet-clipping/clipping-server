package com.depromeet.clippingserver.post.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    private int orderNo;
    private boolean deleted;

    @OneToMany(mappedBy = "category")
    private List<Post> posts;
}
