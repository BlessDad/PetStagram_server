package com.example.myspringserver.service;

import com.example.myspringserver.dto.WalkingDto;
import com.example.myspringserver.entity.Walking;
import org.springframework.stereotype.Service;

@Service
public class WalkingService {
    public WalkingDto convertToDto(Walking walking) {
        WalkingDto dto = new WalkingDto();

        dto.setWalking_id(walking.getWalking_id());
        dto.setWalking_start(walking.getWalking_start());
        dto.setWalking_end(walking.getWalking_end());
        dto.setWalking_distance(walking.getWalking_distance());
        dto.setWalking_calorie(walking.getWalking_calorie());
        dto.setWalking_speed(walking.getWalking_speed());

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

        return walking;
    }

}
