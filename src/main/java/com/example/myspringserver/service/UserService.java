package com.example.myspringserver.service;

import com.example.myspringserver.dto.UserDto;
import com.example.myspringserver.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public UserDto converToDto(User user) {
        UserDto dto = new UserDto();
        dto.setUser_id(user.getUser_id());
        dto.setUser_nickname(user.getUser_nickname());
        dto.setPet_name(user.getPet_name());
        dto.setPet_age(user.getPet_age());
        dto.setUser_introduce(user.getUser_introduce());
        dto.setUser_follower_count(user.getUser_follower_count());
        dto.setUser_following_count(user.getUser_following_count());
        dto.setUser_post_count(user.getUser_post_count());

        return dto;
    }

    public User converToEntity(UserDto dto){
        User user = new User();
        user.setUser_id(dto.getUser_id());
        user.setUser_nickname(dto.getUser_nickname());
        user.setPet_name(dto.getPet_name());
        user.setPet_age(dto.getPet_age());
        user.setUser_introduce(dto.getUser_introduce());
        user.setUser_follower_count(dto.getUser_follower_count());
        user.setUser_following_count(dto.getUser_following_count());
        user.setUser_post_count(dto.getUser_post_count());

        return user;
    }
}
