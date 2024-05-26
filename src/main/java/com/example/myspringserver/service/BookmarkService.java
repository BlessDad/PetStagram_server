package com.example.myspringserver.service;

import com.example.myspringserver.dto.BookmarkDto;
import com.example.myspringserver.entity.Bookmark;
import com.example.myspringserver.entity.User;
import com.example.myspringserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;

@Service
public class BookmarkService {
    @Autowired
    private UserRepository userRepository;

    public BookmarkDto convertToDto(Bookmark bookmark) {
        BookmarkDto dto = new BookmarkDto();
        dto.setBookmark_id(bookmark.getBookmark_id());
        dto.setBookmark_address(bookmark.getBookmark_address());
        dto.setBookmark_name(bookmark.getBookmark_name());
        dto.setUser_id(bookmark.getUser().getUser_id());
        return dto;
    }

    public Bookmark convertToEntity(BookmarkDto dto) {
        Bookmark bookmark = new Bookmark();
        bookmark.setBookmark_id(dto.getBookmark_id());
        bookmark.setBookmark_address(dto.getBookmark_address());
        bookmark.setBookmark_name(dto.getBookmark_name());

        User user = userRepository.findById(dto.getUser_id())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Id : " + dto.getUser_id()));
        bookmark.setUser(user);

        return bookmark;
    }
}
