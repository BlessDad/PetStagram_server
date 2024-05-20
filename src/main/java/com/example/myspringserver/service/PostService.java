package com.example.myspringserver.service;

import com.example.myspringserver.dto.PostDto;
import com.example.myspringserver.dto.CommentDto;
import com.example.myspringserver.dto.TagDto;
import com.example.myspringserver.entity.Post;
import com.example.myspringserver.entity.Comment;
import com.example.myspringserver.entity.Tag;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

@Service
public class PostService {

    public PostDto convertToDto(Post post) {
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setContent(post.getContent());
        dto.setImageUrl(post.getImageUrl());

        dto.setComments(post.getComments().stream()
                .map(this::convertCommentToDto)
                .collect(Collectors.toList())); // 댓글 목록 추가


        dto.setTags(post.getTags().stream()
                .map(this::convertTagToDto)
                .collect(Collectors.toList())
        );


        return dto;
    }

    public Post convertToEntity(PostDto dto) {
        Post post = new Post();
        post.setId(dto.getId());
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        post.setImageUrl(dto.getImageUrl());
        post.setComments(dto.getComments().stream()
                .map(commentDto -> convertCommentToEntity(commentDto, post))
                .collect(Collectors.toList())); // 댓글 목록 추가
        return post;
    }

    private CommentDto convertCommentToDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setComment_id(comment.getComment_id());
        commentDto.setComment_writer(comment.getComment_writer());
        commentDto.setComment_content(comment.getComment_content());
        commentDto.setPost_id(comment.getPost().getId()); // 게시물 ID 추가
        return commentDto;
    }

    private TagDto convertTagToDto(Tag tag) {
        TagDto tagDto = new TagDto();
        tagDto.setTag_id(tag.getTag_id());
        tagDto.setTag_name(tag.getTag_name());
        tagDto.setPost_id(tag.getPost().getId());
        return tagDto;
    }

    private Comment convertCommentToEntity(CommentDto commentDto, Post post) {
        Comment comment = new Comment();
        comment.setComment_id(commentDto.getComment_id());
        comment.setComment_writer(commentDto.getComment_writer());
        comment.setComment_content(commentDto.getComment_content());
        comment.setPost(post); // Post 설정
        return comment;
    }

    private Tag convertTagToEntity(TagDto tagDto, Post post) {
        Tag tag = new Tag();
        tag.setTag_id(tagDto.getTag_id());
        tag.setTag_name(tagDto.getTag_name());
        tag.setPost(post);
        return tag;
    }
}
