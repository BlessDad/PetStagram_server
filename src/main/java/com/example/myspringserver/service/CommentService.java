package com.example.myspringserver.service;

import com.example.myspringserver.dto.CommentDto;
import com.example.myspringserver.entity.Comment;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    public CommentDto convertToDto (Comment comment) {
        CommentDto dto = new CommentDto();

        dto.setComment_id(comment.getComment_id());
        dto.setComment_writer(comment.getComment_writer());
        dto.setComment_content(comment.getComment_content());

        return dto;
    }

    public Comment convertToEntity(CommentDto dto){
        Comment comment = new Comment();

        comment.setComment_id(dto.getComment_id());
        comment.setComment_writer(dto.getComment_writer());
        comment.setComment_content(dto.getComment_content());

        return comment;
    }
}
