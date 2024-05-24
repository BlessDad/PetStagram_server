package com.example.myspringserver.service;

import com.example.myspringserver.dto.TagDto;
import com.example.myspringserver.entity.Post;
import com.example.myspringserver.entity.Tag;
import com.example.myspringserver.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {
    @Autowired
    private PostRepository postRepository;

    public TagDto convertToDto(Tag tag){
        TagDto dto = new TagDto();

        dto.setTag_id(tag.getTag_id());
        dto.setTag_name(tag.getTag_name());
        dto.setPost_id(tag.getPost().getId());

        return dto;
    }

    public Tag convertToEntity(TagDto dto){
        Tag tag = new Tag();

        tag.setTag_id(dto.getTag_id());
        tag.setTag_name(dto.getTag_name());

        Post post = postRepository.findById(dto.getPost_id())
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID: " + dto.getPost_id()));
        tag.setPost(post);

        return tag;
    }
}
