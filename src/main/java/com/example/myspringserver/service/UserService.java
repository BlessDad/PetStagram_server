package com.example.myspringserver.service;

import com.example.myspringserver.dto.CommentDto;
import com.example.myspringserver.dto.PostDto;
import com.example.myspringserver.dto.TagDto;
import com.example.myspringserver.dto.UserDto;
import com.example.myspringserver.entity.Comment;
import com.example.myspringserver.entity.Post;
import com.example.myspringserver.entity.Tag;
import com.example.myspringserver.entity.User;
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
        return dto;
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
        return user;
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
