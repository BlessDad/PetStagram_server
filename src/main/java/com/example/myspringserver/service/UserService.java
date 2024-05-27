package com.example.myspringserver.service;

import com.example.myspringserver.dto.*;
import com.example.myspringserver.entity.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserService {
    public UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setUser_id(user.getUser_id());
        dto.setUser_nickname(user.getUser_nickname());
        dto.setPet_name(user.getPet_name());
        dto.setPet_age(user.getPet_age());
        dto.setUser_introduce(user.getUser_introduce());
        dto.setUser_follower_count(user.getUser_follower_count());
        dto.setUser_following_count(user.getUser_following_count());
        dto.setUser_post_count(user.getUser_post_count());
        dto.setPosts(user.getPosts().stream().map(this::convertPostToDto).collect(Collectors.toList()));
        
        dto.setWalkings(user.getWalkings().stream().map(this::convertWalkingToDto).collect(Collectors.toList()));
        return dto;
    }

    private WalkingDto convertWalkingToDto(Walking walking) {
        WalkingDto walkingDto = new WalkingDto();
        walkingDto.setWalking_id(walking.getWalking_id());
        walkingDto.setWalking_start(walking.getWalking_start());
        walkingDto.setWalking_end(walking.getWalking_end());
        walkingDto.setWalking_distance(walking.getWalking_distance());
        walkingDto.setWalking_calorie(walking.getWalking_calorie());
        walkingDto.setWalking_speed(walking.getWalking_speed());
        walkingDto.setUser_id(walking.getUser().getUser_id());
        return walkingDto;
    }

    public User convertToEntity(UserDto dto) {
        User user = new User();
        user.setUser_id(dto.getUser_id());
        user.setUser_nickname(dto.getUser_nickname());
        user.setPet_name(dto.getPet_name());
        user.setPet_age(dto.getPet_age());
        user.setUser_introduce(dto.getUser_introduce());
        user.setUser_follower_count(dto.getUser_follower_count());
        user.setUser_following_count(dto.getUser_following_count());
        user.setUser_post_count(dto.getUser_post_count());
        
        user.setWalkings(dto.getWalkings().stream().
                map(walkingDto -> convertWalkingToEntity(walkingDto, user)).collect(Collectors.toList()));
        return user;
    }

    private Walking convertWalkingToEntity(WalkingDto walkingDto, User user) {
        Walking walking = new Walking();
        walking.setWalking_id(walkingDto.getWalking_id());
        walking.setWalking_start(walkingDto.getWalking_start());
        walking.setWalking_end(walkingDto.getWalking_end());
        walking.setWalking_distance(walkingDto.getWalking_distance());
        walking.setWalking_calorie(walkingDto.getWalking_calorie());
        walking.setWalking_speed(walkingDto.getWalking_speed());
        walking.setUser(user);
        return walking;
    }

    private PostDto convertPostToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setImageUrl(post.getImageUrl());
        postDto.setComments(post.getComments().stream().map(this::convertCommentToDto).collect(Collectors.toList()));
        postDto.setTags(post.getTags().stream().map(this::convertTagToDto).collect(Collectors.toList()));
        postDto.setUser_id(post.getUser().getUser_id());
        return postDto;
    }

    private CommentDto convertCommentToDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setComment_id(comment.getComment_id());
        commentDto.setComment_writer(comment.getComment_writer());
        commentDto.setComment_content(comment.getComment_content());
        commentDto.setPost_id(comment.getPost().getId());
        return commentDto;
    }

    private TagDto convertTagToDto(Tag tag) {
        TagDto tagDto = new TagDto();
        tagDto.setTag_id(tag.getTag_id());
        tagDto.setTag_name(tag.getTag_name());
        tagDto.setPost_id(tag.getPost().getId());
        return tagDto;
    }
}
