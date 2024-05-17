package com.example.myspringserver.controller;

import com.example.myspringserver.dto.TagDto;
import com.example.myspringserver.entity.Tag;
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

    @PostMapping("/insert")
    public ResponseEntity<String> insertTag(@RequestBody TagDto tagDto) {
        tagRepository.save(tagService.convertToEntity(tagDto));
        return ResponseEntity.status(HttpStatus.CREATED).body("태그 추가 성공");
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
