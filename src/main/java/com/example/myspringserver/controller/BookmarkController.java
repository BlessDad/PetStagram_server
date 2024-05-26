package com.example.myspringserver.controller;

import com.example.myspringserver.dto.BookmarkDto;
import com.example.myspringserver.entity.Bookmark;
import com.example.myspringserver.entity.User;
import com.example.myspringserver.repository.BookmarkRepository;
import com.example.myspringserver.repository.UserRepository;
import com.example.myspringserver.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookmark")
public class BookmarkController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private BookmarkService bookmarkService;

    @PostMapping("/insert/{user_id}")
    public ResponseEntity<String> insertBookmark(@PathVariable Integer user_id, @RequestBody BookmarkDto bookmarkDto) {
        try{
            User user = userRepository.findById(user_id)
                    .orElseThrow(() -> new NoSuchElementException("User를 찾을 수 없습니다."));
            bookmarkDto.setUser_id(user.getUser_id());
            Bookmark bookmark = bookmarkService.convertToEntity(bookmarkDto);
            bookmarkRepository.save(bookmark);
            return ResponseEntity.status(HttpStatus.CREATED).body("즐겨찾기 등록 성공");

        }
        catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User를 찾을 수 없습니다.");
        }
    }

    @GetMapping("/getBookmark/{user_id}")
    public ResponseEntity<List<BookmarkDto>> getBookmarkByUserId (@PathVariable Integer user_id) {
        List<Bookmark> bookmarks = bookmarkRepository.findByUser_user_id(user_id);
        List<BookmarkDto> bookmarkDtos = bookmarks.stream().map(bookmarkService::convertToDto).collect(Collectors.toList());

        return new ResponseEntity<>(bookmarkDtos, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{bookmark_id}")
    public ResponseEntity<?> deleteBookmark(@PathVariable Integer bookmark_id) {
        try{
            bookmarkRepository.deleteById(bookmark_id);
            return ResponseEntity.ok().build();
        }
        catch (EmptyResultDataAccessException e){
            return ResponseEntity.notFound().build();
        }
    }



}
