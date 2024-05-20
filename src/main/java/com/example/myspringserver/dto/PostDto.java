package com.example.myspringserver.dto;

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

    private List<CommentDto> comments;  // 댓글 목록 추가

    public List<CommentDto> getComments() {
        return comments != null ? comments : Collections.emptyList();
    }
}

//test