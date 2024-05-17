package com.example.myspringserver.controller;
import com.example.myspringserver.dto.PostDto;
import com.example.myspringserver.entity.Post;
import com.example.myspringserver.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.myspringserver.repository.PostRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @GetMapping("/getPost")
    public ResponseEntity<List<PostDto>> getPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostDto> postDtos = posts.stream().map(postService::convertToDto).collect(Collectors.toList());

        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    @DeleteMapping("/deletePost/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        try {
            postRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/insert")
    public ResponseEntity<String> insertPost(@RequestBody PostDto postDto) {
        System.out.println("이미지 uri는 ..." + postDto.getImageUrl());
        postRepository.save(postService.convertToEntity(postDto));
        return ResponseEntity.status(HttpStatus.CREATED).body("게시물이 성공적으로 추가되었습니다.");
    }

    @PostMapping("/upload") // 이미지 업로드 처리
    public ResponseEntity<String> uploadImage(@RequestParam("image")MultipartFile file){
        if (file.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select a file to upload.");
        }
        try {
            String uploadDir = "src/main/resources/static/uploads";
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs(); // 업로드 디렉토리가 없는 경우 생성
            }
            String fileName = file.getOriginalFilename();
            String filePath = uploadDir + File.separator + fileName;
            File dest = new File(filePath);
            file.transferTo(dest);
            String fileUrl = "/uploads/" + fileName;
            return ResponseEntity.status(HttpStatus.CREATED).body(fileUrl);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image.");
        }
    }

    @PutMapping("/updatePost/{id}")
    public ResponseEntity<String> updatePost(@PathVariable Long id, @RequestBody Post updatedPost) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("게시물을 찾을 수 없습니다."));

        existingPost.setTitle(updatedPost.getTitle());
        existingPost.setContent(updatedPost.getContent());

        postRepository.save(existingPost);
        return ResponseEntity.ok("게시물이 성공적으로 수정되었습니다.");
    }
}
