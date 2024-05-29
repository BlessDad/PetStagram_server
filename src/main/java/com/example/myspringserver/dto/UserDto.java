package com.example.myspringserver.dto;

import com.example.myspringserver.entity.Post;
import com.example.myspringserver.entity.Walking;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class UserDto {
    private Integer user_id;
    private String user_nickname;
    private String pet_name;
    private Integer pet_age;
    private String user_introduce;
    private Integer user_follower_count;
    private Integer user_following_count;
    private Integer user_post_count;
    private String imageUrl;

    //연관관계
    private List<PostDto> posts;
    private List<WalkingDto> walkings;
    private List<BookmarkDto> bookmarks;

    public List<PostDto> getPosts() {
        return posts != null ? posts: Collections.emptyList();
    }

    public List<WalkingDto> getWalkings() {
        return walkings != null ? walkings:Collections.emptyList();
    }

    public List<BookmarkDto> getBookmarks() {
        return bookmarks != null ? bookmarks:Collections.emptyList();
    }
}
