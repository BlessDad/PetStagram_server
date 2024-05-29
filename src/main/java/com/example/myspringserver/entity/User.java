package com.example.myspringserver.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.IdGeneratorType;

import java.util.List;

@Getter
@Setter
@Table(name = "user")
@Entity
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer user_id;

    @Column (name = "user_nickname")
    private String user_nickname;

    @Column (name = "pet_name")
    private String pet_name;

    @Column (name = "pet_age")
    private Integer pet_age;

    @Column (name = "user_introduce")
    private String user_introduce;

    @Column (name = "user_follower_count")
    private Integer user_follower_count;

    @Column (name =  "user_following_count")
    private Integer user_following_count;

    @Column (name = "user_post_count")
    private Integer user_post_count;

    @Column (name = "imageUrl")
    private String imageUrl;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Walking> walkings;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarks;

}
