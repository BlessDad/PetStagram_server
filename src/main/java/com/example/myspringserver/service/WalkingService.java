package com.example.myspringserver.service;

import com.example.myspringserver.dto.WalkingDto;
import com.example.myspringserver.entity.User;
import com.example.myspringserver.entity.Walking;
import com.example.myspringserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class WalkingService {
    @Autowired
    private UserRepository userRepository;

    public WalkingDto convertToDto(Walking walking) {
        WalkingDto dto = new WalkingDto();

        dto.setWalking_id(walking.getWalking_id());
        dto.setWalking_start(walking.getWalking_start());
        dto.setWalking_end(walking.getWalking_end());
        dto.setWalking_distance(walking.getWalking_distance());
        dto.setWalking_calorie(walking.getWalking_calorie());
        dto.setWalking_speed(walking.getWalking_speed());
        dto.setUser_id(walking.getUser().getUser_id());
        dto.setImageUrl(walking.getImageUrl());
        return dto;
    }

    public Walking convertToEntity(WalkingDto dto) {
        Walking walking = new Walking();

        walking.setWalking_id(dto.getWalking_id());
        walking.setWalking_start(dto.getWalking_start());
        walking.setWalking_end(dto.getWalking_end());
        walking.setWalking_distance(dto.getWalking_distance());
        walking.setWalking_calorie(dto.getWalking_calorie());
        walking.setWalking_speed(dto.getWalking_speed());
        walking.setImageUrl(dto.getImageUrl());

        User user = userRepository.findById(dto.getUser_id())
                .orElseThrow(() -> new IllegalArgumentException("invalid user id : " + dto.getUser_id()));

        walking.setUser(user);
        return walking;
    }

}
