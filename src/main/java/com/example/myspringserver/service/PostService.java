package com.example.myspringserver.service;

import com.example.myspringserver.dto.PostDto;
import com.example.myspringserver.entity.Post;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    public PostDto convertToDto(Post post) {
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setImageUrl(post.getImageUrl());
        return dto;
    }

    public Post convertToEntity(PostDto dto) {
        Post post = new Post();
        post.setId(dto.getId());
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setImageUrl(dto.getImageUrl());
        return post;
    }
}