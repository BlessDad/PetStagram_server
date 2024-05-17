package com.example.myspringserver.controller;

import com.example.myspringserver.dto.CommentDto;
import com.example.myspringserver.entity.Comment;
import com.example.myspringserver.repository.CommentRepository;
import com.example.myspringserver.service.CommentService;
import org.apache.catalina.connector.Response;
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

    @PostMapping("/insert")
    public ResponseEntity<String> insertComment(@RequestBody CommentDto commentDto){
        commentRepository.save(commentService.convertToEntity(commentDto));
        return ResponseEntity.status(HttpStatus.CREATED).body("댓글 등록 성공");
    }

    @PutMapping("/updateComment/{id}")
    public ResponseEntity<String> updateComment(@PathVariable Integer id, @RequestBody Comment updatedComment) {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("댓글을 찾을 수 없음"));

        //existingComment.setComment_id(updatedComment.getComment_id());
        existingComment.setComment_writer(updatedComment.getComment_writer());
        existingComment.setComment_content(updatedComment.getComment_content());

        commentRepository.save(existingComment);

        return ResponseEntity.ok("댓글 수정 성공");
    }

    @GetMapping("/getComment")
    public ResponseEntity<List<CommentDto>> getComments() {
        List<Comment> comments = commentRepository.findAll();
        List<CommentDto> commentDtos = comments.stream().map(commentService::convertToDto).collect(Collectors.toList());

        return new ResponseEntity<>(commentDtos, HttpStatus.OK);
    }

    @DeleteMapping("/deleteComment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer id) {
        try{
            commentRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        catch (EmptyResultDataAccessException e){
            return ResponseEntity.notFound().build();
        }
    }

}
