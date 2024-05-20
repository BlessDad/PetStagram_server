package com.example.myspringserver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    private Integer comment_id;
    private String comment_writer;
    private String comment_content;

    private Long post_id;  // 게시물 ID 추가
}
