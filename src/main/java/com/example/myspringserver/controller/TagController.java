package com.example.myspringserver.controller;

import com.example.myspringserver.dto.TagDto;
import com.example.myspringserver.entity.Post;
import com.example.myspringserver.entity.Tag;
import com.example.myspringserver.repository.PostRepository;
import com.example.myspringserver.repository.TagRepository;
import com.example.myspringserver.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private PostRepository postRepository;

    @PostMapping("/insert/{post_id}")
    public ResponseEntity<String> insertTag(@PathVariable Long post_id, @RequestBody TagDto tagDto) {
        try {
            // Check if the post exists
            Post post = postRepository.findById(post_id)
                    .orElseThrow(() -> new NoSuchElementException("게시물을 찾을 수 없습니다."));

            tagDto.setPost_id(post.getId());
            Tag tag = tagService.convertToEntity(tagDto);

            tagRepository.save(tag);
            return ResponseEntity.status(HttpStatus.CREATED).body("태그가 게시물에 추가되었습니다.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 게시물을 찾을 수 없습니다.");
        }
    }

    @PutMapping("/updateTag/{id}")
    public ResponseEntity<String> updateTag(@PathVariable Integer id, @RequestBody Tag updatedTag) {
        Tag existingTag = tagRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("태그를 찾을 수 없습니다."));

        //existingTag.setTag_id(updatedTag.getTag_id());
        existingTag.setTag_name(updatedTag.getTag_name());

        tagRepository.save(existingTag);

        return ResponseEntity.ok("태그 수정 성공");
    }

    @GetMapping("/getTag")
    public ResponseEntity<List<TagDto>> getTags(){
        List<Tag> tags = tagRepository.findAll();
        List<TagDto> tagDtos = tags.stream().map(tagService::convertToDto).collect(Collectors.toList());

        return new ResponseEntity<>(tagDtos, HttpStatus.OK);
    }

    @DeleteMapping("/deleteTag/{id}")
    public ResponseEntity<?> deleteTag (@PathVariable Integer id) {
        try{
            tagRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }


}
