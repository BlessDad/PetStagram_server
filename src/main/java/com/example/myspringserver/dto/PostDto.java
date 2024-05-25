package com.example.myspringserver.dto;

import com.example.myspringserver.entity.Tag;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private String imageUrl;

    private List<CommentDto> comments;
    private List<TagDto> tags;

    private Integer user_id;

    public List<CommentDto> getComments() {
        return comments != null ? comments : Collections.emptyList();
    }

    public List<TagDto> getTags() {
        return tags != null ? tags : Collections.emptyList();
    }
}
