package com.example.myspringserver.service;

import com.example.myspringserver.dto.TagDto;
import com.example.myspringserver.entity.Tag;
import org.springframework.stereotype.Service;

@Service
public class TagService {
    public TagDto convertToDto(Tag tag){
        TagDto dto = new TagDto();

        dto.setTag_id(tag.getTag_id());
        dto.setTag_name(tag.getTag_name());

        return dto;
    }

    public Tag convertToEntity(TagDto dto){
        Tag tag = new Tag();

        tag.setTag_id(dto.getTag_id());
        tag.setTag_name(dto.getTag_name());

        return tag;
    }
}
