package com.example.myspringserver.dto;

import lombok.Getter;
import lombok.Setter;

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

}
