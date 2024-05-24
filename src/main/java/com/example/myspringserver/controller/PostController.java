package com.example.myspringserver.controller;

import com.example.myspringserver.dto.PostDto;
import com.example.myspringserver.entity.Post;
import com.example.myspringserver.entity.User;
import com.example.myspringserver.repository.UserRepository;
import com.example.myspringserver.service.PostService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.myspringserver.repository.PostRepository;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/getPost")
    public ResponseEntity<List<PostDto>> getPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostDto> postDtos = posts.stream().map(postService::convertToDto).collect(Collectors.toList());
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    @GetMapping("/getPost/{id}")
    public ResponseEntity<List<PostDto>> getPostsById(@PathVariable Integer id){
        List<Post> posts = postRepository.findPostById(id);
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

    @PostMapping("/insert/{user_id}")
    public ResponseEntity<String> insertPost(@PathVariable Integer user_id, @RequestBody PostDto postDto) {
        try {
            User user = userRepository.findById(user_id)
                    .orElseThrow(() -> new NoSuchElementException("User를 찾을 수 없습니다."));

            postDto.setUser_id(user.getUser_id());
            Post post = postService.convertToEntity(postDto);
            postRepository.save(post);
            return ResponseEntity.status(HttpStatus.CREATED).body("게시글이 추가되었습니다.");
        }
        catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User를 찾을 수 없습니다.");
        }
    }

    @PostMapping("/upload") // 이미지 업로드 처리
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please select a file to upload.");
        }
        try {
            String uploadDir = "/home/ubuntu/images";
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
    public ResponseEntity<String> updatePost(@PathVariable Long id, @RequestBody PostDto updatedPostDto) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("게시물을 찾을 수 없습니다."));

        existingPost.setTitle(updatedPostDto.getTitle());
        existingPost.setContent(updatedPostDto.getContent());
        existingPost.setImageUrl(updatedPostDto.getImageUrl());

//        Post updatedPost = postService.convertToEntity(updatedPostDto);
//        updatedPost.setId(existingPost.getId());
//
//        postRepository.save(updatedPost);

        postRepository.save(existingPost);
        return ResponseEntity.ok("게시물이 성공적으로 수정되었습니다.");
    }
}
