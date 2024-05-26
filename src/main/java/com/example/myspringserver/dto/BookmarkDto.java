package com.example.myspringserver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookmarkDto {
    private Integer Bookmark_id;
    private String Bookmark_address;
    private String Bookmark_name;

    private Integer user_id;
}
