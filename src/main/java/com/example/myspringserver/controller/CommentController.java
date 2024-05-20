package com.example.myspringserver.controller;

import com.example.myspringserver.dto.CommentDto;
import com.example.myspringserver.entity.Comment;
import com.example.myspringserver.entity.Post;
import com.example.myspringserver.repository.CommentRepository;
import com.example.myspringserver.repository.PostRepository;
import com.example.myspringserver.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostRepository postRepository;

    @PostMapping("/insert/{post_id}") // 게시물의 ID에 따라 댓글 추가
    public ResponseEntity<String> insertComment(@PathVariable Long post_id, @RequestBody CommentDto commentDto) {
        try {
            // Check if the post exists
            Post post = postRepository.findById(post_id)
                    .orElseThrow(() -> new NoSuchElementException("게시물을 찾을 수 없습니다."));

            commentDto.setPost_id(post.getId());
            Comment comment = commentService.convertToEntity(commentDto);
            //comment.setPost(post);
            commentRepository.save(comment);
            return ResponseEntity.status(HttpStatus.CREATED).body("댓글이 게시물에 추가되었습니다.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 게시물을 찾을 수 없습니다.");
        }
    }

    @GetMapping("/getComment/{post_id}") // 게시물의 ID에 따라 댓글 조회
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable Long post_id) {
        List<Comment> comments = commentRepository.findByPostId(post_id);
        List<CommentDto> commentDtos = comments.stream().map(commentService::convertToDto).collect(Collectors.toList());
        return new ResponseEntity<>(commentDtos, HttpStatus.OK);
    }

    @PutMapping("/updateComment/{id}")
    public ResponseEntity<String> updateComment(@PathVariable Integer id, @RequestBody Comment updatedComment) {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("댓글을 찾을 수 없음"));

        existingComment.setComment_writer(updatedComment.getComment_writer());
        existingComment.setComment_content(updatedComment.getComment_content());

        commentRepository.save(existingComment);

        return ResponseEntity.ok("댓글 수정 성공");
    }

    @DeleteMapping("/deleteComment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer id) {
        try {
            commentRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
