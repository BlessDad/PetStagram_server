package com.example.myspringserver.service;

import com.example.myspringserver.dto.CommentDto;
import com.example.myspringserver.entity.Comment;
import com.example.myspringserver.entity.Post;
import com.example.myspringserver.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private PostRepository postRepository;

    public CommentDto convertToDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setComment_id(comment.getComment_id());
        dto.setComment_writer(comment.getComment_writer());
        dto.setComment_content(comment.getComment_content());
        dto.setPost_id(comment.getPost().getId()); // 게시물 ID 추가
        return dto;
    }

    public Comment convertToEntity(CommentDto dto) {
        Comment comment = new Comment();
        comment.setComment_id(dto.getComment_id());
        comment.setComment_writer(dto.getComment_writer());
        comment.setComment_content(dto.getComment_content());

        // Post 설정
        Post post = postRepository.findById(dto.getPost_id())
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID: " + dto.getPost_id()));
        comment.setPost(post);

        return comment;
    }
}
